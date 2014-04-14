package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SetExperiencePacket extends Packet {
	@Serialize(type = Type.FLOAT, order = 0)
	private final float experienceBar;
	@Serialize(type = Type.SHORT, order = 1)
	private final short level;
	@Serialize(type = Type.SHORT, order = 2)
	private final short totalExperience;

	public SetExperiencePacket(float experienceBar, short level, short totalExperience) {
		super();
		this.experienceBar = experienceBar;
		this.level = level;
		this.totalExperience = totalExperience;
	}

	public int getOpcode() {
		return 0x2B;
	}

	public float getExperienceBar() {
		return experienceBar;
	}

	public short getLevel() {
		return level;
	}

	public short getTotalExperience() {
		return totalExperience;
	}

	public String getToStringDescription() {
		return String.format("experienceBar=\"%d\",level=\"%d\",totalExperience=\"%d\"", experienceBar, level, totalExperience);
	}
}
