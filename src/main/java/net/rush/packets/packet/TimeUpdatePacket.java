package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class TimeUpdatePacket extends Packet {
	@Serialize(type = Type.LONG, order = 0)
	private final long worldAge;
	@Serialize(type = Type.LONG, order = 1)
	private final long time;

	public TimeUpdatePacket(long worldAge, long time) {
		super();
		this.worldAge = worldAge;
		this.time = time;
	}

	public int getOpcode() {
		return 0x04;
	}

	public long getWorldAge() {
		return worldAge;
	}

	public long getTime() {
		return time;
	}

	public String getToStringDescription() {
		return String.format("time=\"%d\"", time);
	}
}
