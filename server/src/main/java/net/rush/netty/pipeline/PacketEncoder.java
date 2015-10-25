package net.rush.netty.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.Setter;
import net.rush.netty.RushChannelHandler;
import net.rush.protocol.Packet;
import net.rush.protocol.Protocol;
import net.rush.protocol.Protocol.PacketDirection;
import net.rush.protocol.packets.LoginSuccess;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

	@Setter
	private Protocol protocol;

	public PacketEncoder(Protocol protocol) {
		this.protocol = protocol;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) {
		try {
			PacketDirection prot = protocol.TO_CLIENT;
			int protocol = ctx.pipeline().get(RushChannelHandler.class).getSession().protocol;
			
			Packet.writeVarInt(prot.getId(packet.getClass()), out);
			packet.setProtocol(protocol);
			packet.write(out);
			
			if (packet instanceof LoginSuccess)
				prot.changeProtocol(ctx, Protocol.GAME);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}