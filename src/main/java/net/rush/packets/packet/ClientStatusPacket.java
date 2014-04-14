package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ClientStatusPacket extends Packet {
	@Serialize(type = Type.BYTE, order = 0)
	private final byte payload;

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
}
