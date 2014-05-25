package net.rush.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import net.rush.Server;
import net.rush.gui.contentpane.GuiPane;
import net.rush.model.Player;
import net.rush.packets.Packet;
import net.rush.packets.handler.HandlerLookupService;
import net.rush.packets.handler.PacketHandler;
import net.rush.packets.packet.KeepAlivePacket;
import net.rush.packets.packet.KickPacket;
import net.rush.util.StringUtils;

/**
 * A single connection to the server, which may or may not be associated with a
 * player.

 */
public final class Session {

	/**
	 * The number of ticks which are elapsed before a client is disconnected due
	 * to a timeout.
	 * If client don´t get this value for about 22 seconds it gets disconnected,
	 * but it is recommended to set this to lower number due to server lag.
	 */
	private static final int TIMEOUT_TICKS = 6 * 20;

	/**
	 * The state this connection is currently in.
	 */
	public enum State {

		/**
		 * In the exchange handshake state, the server is waiting for the client
		 * to send its initial handshake packet.
		 */
		EXCHANGE_HANDSHAKE,

		/**
		 * In the exchange identification state, the server is waiting for the
		 * client to send its identification packet.
		 */
		EXCHANGE_IDENTIFICATION,

		/**
		 * In the game state the session has an associated player.
		 */
		GAME;
	}

	/**
	 * The server this session belongs to.
	 */
	private final Server server;

	/**
	 * The channel associated with this session.
	 */
	private final Channel channel;

	/**
	 * A queue of incoming and unprocessed messages.
	 */
	private final Queue<Packet> messageQueue = new ArrayDeque<Packet>();

	/**
	 * A timeout counter. This is increment once every tick and if it goes above
	 * a certain value the session is disconnected.
	 */
	private int timeoutCounter = 0;

	/**
	 * The current state.
	 */
	private State state = State.EXCHANGE_HANDSHAKE;
	;
	/**
	 * The player associated with this session (if there is one).
	 */
	private Player player;

	/**
	 * True means that this is a 1.6 client.
	 */
	private final boolean compat;

	private ClientVersion clientVersion = new ClientVersion("1.7.2", 4); // default to prevent NPE

	private boolean pendingRemoval = false;
	private int pingMessageId;

	/**
	 * Creates a new session.
	 * @param server The server this session belongs to.
	 * @param channel The channel associated with this session.
	 */
	public Session(Server server, Channel channel, boolean compat) {
		this.server = server;
		this.channel = channel;
		this.compat = compat;
	}

	/**
	 * Gets the state of this session.
	 * @return The session's state.
	 */
	public State getState() {
		return state;
	}

	/**
	 * Sets the state of this session.
	 * @param state The new state.
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * Gets the player associated with this session.
	 * @return The player, or {@code null} if no player is associated with it.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the player associated with this session.
	 * @param player The new player.
	 * @throws IllegalStateException if there is already a player associated
	 * with this session.
	 */
	public void setPlayer(Player player) {
		if (this.player != null)
			throw new IllegalStateException();

		this.player = player;
		this.server.getWorld().getPlayers().add(player);
	}

	/**
	 * Handles any queued messages for this session and increments the timeout
	 * counter.
	 * @return 
	 * @return {@code true} if this session is still active, {@code false} if
	 * it is pending removal.
	 */
	@SuppressWarnings("unchecked")
	public boolean pulse() {
		if(pendingRemoval)
			return false;

		Packet packet;
		while ((packet = messageQueue.poll()) != null) {
			PacketHandler<Packet> handler = (PacketHandler<Packet>) HandlerLookupService.find(packet.getPacketType());
			if (handler != null) {
				handler.handle(this, player, packet);
				String name = packet.getPacketType().getSimpleName();
				if(!name.contains("Position") && !name.contains("PlayerOnGround") && !name.contains("Look") && !name.contains("ChatPacket") && !name.contains("KeepAlive")  && !name.contains("Animation"))
					server.getLogger().info("Handling packet: " + packet.getPacketType().getSimpleName());
			} else {
				server.getLogger().info("&cMissing handler for packet: " + packet.getPacketType().getSimpleName());
				server.getGui().showPane(new GuiPane("Unhandled packet", "Missing handler for packet:", packet.getPacketType().getSimpleName(), Color.RED, Color.WHITE, Color.WHITE));
			}
		}

		if (timeoutCounter >= TIMEOUT_TICKS) {
			if (pingMessageId == 0) {
				pingMessageId = new Random().nextInt();
				send(new KeepAlivePacket(pingMessageId));
			} else {
				disconnect("Timed out");
			}
			timeoutCounter = 0;
		} else {
			timeoutCounter++;
		}
		return true;
	}

	/**
	 * Sends a packet to the client.
	 * @param packet The message.
	 */
	public void send(Packet packet) {
		channel.writeAndFlush(packet);
	}

	/**
	 * Disconnects the session with the specified reason. This causes a
	 * {@link KickPacket} to be sent. When it has been delivered, the channel
	 * is closed.
	 * @param reason The reason for disconnection.
	 */
	public void disconnect(String reason) {
		channel.writeAndFlush(new KickPacket(StringUtils.colorize(reason))).addListener(ChannelFutureListener.CLOSE);
	}

	/**
	 * Gets the server associated with this session.
	 * @return The server.
	 */
	public Server getServer() {
		return server;
	}

	@Override
	public String toString() {
		return Session.class.getName() + " [address=" + getIp() + "]";
	}

	/**
	 * Adds a message to the unprocessed queue.
	 * @param message The message.
	 * @param <T> The type of message.
	 */
	<T extends Packet> void messageReceived(T message) {
		messageQueue.add(message);
	}

	/**
	 * Disposes of this session by destroying the associated player, if there is
	 * one.
	 */
	void dispose() {
		if (player != null) {
			player.destroy();
			player = null; // in case we are disposed twice
		}
	}

	public int getPingMessageId() {
		return pingMessageId;
	}

	public String getIp() {
		return channel.remoteAddress().toString().replace("/", "");
	}

	public void pong() {
		timeoutCounter = 0;
		pingMessageId = 0;
	}

	void flagForRemoval() {
		pendingRemoval = true;
	}

	public boolean isCompat() {
		return compat;
	}

	public void setClientVersion(String version, int protocol) {
		this.clientVersion = new ClientVersion(version, protocol);
	}

	public ClientVersion getClientVersion() {
		return clientVersion;
	}

	public static class ClientVersion {
		private final String version;
		private final int protocol;

		public ClientVersion(String version, int protocol) {
			super();
			this.version = version;
			this.protocol = protocol;
		}

		public String getVersion() {
			return version;
		}

		public int getProtocol() {
			return protocol;
		}

		@Override
		public String toString() {
			return "ver=" + version + ",protocol=" + protocol;
		}
	}
}

