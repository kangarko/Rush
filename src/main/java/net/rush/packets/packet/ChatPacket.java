package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

import org.bukkit.ChatColor;
import org.json.simple.JSONObject;

public class ChatPacket extends Packet {
	public ChatPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.STRING, order = 0)
	private String message;

	private String plainText;

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

	@Override
	public void read17(ByteBufInputStream input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write17(ByteBufOutputStream output) {
		// TODO Auto-generated method stub

	}
}
