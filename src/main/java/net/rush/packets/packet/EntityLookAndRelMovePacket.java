package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityLookAndRelMovePacket extends Packet {
	
	public EntityLookAndRelMovePacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte diffX;
	@Serialize(type = Type.BYTE, order = 2)
	private byte diffY;
	@Serialize(type = Type.BYTE, order = 3)
	private byte diffZ;
	@Serialize(type = Type.BYTE, order = 4)
	private byte yaw;
	@Serialize(type = Type.BYTE, order = 5)
	private byte pitch;

	public EntityLookAndRelMovePacket(int entityId, byte diffX, byte diffY, byte diffZ, byte yaw, byte pitch) {
		super();
		this.entityId = entityId;
		this.diffX = diffX;
		this.diffY = diffY;
		this.diffZ = diffZ;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public int getOpcode() {
		return 0x21;
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

	public byte getYaw() {
		return yaw;
	}

	public byte getPitch() {
		return pitch;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",diffX=\"%d\",diffY=\"%d\",diffZ=\"%d\",yaw=\"%d\",pitch=\"%d\"", entityId, diffX, diffY, diffZ, yaw, pitch);
	}
	
	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		entityId = input.readInt();
		diffX = input.readByte();
		diffY = input.readByte();
		diffZ = input.readByte();
		yaw = input.readByte();
		pitch = input.readByte();
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeInt(entityId);
		output.writeByte(diffX);
		output.writeByte(diffY);
		output.writeByte(diffZ);
		output.writeByte(yaw);
		output.writeByte(pitch);
	}
}
