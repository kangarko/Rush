package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;

public class PacketStatusRequest extends Packet {

	@Override
	public String getToStringDescription() {
		return null;
	}

	public int getOpcode() {
		return 0;
	}

	@Override
	public void read17(ByteBufInputStream input) {
	}

	@Override
	public void write17(ByteBufOutputStream output) {
	}

}
