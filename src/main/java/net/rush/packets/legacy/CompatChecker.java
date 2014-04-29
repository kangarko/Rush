package net.rush.packets.legacy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import net.rush.net.PacketDecoder;

import org.bukkit.ChatColor;

/**
 * Checker whenever the client is 1.6 or 1.7.
 */
public class CompatChecker extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < 3)
			return;
		
		int index = in.readerIndex();
		short b1 = in.getUnsignedByte(index++);
		short b2 = in.getUnsignedByte(index++);
		short b3 = in.getUnsignedByte(index++);

		if (b1 == 254 && b2 == 1 && b3 == 250) {
			LegacyCompatProvider.provideCompatFor(ctx.channel().remoteAddress());
			ctx.pipeline().remove(PacketDecoder.class);
			String kickMessage = ChatColor.DARK_BLUE 
					+ "\00" + 78
					+ "\00" + "1.6.4-1.7.5" 
					+ "\00" + "Detected 1.6.x client!"
					+ "\00" + 0
					+ "\00"	+ 20;

			ctx.writeAndFlush(kickMessage);
			ctx.close();
		} else if (b1 == 2 && b2 == 76) {
			ctx.pipeline().remove(PacketDecoder.class);
			LegacyCompatProvider.provideCompatFor(ctx.channel().remoteAddress());
			String kickstr = ChatColor.GREEN + "Please Login Again";
			ctx.writeAndFlush(kickstr);
			ctx.close();
		} else
			LegacyCompatProvider.stopProvidingCompatFor(ctx.channel().remoteAddress());

		ctx.pipeline().remove(this);
	}
}
