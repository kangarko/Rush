package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityRelMovePacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private final byte diffX;
	@Serialize(type = Type.BYTE, order = 2)
	private final byte diffY;
	@Serialize(type = Type.BYTE, order = 3)
	private final byte diffZ;

	public EntityRelMovePacket(int entityId, byte diffX, byte diffY, byte diffZ) {
		super();
		this.entityId = entityId;
		this.diffX = diffX;
		this.diffY = diffY;
		this.diffZ = diffZ;
	}

	public int getOpcode() {
		return 0x1F;
	}

	public int getEntityId() {
		return entityId;
	}

	public byte getDiffX() {
		return diffX;
	}

	public byte getDiffY() {
		return diffY;
	}

	public byte getDiffZ() {
		return diffZ;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",diffX=\"%d\",diffY=\"%d\",diffZ=\"%d\"", entityId, diffX, diffY, diffZ);
	}
}
