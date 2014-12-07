package net.rush.api;

public class Rush {
	
	private Rush() {}
	
	private static Server server = null;
	
	public static void setServer(Server server) {
		if (Rush.server != null)
			throw new RuntimeException("Server already set");
		
		Rush.server = server;
	}
	
	public static Server getServer() {
		return server;
	}	
	
}
