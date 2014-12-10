package net.rush.netty.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.rush.netty.NettyChannelHandler;
import net.rush.protocol.Packet;
import net.rush.protocol.Protocol;
import net.rush.protocol.packets.LoginSuccess;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

	public Protocol protocol;

	public PacketEncoder(Protocol protocol) {
		this.protocol = protocol;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) {
		try {
			Protocol.PacketDirection prot = protocol.TO_CLIENT;
			int protocol = ctx.pipeline().get(NettyChannelHandler.class).session.protocol;
			
			Packet.writeVarInt(prot.getId(packet.getClass()), out);
			packet.protocol = protocol;
			packet.write(out);
			
			if (packet instanceof LoginSuccess)
				prot.setProtocol(ctx, Protocol.GAME);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}