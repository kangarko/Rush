package net.rush.model;

import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import org.apache.commons.lang3.Validate;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.rush.Server;
import net.rush.entity.EntityPlayer;
import net.rush.protocol.Packet;
import net.rush.protocol.PacketHandler;
import net.rush.protocol.packets.KeepAlive;
import net.rush.protocol.packets.Kick;
import net.rush.utils.JsonUtils;

public class Session {

	public int protocol = 5;

	private final PacketHandler handler = new PacketHandler();
	private final Queue<Packet> messageQueue = new LinkedList<>();
	private final Channel channel;
	@Getter
	private final Server server;
	@Getter
	private EntityPlayer player = null;

	private int pingTimer;
	@Getter
	private int pingToken = 0;
	@Getter
	private boolean pendingRemoval = false;

	public Session(Server server, Channel channel) {
		this.server = server;
		this.channel = channel;

		server.getSessionRegistry().add(this);
	}

	/**
	 * This is to ensure the player has actually logged in.
	 */
	private JoinHelper joinHelper;

	public void sendPacket(Packet packet) {
		channel.writeAndFlush(packet);
	}

	/**
	 * @deprecated Use disconnect method
	 */
	public void sendPacket(Kick packet) {
		disconnect(packet.getReason());
	}

	public void disconnect(String reason) {
		channel.writeAndFlush(new Kick(JsonUtils.jsonizePlainText(reason))).addListener(ChannelFutureListener.CLOSE);

		if (!pendingRemoval)
			destroy();
	}

	public void pulse() {
		synchronized (messageQueue) {
			Packet packet;

			while ((packet = messageQueue.poll()) != null)
				handler.handle(this, packet);
		}

		if (player != null) // Prevent pinging while viewing MoTD.
			if (pingTimer++ == 7 * 20) { // Ping every 7. second.
				if (pingToken == 0) {
					pingToken = server.rand.nextInt();

					sendPacket(new KeepAlive(pingToken));
				} else
					disconnect("Timed out");

				pingTimer = 0;
			}

		if (joinHelper != null && joinHelper.getDecreasedWaitCount() == 0) {
			if (!pendingRemoval)
				setPlayer(new EntityPlayer(this, joinHelper.getPlayerName()));

			joinHelper = null;
		}
	}

	public void setPlayer(EntityPlayer newPlayer) {
		Objects.requireNonNull(newPlayer, "New player cannot be null!");
		Validate.isTrue(player == null, "Player already associated with the session!");

		this.player = newPlayer;
	}

	public void startJoining(String name) {
		Validate.isTrue(joinHelper == null, "Already joining!");
		Validate.isTrue(player == null, "Player has already joined!");

		joinHelper = new JoinHelper(name);
	}

	public boolean hasPlayer() {
		return player != null;
	}

	public void messageReceived(Packet packet) {
		synchronized (messageQueue) {
			messageQueue.add(packet);
		}
	}

	public SocketAddress getIp() {
		return channel.remoteAddress();
	}

	public void destroy() {
		Validate.isTrue(!pendingRemoval, "Already pending removal.");

		pendingRemoval = true;
	}	

	void onDispose() {
		if (player != null) {
			System.out.println("[!] - >> An assosiated player (" + player + ") has been removed!");
			player.destroy();
			player.onDisconnect();
			player = null;
		}

		System.out.println("Session removed.");
	}

	public void pong() {
		pingToken = 0;
	}
}

@RequiredArgsConstructor
class JoinHelper {
	/**
	 * Since netty is async we need to wait one round to get the true result.
	 */
	private int waitCount = 1;
	@Getter
	private final String playerName;

	public int getDecreasedWaitCount() {
		return waitCount--;
	}
}