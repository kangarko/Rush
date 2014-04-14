package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

import org.bukkit.ChatColor;
import org.json.simple.JSONObject;

public class ChatPacket extends Packet {
	@Serialize(type = Type.STRING, order = 0)
	private final String message;

	private final String plainText;

	@SuppressWarnings("unchecked")
	public ChatPacket(String message) {
		super();
		JSONObject msg = new JSONObject();
		msg.put("text", ChatColor.translateAlternateColorCodes("&".charAt(0), message));
		this.message = msg.toJSONString();
		plainText = message;
	}

	public int getOpcode() {
		return 0x03;
	}

	public String getMessage() {
		return message;
	}

	public String getPlainMessage() {
		return plainText;
	}

	public String getToStringDescription() {
		return String.format("message=\"%s\"", message);
	}
}
