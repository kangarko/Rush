package net.rush.packets.packet;

import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;

public class PacketLoginRequest extends Packet {

	public PacketLoginRequest() {
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
	public void read17(ByteBufInputStream input) throws IOException {
		name = readString(input, 16, false);
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		throw new IOException("cannot write " + this);
	}

}
