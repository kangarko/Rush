package net.rush.model.entity;

import net.rush.model.RushWorld;
import net.rush.protocol.Packet;
import net.rush.protocol.packets.EntityLook;
import net.rush.protocol.packets.EntityLookRelMove;
import net.rush.protocol.packets.EntityRelMove;
import net.rush.protocol.packets.EntityTeleport;

public abstract class RushTrackeableEntity extends RushEntity {

	protected RushTrackeableEntity(RushWorld world) {
		super(world);
	}

	@Override
	public Packet createUpdateMessage() {		
		boolean moved = hasMoved();
		boolean rotated = hasRotated();

		int x = position.getPixelX();
		int y = position.getPixelY();
		int z = position.getPixelZ();

		int dx = x - prevPosition.getPixelX();
		int dy = y - prevPosition.getPixelY();
		int dz = z - prevPosition.getPixelZ();

		boolean teleported = dx > Byte.MAX_VALUE || dy > Byte.MAX_VALUE || dz > Byte.MAX_VALUE || dx < Byte.MIN_VALUE || dy < Byte.MIN_VALUE || dz < Byte.MIN_VALUE;

		int yaw = position.getIntYaw();
		int pitch = position.getIntPitch();

		if (moved && teleported)
			return new EntityTeleport(id, x, y, z, yaw, pitch);
		else if (moved && rotated)
			return new EntityLookRelMove(id, (byte)dx, (byte)dy, (byte)dz, (byte)yaw, (byte)pitch);
		else if (moved)
			return new EntityRelMove(id, dx, dy, dz);
		else if (rotated)
			return new EntityLook(id, yaw, pitch);

		return null;
	}
}

