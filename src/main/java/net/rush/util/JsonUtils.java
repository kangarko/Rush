package net.rush.util;

import net.rush.packets.misc.ServerPing;

public class JsonUtils {
	
	private JsonUtils() {}
	
	public static String serverPingToJson(ServerPing ping) {
		String json = "{";
		json+= "\"version\":{\"name\":\"" + ping.getVersion().getName() + "\",\"protocol\":" + ping.getVersion().getProtocol() + "},";
		json+= "\"description\":\"" + ping.getDescription() + "\",";
		json+= "\"favicon\":\"" + ping.getFavicon() + "\",";
		json+= "\"players\":{\"max\":" + ping.getPlayers().getMax() + ",\"online\":" + ping.getPlayers().getOnline() + "}}";
		
		return json;
	}
	
	/*@SuppressWarnings("unchecked")
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
