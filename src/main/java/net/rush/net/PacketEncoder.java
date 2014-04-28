package net.rush.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.rush.packets.Packet;
import net.rush.packets.misc.Protocol;
import net.rush.packets.packet.PacketLoginSuccess;
import net.rush.packets.serialization.SerializationPacketSender;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

	private Protocol protocol;

	private SerializationPacketSender<Packet> sender = new SerializationPacketSender<Packet>();
	
	public PacketEncoder(Protocol prot) {
		this.protocol = prot;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
		Protocol.ProtocolDirection dir = protocol.TO_CLIENT;
		Packet.writeVarInt(dir.getId(packet.getClass()), out);

		ByteBufOutputStream outS = new ByteBufOutputStream(out);

		//msg.a(outS);

		sender.send(outS, packet);

		if (packet instanceof PacketLoginSuccess)
			setProtocol(ctx, Protocol.GAME);
		
	}

	public void setProtocol(ChannelHandlerContext channel, Protocol prot) {
		channel.pipeline().get(PacketDecoder.class).setProtocol(prot);
		channel.pipeline().get(PacketEncoder.class).setProtocol(prot);
	}
}
