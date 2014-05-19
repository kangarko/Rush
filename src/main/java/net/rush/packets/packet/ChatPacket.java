package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;
import net.rush.util.JsonUtils;

public class ChatPacket extends Packet {

	public ChatPacket() {
	}

	@Serialize(type = Type.JSON_CHAT, order = 0)
	private String message;

	public ChatPacket(String message) {
		super();
		this.message = message;
	}

	public int getOpcode() {
		return 0x03;
	}

	public String getMessage() {
		return message;
	}

	public String getToStringDescription() {
		return String.format("message=\"%s\"", message);
	}

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		message = readString(input, 65000, false);
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		writeString(JsonUtils.chatMessageToJson(message), output, false);
	}
}
