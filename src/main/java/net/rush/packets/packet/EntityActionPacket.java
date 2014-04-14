package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityActionPacket extends Packet {

	public static final int ACTION_CROUCH = 1;
	public static final int ACTION_UNCROUCH = 2;
	public static final int ACTION_LEAVE_BED = 3;
	public static final int START_SPRINTING = 4;
	public static final int STOP_SPRINTING = 5;

	@Serialize(type = Type.INT, order = 0)
	private final int entityId;
	@Serialize(type = Type.BYTE, order = 1)
	private final byte actionId;
	@Serialize(type = Type.INT, order = 2)
	private final int horseJumpBoost;

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
}
