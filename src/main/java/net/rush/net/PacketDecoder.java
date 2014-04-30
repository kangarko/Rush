package net.rush.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

import net.rush.packets.Packet;
import net.rush.packets.misc.Protocol;
import net.rush.packets.packet.HandshakePacket;

/**
 * Packet decoding class backed by a reusable {@link DataInputStream} which
 * backs the input {@link ByteBuf}. Reads an unsigned byte packet header and
 * then decodes the packet accordingly.
 */
public class PacketDecoder extends MessageToMessageDecoder<ByteBuf> {

	private Protocol protocol;

	public PacketDecoder(Protocol prot) {
		this.protocol = prot;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if(in.readableBytes() == 0)
			return;

		Protocol.ProtocolDirection dir = protocol.TO_SERVER;		
		int packetId = Packet.readVarInt(in);
		Class<? extends Packet> packetClazz = dir.createPacket(packetId);
		
		if (packetClazz == null)
			throw new IOException("Unknown operation code: " + packetId + ").");
		
		Packet packet = packetClazz.newInstance();
		
		packet.read17(new ByteBufInputStream(in));
		
		out.add(packet);
		 
		if (packet instanceof HandshakePacket) {
			HandshakePacket handshake = (HandshakePacket) packet;
			
			switch (handshake.state) {
				case 1:
					setProtocol(ctx, Protocol.STATUS);
					break;
				case 2:
					setProtocol(ctx, Protocol.LOGIN);
					break;
			}
		}
	}

	public void setProtocol(ChannelHandlerContext channel, Protocol prot) {
		channel.pipeline().get(PacketDecoder.class).setProtocol(prot);
		channel.pipeline().get(PacketEncoder.class).setProtocol(prot);
	}
	
	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}
}
