package net.rush.packets.packet;

import net.rush.model.Position;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SpawnObjectPacket extends Packet {
	public SpawnObjectPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte type;
	@Serialize(type = Type.INT, order = 2)
	private int x;
	@Serialize(type = Type.INT, order = 3)
	private int y;
	@Serialize(type = Type.INT, order = 4)
	private int z;
	@Serialize(type = Type.BYTE, order = 5)
	private byte pitch;
	@Serialize(type = Type.BYTE, order = 6)
	private byte yaw;
	@Serialize(type = Type.INT, order = 7)
	private int integer;

	public SpawnObjectPacket(int entityId, int type, Position pos, int pitch, int yaw) {
		super();
		this.entityId = entityId;
		this.type = (byte) type;
		x = (int) pos.getPixelX();
		y = (int) pos.getPixelY();
		z = (int) pos.getPixelZ();
		this.pitch = (byte)pitch;
		this.yaw = (byte)yaw;
		integer = 0; // TODO
	}

	public int getOpcode() {
		return 0x17;
	}

	public int getEntityId() {
		return entityId;
	}

	public byte getType() {
		return type;
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

	public byte getPitch() {
		return pitch;
	}

	public byte getYaw() {
		return yaw;
	}

	public int getInteger() {
		return integer;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",type=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\",fireballThrower=\"%d\"," + "fireballSpeedX=\"%d\",fireballSpeedY=\"%d\",fireballSpeedZ=\"%d\"", entityId, type, x, y, z);
	}

}
