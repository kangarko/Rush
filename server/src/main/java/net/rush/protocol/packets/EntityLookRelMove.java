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
public class EntityLookRelMove extends Packet {

	private int entityId;
	private int diffX;
	private int diffY;
	private int diffZ;
	private int yaw;
	private int pitch;

	@Override
	public void read(ByteBuf in) throws IOException {
		entityId = in.readInt();
		diffX = in.readByte();
		diffY = in.readByte();
		diffZ = in.readByte();
		yaw = in.readByte();
		pitch = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeInt(entityId);
		out.writeByte(diffX);
		out.writeByte(diffY);
		out.writeByte(diffZ);
		out.writeByte(yaw);
		out.writeByte(pitch);
	}
}
