package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class CloseWindowPacket extends Packet {
	@Serialize(type = Type.BYTE, order = 0)
	private final byte windowId;

	public CloseWindowPacket(byte windowId) {
		super();
		this.windowId = windowId;
	}

	public int getOpcode() {
		return 0x65;
	}

	public byte getWindowId() {
		return windowId;
	}

	public String getToStringDescription() {
		return String.format("windowId=\"%d\"", windowId);
	}
}
