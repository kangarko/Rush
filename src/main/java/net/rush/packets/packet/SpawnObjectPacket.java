package net.rush.packets.packet;

import net.rush.model.Position;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SpawnObjectPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private final byte type;
	@Serialize(type = Type.INT, order = 2)
	private final int x;
	@Serialize(type = Type.INT, order = 3)
	private final int y;
	@Serialize(type = Type.INT, order = 4)
	private final int z;
	@Serialize(type = Type.BYTE, order = 5)
	private final byte pitch;
	@Serialize(type = Type.BYTE, order = 6)
	private final byte yaw;
	@Serialize(type = Type.INT, order = 7)
	private final int integer;

	/* Unused yet. If the number of "integer" isn´t 0 than following will also be send:
	@Serialize(type = Type.SHORT, order = 8) // FIXME is it SHORT or CONDITIONAL SHORT? In notchian server the datainput read it as a short so I dunno.
	private final short speedZ;
	@Serialize(type = Type.SHORT, order = 9)
	private final short speedY;
	@Serialize(type = Type.SHORT, order = 10)
	private final short shortspeedZ;
	*/

	public SpawnObjectPacket(int entityId, byte type, Position pos, byte pitch, byte yaw) {
		super();
		this.entityId = entityId;
		this.type = type;
		x = (int) pos.getX();
		y = (int) pos.getY();
		z = (int) pos.getZ();
		this.pitch = pitch;
		this.yaw = yaw;
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
