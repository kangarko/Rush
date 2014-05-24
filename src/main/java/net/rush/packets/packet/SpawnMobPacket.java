package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.model.Position;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;
import net.rush.util.Parameter;

public class SpawnMobPacket extends Packet {
	
	public SpawnMobPacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte entityType;
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
	@Serialize(type = Type.BYTE, order = 7)
	private byte headYaw;
	@Serialize(type = Type.SHORT, order = 8)
	private short velocityX;
	@Serialize(type = Type.SHORT, order = 9)
	private short velocityY;
	@Serialize(type = Type.SHORT, order = 10)
	private short velocityZ;
	@Serialize(type = Type.ENTITY_METADATA, order = 11)
	private Parameter<?>[] metadata;

	public SpawnMobPacket(int entityId, byte entityType, Position pos, byte yaw, byte pitch, byte headYaw, Position velocity, Parameter<?>[] metadata) {
		super();
		this.entityId = entityId;
		this.entityType = entityType;
		x = (int) pos.x;
		y = (int) pos.y;
		z = (int) pos.z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.headYaw = headYaw;
		velocityX = (short) velocity.x;
		velocityY = (short) velocity.y;
		velocityZ = (short) velocity.z;
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

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		writeVarInt(entityId, output);
		output.writeByte(entityType);
		output.writeInt(x);
		output.writeInt(y);
		output.writeInt(z);
		output.writeByte(pitch);
		output.writeByte(headYaw);
		output.writeByte(yaw);
		output.writeShort(velocityX);
		output.writeShort(velocityY);
		output.writeShort(velocityZ);
		writeMetadata(output, metadata);
	}
}
