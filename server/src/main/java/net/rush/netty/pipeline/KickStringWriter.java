package net.rush.netty.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class KickStringWriter extends MessageToByteEncoder<String> {

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
		out.writeByte(0xFF); // KickPacket

		for (int i = 0; i < msg.length(); i++)
			out.writeChar(msg.charAt(i));
	}
}
