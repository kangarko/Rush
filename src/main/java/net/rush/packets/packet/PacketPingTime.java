package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PacketPingTime extends Packet {

	@Serialize(type = Type.LONG, order = 0)
	public long time;

	public PacketPingTime() {}
	
	public PacketPingTime(long l) {
		this.time = l;
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

}
