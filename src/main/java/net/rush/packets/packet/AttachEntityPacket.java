package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class AttachEntityPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.INT, order = 1)
	private int vehicleId;
	@Serialize(type = Type.UNSIGNED_BYTE, order = 2)
	private byte leash;

	public AttachEntityPacket() {
	}

	public AttachEntityPacket(int entityId, int vehicleId, boolean leashed) {
		super();
		this.entityId = entityId;
		this.vehicleId = vehicleId;
		leash = (byte) (leashed ? 1 : 0);
	}

	public int getOpcode() {
		return 0x27;
	}

	public int getEntityId() {
		return entityId;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public byte getLeash() {
		return leash;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",vehicleId=\"%d\"", entityId, vehicleId);
	}

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		entityId = input.readInt();
		vehicleId = input.readInt();
		leash = (byte) (input.readBoolean() ? 1 : 0);
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeInt(entityId);
		output.writeInt(vehicleId);
		output.writeBoolean(leash == 1 ? true : false);
	}
}
