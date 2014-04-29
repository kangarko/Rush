package net.rush.packets.packet;

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

	public PacketPingTime(long l) {
		time = l;
	}

	@Override
	public String getToStringDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getOpcode() {
		return 1;
	}

	@Override
	public void read18(ByteBufInputStream input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write18(ByteBufOutputStream output) {
		// TODO Auto-generated method stub

	}

}
