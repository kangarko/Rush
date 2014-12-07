package net.rush.utils;

import net.rush.api.ChatColor;
import net.rush.protocol.ServerPing;

/**
 * Smart, easy and comfortable JSON handling without the need of an additional library.
 * @author kangarko
 * (C) 2014 Patent pending
 */
public class JsonUtils {

	private JsonUtils() {}

	public static String jsonizeServerPing(ServerPing ping) {
		String json = "{";
		json+= "\"version\":{\"name\":\"" + ping.getProtocol().getName() + "\",\"protocol\":" + ping.getProtocol().getProtocol() + "},";
		json+= "\"description\":\"" + ping.getDescription() + "\",";
		json+= "\"favicon\":\"" + ping.getFavicon() + "\",";
		json+= "\"players\":{\"max\":" + ping.getPlayers().getMaximum() + ",\"online\":" + ping.getPlayers().getOnline() + "}}";
		return json;
	}

	public static String jsonizeChat(String str) {
		String json = "{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', str).replace("%Rush", "&3Rush //&f").replace("\"", "\\u0022") + "\"}";

		return json;
	}

	/*public static String chatMessageToJson(Message message) {
		String
		json = "{";

		json+= "\"text\":\"" + message.text + "\",";

		json+= "\"bold\":\"" + message.bold + "\",";
		json+= "\"italic\":\"" + message.italic + "\",";
		json+= "\"underlined\":\"" + message.underlined + "\",";
		json+= "\"strikethrough\":\"" + message.strikethrough + "\",";
		json+= "\"obfuscated\":\"" + message.obfuscated + "\",";

		json+= "\"color\":{\"code\":\"" + message.color.code + "\"" + "},";
		json+= "\"clickEvent\":{\"action\":" + message.clickEvent.action + ",\"value\":" + message.clickEvent.value + "}";

		json+= "}";
		return json;
	}

	@SuppressWarnings("unchecked")
	public static String serverPingToJson(ServerPing ping) {

		JSONObject version = new JSONObject();
		version.put("name", ping.getVersion().getName());
		version.put("protocol", ping.getVersion().getProtocol());

		JSONObject players = new JSONObject();
		players.put("max", ping.getPlayers().getMax());
		players.put("online", ping.getPlayers().getOnline());

		JSONObject json = new JSONObject();
		json.put("version", version);
		json.put("description", ping.getDescription());
		json.put("favicon", ping.getFavicon());
		json.put("players", players);

		return json.toJSONString();
	}*/
}
