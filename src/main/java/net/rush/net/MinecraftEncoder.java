package net.rush.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.rush.packets.Packet;
import net.rush.packets.serialization.SerializationPacketSender;

/**
 * This class encodes (write) incoming connection (in this case - packets).
 * @author kangarko
 */
public class MinecraftEncoder extends MessageToByteEncoder<Packet> {

	private SerializationPacketSender<Packet> sender = new SerializationPacketSender<Packet>();


	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf buf) throws Exception {
		//System.out.println("encoding packet! type: " + packet.getClass().getSimpleName());

		/*Class<? extends Packet> clazz = packet.getPacketType();
		Class<? extends Packet> codec = Packets.lookupPacket(packet.getOpcode());

		if (codec == null)
			throw new IOException("Unknown packet type: " + clazz.getSimpleName() + ".");*/

		//ByteBuf opcodeBuf = Unpooled.buffer();
		buf.writeByte(packet.getOpcode());

		ByteBufOutputStream output = new ByteBufOutputStream(buf);
		
		sender.send(output, packet);

		//ctx.write(Unpooled.wrappedBuffer(opcodeBuf));
	}
}
