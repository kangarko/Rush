package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SpawnPaintingPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int entityId;
	@Serialize(type = Type.STRING, order = 1)
	private final String title;
	@Serialize(type = Type.INT, order = 2)
	private final int x;
	@Serialize(type = Type.INT, order = 3)
	private final int y;
	@Serialize(type = Type.INT, order = 4)
	private final int z;
	@Serialize(type = Type.INT, order = 5)
	private final int direction;

	public SpawnPaintingPacket(int entityId, String title, int x, int y, int z, int direction) {
		super();
		this.entityId = entityId;
		this.title = title;
		this.x = x;
		this.y = y;
		this.z = z;
		this.direction = direction;
	}

	public int getOpcode() {
		return 0x19;
	}

	public int getEntityId() {
		return entityId;
	}

	public String getTitle() {
		return title;
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

	public int getDirection() {
		return direction;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",title=\"%s\",x=\"%d\",y=\"%d\",z=\"%d\",direction=\"%d\"", entityId, title, x, y, z, direction);
	}
}
