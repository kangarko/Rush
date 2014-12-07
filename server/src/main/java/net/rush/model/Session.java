package net.rush.model;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.lang3.Validate;

import lombok.RequiredArgsConstructor;
import net.rush.RushServer;
import net.rush.model.entity.RushPlayer;
import net.rush.protocol.Packet;
import net.rush.protocol.PacketHandler;
import net.rush.protocol.packets.Kick;
import net.rush.utils.JsonUtils;

@RequiredArgsConstructor
public class Session {

	private final PacketHandler handler = new PacketHandler();
	private final Queue<Packet> messageQueue = new LinkedList<>();
	
	private final Channel channel;
	public final RushServer server;
	
	public int protocol;	
	public boolean active = true;
	public RushPlayer player = null;

	public void sendPacket(Packet packet) {
		channel.writeAndFlush(packet);
	}

	public void sendPacket(Kick packet) {
		disconnect(packet.getReason());
	}

	public void disconnect(String reason) {
		channel.writeAndFlush(new Kick(JsonUtils.jsonizeChat(reason))).addListener(ChannelFutureListener.CLOSE);
	}
	
	public void pulse() {
		synchronized (messageQueue) {
			Packet packet;
			
			while ((packet = messageQueue.poll()) != null)
				handler.handle(this, packet);
		}
	}
	
	public void setPlayer(RushPlayer player) {
		Validate.isTrue(player != null, "Player with name " + player.name + " already associated with the session!");
		this.player = player;
	}
	
	public void messageReceived(Packet packet) {
		synchronized (messageQueue) {
			messageQueue.add(packet);
		}
	}
	
	public SocketAddress removeAddress() {
		return channel.remoteAddress();
	}

}
