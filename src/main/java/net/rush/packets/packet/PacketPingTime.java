package net.rush.packets.packet;

import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PacketPingTime extends Packet {

	@Serialize(type = Type.LONG, order = 0)
	public long time;

	public PacketPingTime() {
	}

	public PacketPingTime(long time) {
		this.time = time;
	}

	@Override
	public String getToStringDescription() {
		return "time=" + time;
	}

	@Override
	public int getOpcode() {
		return 1;
	}

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		time = input.readLong();
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeLong(time);
	}

}
