package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SetExperiencePacket extends Packet {
	public SetExperiencePacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.FLOAT, order = 0)
	private float experienceBar;
	@Serialize(type = Type.SHORT, order = 1)
	private short level;
	@Serialize(type = Type.SHORT, order = 2)
	private short totalExperience;

	public SetExperiencePacket(float experienceBar, short level,
			short totalExperience) {
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
		return String.format(
				"experienceBar=\"%d\",level=\"%d\",totalExperience=\"%d\"",
				experienceBar, level, totalExperience);
	}

	@Override
	public void read17(ByteBufInputStream input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write17(ByteBufOutputStream output) {
		// TODO Auto-generated method stub

	}
}
