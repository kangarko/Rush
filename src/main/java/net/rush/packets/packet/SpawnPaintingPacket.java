package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SpawnPaintingPacket extends Packet {
	
	public SpawnPaintingPacket() {
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

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		writeVarInt(entityId, output);
		writeString(title, output, false);
		output.writeInt(x);
		output.writeInt(y);
		output.writeInt(z);
		output.writeInt(direction);
	}
}
