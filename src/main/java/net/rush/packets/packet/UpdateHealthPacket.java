package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class UpdateHealthPacket extends Packet {
	@Serialize(type = Type.FLOAT, order = 0)
	private final float health;
	@Serialize(type = Type.SHORT, order = 1)
	private final short food;
	@Serialize(type = Type.FLOAT, order = 2)
	private final float saturation;

	public UpdateHealthPacket(float health, short food, float saturation) {
		super();
		this.health = health;
		this.food = food;
		this.saturation = saturation;
	}

	public int getOpcode() {
		return 0x08;
	}

	public float getHealth() {
		return health;
	}

	public short getFood() {
		return food;
	}

	public float getSaturation() {
		return saturation;
	}

	public String getToStringDescription() {
		return String.format("health=\"%d\",food=\"%d\",saturation=\"%d\"", health, food, saturation);
	}
}
