package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
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

	/*
	 * Unused yet. If the number of "integer" isn´t 0 than following will also
	 * be send:
	 * 
	 * @Serialize(type = Type.SHORT, order = 8) // FIXME is it SHORT or
	 * CONDITIONAL SHORT? In notchian server the datainput read it as a short so
	 * I dunno. private short speedZ;
	 * 
	 * @Serialize(type = Type.SHORT, order = 9) private short speedY;
	 * 
	 * @Serialize(type = Type.SHORT, order = 10) private short
	 * shortspeedZ;
	 */

	public SpawnObjectPacket(int entityId, byte type, Position pos, byte pitch,
			byte yaw) {
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
		return String
				.format("entityId=\"%d\",type=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\",fireballThrower=\"%d\","
						+ "fireballSpeedX=\"%d\",fireballSpeedY=\"%d\",fireballSpeedZ=\"%d\"",
						entityId, type, x, y, z);
	}

	@Override
	public void read17(ByteBufInputStream input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write17(ByteBufOutputStream output) {
		// TODO Auto-generated method stub

	}
}
