package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class TabCompletePacket extends Packet {
	
	public TabCompletePacket() {
	}

	@Serialize(type = Type.STRING, order = 0)
	private String text;

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

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		text = readString(input, 65000, false);
	}
	
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		writeString(text, output, false);
	}
}
