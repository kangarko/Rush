package net.rush.packets.packet.impl;

import net.rush.packets.packet.ChatPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

import org.bukkit.ChatColor;
import org.json.simple.JSONObject;

public class ChatPacketImpl extends AbstractPacket implements ChatPacket {
	@Serialize(type = Type.STRING, order = 0)
	private final String message;

	private final String plainText;
	
	@SuppressWarnings("unchecked")
	public ChatPacketImpl(String message) {
		super();
		JSONObject msg = new JSONObject();
		msg.put("text", ChatColor.translateAlternateColorCodes("&".charAt(0), message));
		this.message = msg.toJSONString();
		this.plainText = message;
	}

	@Override
	public int getOpcode() {
		return 0x03;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	public String getPlainMessage() {
		return plainText;
	}

	@Override
	public String getToStringDescription() {
		return String.format("message=\"%s\"", message);
	}
}
