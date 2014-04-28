package net.rush.packets.legacy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import net.rush.net.PacketDecoder;

import org.bukkit.ChatColor;

public class LegacyCompatDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < 3) {
			return;
		}
		int i = in.readerIndex();
		short serverListPingPacket = in.getUnsignedByte(i++);
		short b2 = in.getUnsignedByte(i++);
		short pluginMessage = in.getUnsignedByte(i++);

		if (serverListPingPacket == 254 && b2 == 1 && pluginMessage == 250) {
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
		} else if (serverListPingPacket == 2 && b2 == 76) {
			ctx.pipeline().remove(PacketDecoder.class);
			LegacyCompatProvider.provideCompatFor(ctx.channel().remoteAddress());
			String kickstr = ChatColor.GREEN + "Please Login Again";
			ctx.writeAndFlush(kickstr);
			ctx.close();
		} else {
			LegacyCompatProvider.stopProvidingCompatFor(ctx.channel().remoteAddress());
		}
		ctx.pipeline().remove(this);
	}
}
