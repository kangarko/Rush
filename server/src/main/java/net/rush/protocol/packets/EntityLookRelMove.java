package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EntityLookRelMove extends EntityExists {

	public EntityLookRelMove(int entityId, int x, int y, int z, int yaw, int pitch) {
		super(entityId, x, y, z, yaw, pitch);
	}
	
	@Override
	public void read(ByteBuf in) throws IOException {
		super.read(in);

		x = in.readByte();
		y = in.readByte();
		z = in.readByte();
		yaw = in.readByte();
		pitch = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		super.write(out);

		out.writeByte(x);
		out.writeByte(y);
		out.writeByte(z);
		out.writeByte(yaw);
		out.writeByte(pitch);
	}
}
