package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;

public class PacketStatusRequest extends Packet {

	public PacketStatusRequest() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getToStringDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getOpcode() {
		// TODO Auto-generated method stub
		return 0;
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
