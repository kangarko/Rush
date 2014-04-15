package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class KickPacket extends Packet {
	@Serialize(type = Type.STRING, order = 0)
	private final String reason;

	public KickPacket(String reason) {
		super();
		this.reason = reason;
	}

	public int getOpcode() {
		return 0xFF;
	}

	public String getReason() {
		return reason;
	}

	public String getToStringDescription() {
		return String.format("reason=\"%s\"", reason);
	}
}
