package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SpawnPaintingPacket extends Packet {
	public SpawnPaintingPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.STRING, order = 1)
	private String title;
	@Serialize(type = Type.INT, order = 2)
	private int x;
	@Serialize(type = Type.INT, order = 3)
	private int y;
	@Serialize(type = Type.INT, order = 4)
	private int z;
	@Serialize(type = Type.INT, order = 5)
	private int direction;

	public SpawnPaintingPacket(int entityId, String title, int x, int y, int z,
			int direction) {
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
		return String
				.format("entityId=\"%d\",title=\"%s\",x=\"%d\",y=\"%d\",z=\"%d\",direction=\"%d\"",
						entityId, title, x, y, z, direction);
	}

	@Override
	public void read18(ByteBufInputStream input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write18(ByteBufOutputStream output) {
		// TODO Auto-generated method stub

	}
}
