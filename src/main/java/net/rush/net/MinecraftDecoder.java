package net.rush.net;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.PacketHandler;
import net.rush.packets.Packets;
import net.rush.packets.serialization.SerializationPacketHandler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferInputStream;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;
import org.jboss.netty.handler.codec.replay.VoidEnum;

/**
 * A {@link ReplayingDecoder} which decodes {@link ChannelBuffer}s into
 * Minecraft {@link net.lightstone.msg.Message}s.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MinecraftDecoder extends ReplayingDecoder<VoidEnum> {

	private int previousOpcode = -1;
	private static PacketHandler handler = new SerializationPacketHandler<Packet>();


	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel c, ChannelBuffer buf, VoidEnum state) throws Exception {
		int opcode = buf.readUnsignedByte();
		ChannelBufferInputStream input = new ChannelBufferInputStream(buf);
		
		Class<? extends Packet> packet = Packets.lookupPacket(opcode);
		if (packet == null) {
			input.close();
			throw new IOException("Unknown operation code: " + opcode + " (previous opcode: " + previousOpcode + ").");	
		}

		previousOpcode = opcode;

		return handler.handle(input, packet);
	}

}

