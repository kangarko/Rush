package net.rush.packets.packet;

import java.io.IOException;

import org.json.simple.JSONObject;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class KickPacket extends Packet {
	@Serialize(type = Type.STRING, order = 0)
	private String reason;

	public KickPacket() {
	}

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

	@Override
	public void read18(ByteBufInputStream input) throws IOException {
		readString18(input, 256, false);
	}

	@Override
	public void write18(ByteBufOutputStream output) throws IOException {
		JSONObject json = new JSONObject();
		json.put("text", reason);
		writeString(json.toJSONString(), output, false);
	}
}
