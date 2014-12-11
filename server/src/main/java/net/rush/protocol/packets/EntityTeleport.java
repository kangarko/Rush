package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.api.utils.MathHelper;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EntityTeleport extends EntityExists {

	public EntityTeleport(int entityId, int x, int y, int z, int yaw, int pitch) {
		super(entityId, x, y, z, yaw, pitch);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		super.write(out);

		writePosIntegers(x, y, z, out);
		out.writeByte(MathHelper.floatToByte(yaw));
		out.writeByte(MathHelper.floatToByte(pitch));
	}
}
