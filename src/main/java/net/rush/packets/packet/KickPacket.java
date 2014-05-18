package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.misc.ServerPing;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;
import net.rush.util.JsonUtils;

public class KickPacket extends Packet {
	@Serialize(type = Type.STRING, order = 0)
	private String reason;

	private boolean jsonize = true;
	
	public KickPacket() {
	}

	public KickPacket(String reason) {
		this.reason = reason;
	}

	public KickPacket(ServerPing ping) {
		this.reason = JsonUtils.serverPingToJson(ping);
		jsonize = false;
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
	public void read17(ByteBufInputStream input) throws IOException {
		readString(input, 256, false);
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		writeString(!jsonize ? reason : "\"" + reason + "\"", output, false);
	}
	
}
