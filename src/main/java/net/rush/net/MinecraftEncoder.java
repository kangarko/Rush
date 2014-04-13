package net.rush.net;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.PacketSender;
import net.rush.packets.Packets;
import net.rush.packets.serialization.SerializationPacketSender;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferOutputStream;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * A {@link OneToOneEncoder} which encodes Minecraft {@link Packet}s into
 * {@link ChannelBuffer}s.
 */
public class MinecraftEncoder extends OneToOneEncoder {

	private static PacketSender<Packet> sender = new SerializationPacketSender<Packet>();
	
	// TODO ? this.getClass().getMethod("encode", message.getPacketType().getClass()).invoke(this, message.getPacketType())
	
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel c, Object msg) throws Exception {
		if (msg instanceof Packet) {
			Packet message = (Packet) msg;

			Class<? extends Packet> clazz = message.getPacketType();
			Class<? extends Packet> codec = Packets.lookupPacket(message.getOpcode());

			if (codec == null)
				throw new IOException("Unknown packet type: " + clazz.getSimpleName() + ".");

			ChannelBuffer opcodeBuf = ChannelBuffers.dynamicBuffer();
			opcodeBuf.writeByte(message.getOpcode());

			ChannelBufferOutputStream output = new ChannelBufferOutputStream(opcodeBuf);
			
			sender.send(output, message);
			
			return ChannelBuffers.wrappedBuffer(opcodeBuf);
		}
		return msg;
	}

}

