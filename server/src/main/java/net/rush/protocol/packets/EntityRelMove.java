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
public class EntityRelMove extends Packet {

	private int entityId;
	private byte diffX;
	private byte diffY;
	private byte diffZ;

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeInt(entityId);
		out.writeByte(diffX);
		out.writeByte(diffY);
		out.writeByte(diffZ);
	}
}
