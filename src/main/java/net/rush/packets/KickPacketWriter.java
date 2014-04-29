package net.rush.packets;

import net.rush.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class KickPacketWriter extends MessageToByteEncoder<String> {

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
		out.writeByte(255); // packet ID - 0xFF = KickPacket
		ByteBufUtils.writeString(out, msg);
	}
}
