package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import net.rush.packets.Packet;

public class PacketLoginRequest extends Packet {

	public PacketLoginRequest() {
	}

	public String name;

	@Override
	public String getToStringDescription() {
		return name == null ? null : "name=" + name;
	}

	public int getOpcode() {
		return 0;
	}

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		name = readString(input, 16, false);
	}
}
