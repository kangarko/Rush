package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EntityHeadLook extends Packet {

	private int entityId;
	private int headYaw;

	@Override
	public void read(ByteBuf in) throws IOException {
		entityId = in.readInt();
		headYaw = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeInt(entityId);
		out.writeByte(headYaw);
	}
}
