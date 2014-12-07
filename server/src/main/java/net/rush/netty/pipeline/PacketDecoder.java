package net.rush.netty.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import net.rush.exceptions.PacketException;
import net.rush.protocol.Packet;
import net.rush.protocol.Protocol;
import net.rush.protocol.packets.Handshake;

public class PacketDecoder extends ByteToMessageDecoder {

	public Protocol protocol;

	public PacketDecoder(Protocol protocol) {
		this.protocol = protocol;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {		
		try {
			if (in instanceof EmptyByteBuf) // TODO Bad workaround. Why getting this?
				return;						// This should not happen. Somewhere is a bug I suppose.
			
			Protocol.PacketDirection prot = protocol.TO_SERVER;

			int id = Packet.readVarInt(in);
			Packet packet = null;

			if (prot.hasPacket(id)) {
				packet = prot.newPacket(id);
				packet.read(in);

				if (in.readableBytes() != 0)
					throw new PacketException("Did not read all bytes from packet " + packet);

			} else {
				System.out.println("Skipping reading packet ID " + id);
				in.skipBytes(in.readableBytes());			
				return;
			}

			out.add(packet);

			if (packet instanceof Handshake) {
				Handshake handshake = (Handshake) packet;

				switch (handshake.getState()) {
					case 1:
						prot.setProtocol(ctx, Protocol.STATUS);
						break;
					case 2:
						prot.setProtocol(ctx, Protocol.LOGIN);
						break;
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
