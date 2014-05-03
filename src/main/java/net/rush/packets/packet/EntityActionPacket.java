package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityActionPacket extends Packet {

	public EntityActionPacket() {
	}

	public static final int ACTION_CROUCH = 1;
	public static final int ACTION_UNCROUCH = 2;
	public static final int ACTION_LEAVE_BED = 3;
	public static final int START_SPRINTING = 4;
	public static final int STOP_SPRINTING = 5;

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private byte actionId;
	@Serialize(type = Type.INT, order = 2)
	private int horseJumpBoost;

	public EntityActionPacket(int entityId, byte actionId, int horseJumpBoost) {
		super();
		this.entityId = entityId;
		this.actionId = actionId;
		this.horseJumpBoost = horseJumpBoost;
	}

	public int getOpcode() {
		return 0x13;
	}

	public int getEntityId() {
		return entityId;
	}

	public byte getActionId() {
		return actionId;
	}

	public int getHorseJumpBoost() {
		return horseJumpBoost;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",actionId=\"%d\"", entityId, actionId);
	}

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		entityId = input.readInt();
		actionId = input.readByte();
		horseJumpBoost = input.readInt();
	}
}
