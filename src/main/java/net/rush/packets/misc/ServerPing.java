package net.rush.packets.misc;

public class ServerPing {

	private Protocol version;
	private String description;
	private String favicon;
	private Players players;

	public ServerPing() {
	}

	public ServerPing(Protocol version, Players players, String description, String favicon) {
		this.version = version;
		this.description = description;
		this.favicon = favicon;
		this.players = players;
	}

	public Protocol getVersion() {
		return version;
	}

	public String getDescription() {
		return description;
	}

	public String getFavicon() {
		return favicon;
	}

	public Players getPlayers() {
		return players;
	}
	
	public static class Protocol {
		public Protocol(String name, int protocol) {
			this.name = name;
			this.protocol = protocol;
		}

		private String name;
		private int protocol;
		
		public String getName() {
			return name;
		}
		
		public int getProtocol() {
			return protocol;
		}
	}

	public static class Players {
		
		public Players(int max, int online) {
			this.max = max;
			this.online = online;
		}

		private int max;
		private int online;

		public int getMax() {
			return max;
		}
		
		public int getOnline() {
			return online;
		}
	}
}