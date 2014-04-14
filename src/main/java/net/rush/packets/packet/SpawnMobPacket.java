package net.rush.packets.packet;

import net.rush.model.Position;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;
import net.rush.util.Parameter;

public class SpawnMobPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private final byte entityType;
	@Serialize(type = Type.INT, order = 2)
	private final int x;
	@Serialize(type = Type.INT, order = 3)
	private final int y;
	@Serialize(type = Type.INT, order = 4)
	private final int z;
	@Serialize(type = Type.BYTE, order = 5)
	private final byte yaw;
	@Serialize(type = Type.BYTE, order = 6)
	private final byte pitch;
	@Serialize(type = Type.BYTE, order = 7)
	private final byte headYaw;
	@Serialize(type = Type.SHORT, order = 8)
	private final short velocityX;
	@Serialize(type = Type.SHORT, order = 9)
	private final short velocityY;
	@Serialize(type = Type.SHORT, order = 10)
	private final short velocityZ;
	@Serialize(type = Type.ENTITY_METADATA, order = 11)
	private final Parameter<?>[] metadata;

	public SpawnMobPacket(int entityId, byte entityType, Position pos, byte yaw, byte pitch, byte headYaw, Position velocity, Parameter<?>[] metadata) {
		super();
		this.entityId = entityId;
		this.entityType = entityType;
		x = (int) pos.getX();
		y = (int) pos.getY();
		z = (int) pos.getZ();
		this.yaw = yaw;
		this.pitch = pitch;
		this.headYaw = headYaw;
		velocityX = (short) velocity.getX();
		velocityY = (short) velocity.getY();
		velocityZ = (short) velocity.getZ();
		this.metadata = metadata;
	}

	public int getOpcode() {
		return 0x18;
	}

	public int getEntityId() {
		return entityId;
	}

	public byte getEntityType() {
		return entityType;
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

	public byte getYaw() {
		return yaw;
	}

	public byte getPitch() {
		return pitch;
	}

	public byte getHeadYaw() {
		return headYaw;
	}

	public short getVelocityX() {
		return velocityX;
	}

	public short getVelocityY() {
		return velocityY;
	}

	public short getVelocityZ() {
		return velocityZ;
	}

	public Parameter<?>[] getMetadata() {
		return metadata;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",entityType=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\"," + "yaw=\"%d\",pitch=\"%d\",headYaw=\"%d\",metadata=\"%s\"", entityId, entityType, x, y, z, yaw, pitch, headYaw, metadata);
	}
}
