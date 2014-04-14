package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class TabCompletePacket extends Packet {
	@Serialize(type = Type.STRING, order = 0)
	private final String text;

	public TabCompletePacket(String text) {
		super();
		this.text = text;
	}

	public int getOpcode() {
		return 0xCB;
	}

	public String getText() {
		return text;
	}

	public String getToStringDescription() {
		return String.format("text=\"%s\"", text);
	}
}
