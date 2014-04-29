package net.rush.packets.packet;

import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;

public class PacketLoginRequest extends Packet {

	public PacketLoginRequest() {
		// TODO Auto-generated constructor stub
	}

	public String name;

	@Override
	public String getToStringDescription() {
		return name == null ? null : "name=" + name;
	}

	@Override
	public int getOpcode() {
		return 0;
	}

	@Override
	public void read18(ByteBufInputStream input) throws IOException {
		name = readString18(input, 256, false);
	}

	@Override
	public void write18(ByteBufOutputStream output) throws IOException {
		writeString(name, output, false);
	}

}
