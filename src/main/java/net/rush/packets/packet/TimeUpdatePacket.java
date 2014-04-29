package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class TimeUpdatePacket extends Packet {
	public TimeUpdatePacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.LONG, order = 0)
	private long worldAge;
	@Serialize(type = Type.LONG, order = 1)
	private long time;

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

	@Override
	public void read17(ByteBufInputStream input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write17(ByteBufOutputStream output) {
		// TODO Auto-generated method stub

	}
}
