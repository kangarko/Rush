package net.rush.netty.pipeline;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.Setter;
import net.rush.api.exceptions.PacketException;
import net.rush.netty.ChannelHandler;
import net.rush.protocol.Packet;
import net.rush.protocol.Protocol;
import net.rush.protocol.Protocol.PacketDirection;
import net.rush.protocol.packets.Handshake;

public class PacketDecoder extends ByteToMessageDecoder {

	@Setter
	private Protocol protocol;

	public PacketDecoder(Protocol protocol) {
		this.protocol = protocol;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {		
		try {
			if (in instanceof EmptyByteBuf)  // TODO Bad workaround. Why getting this?
				return;						 // This should not happen. Somewhere is a bug I suppose.
			
			PacketDirection prot = protocol.TO_SERVER;
			int protocol = ctx.pipeline().get(ChannelHandler.class).getSession().protocol;
			
			int id = Packet.readVarInt(in);
			Packet packet = null;

			if (prot.hasPacket(id)) {
				packet = prot.newPacket(id);
				packet.setProtocol(protocol);
				packet.read(in);

				if (in.readableBytes() != 0)
					throw new PacketException("Did not read all bytes from packet " + packet);

			} else {
				if (id != 4)
					System.out.println("Skipping reading packet ID " + id);
				in.skipBytes(in.readableBytes());			
				return;
			}

			out.add(packet);

			if (packet instanceof Handshake) {
				Handshake handshake = (Handshake) packet;

				switch (handshake.getState()) {
					case 1:
						prot.changeProtocol(ctx, Protocol.STATUS);
						break;
					case 2:
						prot.changeProtocol(ctx, Protocol.LOGIN);
						break;
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
