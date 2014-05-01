package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.RotationUtils;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityTeleportPacket extends Packet {
	public EntityTeleportPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.INT, order = 1)
	private int x;
	@Serialize(type = Type.INT, order = 2)
	private int y;
	@Serialize(type = Type.INT, order = 3)
	private int z;
	@Serialize(type = Type.BYTE, order = 4)
	private byte yaw;
	@Serialize(type = Type.BYTE, order = 5)
	private byte pitch;

	public EntityTeleportPacket(int entityId, int x, int y, int z, float yaw, float pitch) {
		this(entityId, x, y, z, RotationUtils.floatToByte(yaw), RotationUtils.floatToByte(pitch));
	}

	public EntityTeleportPacket(int entityId, int x, int y, int z, byte yaw, byte pitch) {
		super();
		this.entityId = entityId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public int getOpcode() {
		return 0x22;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public int getEntityId() {
		return entityId;
	}

	public byte getYaw() {
		return yaw;
	}

	public byte getPitch() {
		return pitch;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\",yaw=\"%d\",pitch=\"%d\"", entityId, x, y, z, yaw, pitch);
	}

}
