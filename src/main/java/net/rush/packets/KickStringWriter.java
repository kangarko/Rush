package net.rush.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class KickStringWriter extends MessageToByteEncoder<String> {

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
		out.writeByte(255); // packet ID
		out.writeShort(msg.length()); // motd length 
		for (char c : msg.toCharArray())
			out.writeChar(c); // the motd, see ServerListPingPacketHandler for more info
		
	}
}
