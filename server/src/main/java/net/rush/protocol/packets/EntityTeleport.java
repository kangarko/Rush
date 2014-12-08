package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.api.MathHelper;
import net.rush.protocol.Packet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EntityTeleport extends Packet {

	private int entityId;
	private int x;
	private int y;
	private int z;
	private float yaw;
	private float pitch;


	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeInt(entityId);
		writePosIntegers(x, y, z, out);
		out.writeByte(MathHelper.floatToByte(yaw));
		out.writeByte(MathHelper.floatToByte(pitch));
	}
}
