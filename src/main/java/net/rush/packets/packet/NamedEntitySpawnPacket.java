package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.model.Position;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;
import net.rush.util.Parameter;

public class NamedEntitySpawnPacket extends Packet {
	
	public NamedEntitySpawnPacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.STRING, order = 1)
	private String entityName;
	@Serialize(type = Type.INT, order = 2)
	private int x;
	@Serialize(type = Type.INT, order = 3)
	private int y;
	@Serialize(type = Type.INT, order = 4)
	private int z;
	@Serialize(type = Type.BYTE, order = 5)
	private byte yaw;
	@Serialize(type = Type.BYTE, order = 6)
	private byte pitch;
	@Serialize(type = Type.SHORT, order = 7)
	private short currentItem;
	@Serialize(type = Type.ENTITY_METADATA, order = 8)
	private Parameter<?>[] metadata;

	public NamedEntitySpawnPacket(int entityId, String playerName, Position pos, byte yaw, byte pitch, short currentItem, Parameter<?>[] metadata) {
		super();
		this.entityId = entityId;
		entityName = playerName;
		x = (int) pos.x;
		y = (int) pos.y;
		z = (int) pos.z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.currentItem = currentItem;
		this.metadata = metadata;
	}

	public int getOpcode() {
		return 0x14;
	}

	public int getEntityId() {
		return entityId;
	}

	public String getEntityName() {
		return entityName;
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

	public short getCurrentItem() {
		return currentItem;
	}

	public Parameter<?>[] getMetadata() {
		return metadata;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",playerName=\"%s\",x=\"%d\",y=\"%d\",z=\"%d\",yaw=\"%d\",pitch=\"%d\",currentItem=\"%d\"", entityId, entityName, x, y, z, yaw, pitch, currentItem);
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		writeVarInt(entityId, output);
		writeString("invalid", output, false);
		writeString(entityName, output, false);
		output.writeInt(x);
		output.writeInt(y);
		output.writeInt(z);
		output.writeByte(yaw);
		output.writeByte(pitch);
		output.writeShort(currentItem);
		writeMetadata(output, metadata);
	}
	
	@Override
	public void write176(ByteBufOutputStream output) throws IOException {
		writeVarInt(entityId, output);
		writeString("0-0-0-0-0", output, false);
		writeString(entityName, output, false);
		writeVarInt(0, output);
		output.writeInt(x);
		output.writeInt(y);
		output.writeInt(z);
		output.writeByte(yaw);
		output.writeByte(pitch);
		output.writeShort(currentItem);
		writeMetadata(output, metadata);
	};
}
