package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityVelocityPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int entityId;
	@Serialize(type = Type.SHORT, order = 1)
	private final short velocityX;
	@Serialize(type = Type.SHORT, order = 2)
	private final short velocityY;
	@Serialize(type = Type.SHORT, order = 3)
	private final short velocityZ;

	public EntityVelocityPacket(int entityId, short velocityX, short velocityY, short velocityZ) {
		super();
		this.entityId = entityId;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.velocityZ = velocityZ;
	}

	public int getOpcode() {
		return 0x1C;
	}

	public int getEntityId() {
		return entityId;
	}

	public short getVelocityX() {
		return velocityX;
	}

	public short getVelocityY() {
		return velocityX;
	}

	public short getVelocityZ() {
		return velocityX;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",velocityX=\"%d\",velocityY=\"%d\",velocityZ=\"%d\"", entityId, velocityX, velocityY, velocityZ);
	}
}
