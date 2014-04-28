package net.rush.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.MessageList;
import io.netty.handler.codec.ReplayingDecoder;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.Packets;
import net.rush.packets.serialization.SerializationPacketHandler;

/**
 * This class decodes (read) incoming connection (in this case - packets).
 * @author kangarko
 */
@SuppressWarnings("unchecked")
public class MinecraftDecoder extends ReplayingDecoder<Packet> {

	private int previousOpcode = -1;
	
	private SerializationPacketHandler<Packet> handler = new SerializationPacketHandler<Packet>();
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, MessageList<Object> out) throws Exception {
		if(buf.readableBytes() == 0)
			return;
		
		int opcode = buf.readUnsignedByte();
		
		Class<? extends Packet> packet = Packets.lookupPacket(opcode);
		
		if (packet == null) {
			throw new IOException("Unknown operation code: " + opcode + " (previous opcode: " + previousOpcode + ").");	
		}
		
		previousOpcode = opcode;
		
		ByteBufInputStream input = new ByteBufInputStream(buf);

		out.add(handler.handle(input, (Class<Packet>) packet));
	}
}
