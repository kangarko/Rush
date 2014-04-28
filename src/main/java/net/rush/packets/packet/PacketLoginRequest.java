package net.rush.packets.packet;

import net.rush.packets.Packet;

public class PacketLoginRequest extends Packet {

    public String name;

	@Override
	public String getToStringDescription() {
		return name == null ? null : "name=" + name;
	}

	@Override
	public int getOpcode() {
		return 0;
	}

}
