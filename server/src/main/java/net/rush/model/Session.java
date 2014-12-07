package net.rush.model;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

import java.util.LinkedList;
import java.util.Queue;

import lombok.RequiredArgsConstructor;
import net.rush.protocol.Packet;
import net.rush.protocol.PacketHandler;
import net.rush.protocol.packets.Kick;
import net.rush.utils.JsonUtils;

@RequiredArgsConstructor
public class Session {

	private final PacketHandler handler = new PacketHandler();
	private final Channel channel;

	private final Queue<Packet> messageQueue = new LinkedList<>();
	
	public int protocol;	
	public boolean active = true;

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
	
	public void messageReceived(Packet packet) {
		synchronized (messageQueue) {
			messageQueue.add(packet);
		}
	}

}
