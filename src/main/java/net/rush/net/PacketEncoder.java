package net.rush.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.rush.PacketLogger;
import net.rush.packets.Packet;
import net.rush.packets.misc.Protocol;
import net.rush.packets.packet.PacketLoginSuccess;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

	private Protocol protocol;

	public PacketEncoder(Protocol protocol) {
		this.protocol = protocol;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
		int clientProtocol = ctx.pipeline().get(MinecraftHandler.class).session.getClientVersion().getProtocol();
		ByteBufOutputStream output = new ByteBufOutputStream(out);

		Packet.writeVarInt(protocol.TO_CLIENT.getId(packet.getClass()), out);

		if (clientProtocol == 12)
			packet.write18(output);
		else if (clientProtocol == 5)
			packet.write176(output);
		else
			packet.write17(output);

		if (packet instanceof PacketLoginSuccess)
			setProtocol(ctx, Protocol.GAME);
		
		PacketLogger.submitWrite(packet, clientProtocol, false);
	}

	public void setProtocol(ChannelHandlerContext channel, Protocol prot) {
		channel.pipeline().get(PacketDecoder.class).setProtocol(prot);
		channel.pipeline().get(PacketEncoder.class).setProtocol(prot);
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}
}
