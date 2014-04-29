package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ClientStatusPacket extends Packet {
	public ClientStatusPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.BYTE, order = 0)
	private byte payload;

	public ClientStatusPacket(byte payload) {
		super();
		this.payload = payload;
	}

	public int getOpcode() {
		return 0xCD;
	}

	public byte getPayload() {
		return payload;
	}

	public String getToStringDescription() {
		return String.format("reason=\"%s\"", payload);
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
