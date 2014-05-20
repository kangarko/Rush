package net.rush.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import net.rush.ServerProperties;
import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.HandshakePacket;
import net.rush.packets.packet.LoginPacket;
import net.rush.util.enums.Dimension;

public class ThreadLoginVerifier extends Thread {

	final HandshakePacket loginPacket;
	final Session session;

	public ThreadLoginVerifier(Session session, HandshakePacket loginPacket) {
		this.session = session;
		this.loginPacket = loginPacket;
	}

	public void run() {
		try {
			String serverId = session.getServer().serverId;
			URL url = new URL("http://session.minecraft.net/game/checkserver.jsp?user=" + URLEncoder.encode(loginPacket.getUsername(), "UTF-8") + "&serverId=" + URLEncoder.encode(serverId, "UTF-8"));
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String response = reader.readLine();
			reader.close();
			
			System.out.println("(Login verifier) Got response: " + response);
			
			if (response.equals("YES")) {
				ServerProperties prop = session.getServer().getProperties();
				session.send(new LoginPacket(0, prop.levelType, prop.gamemode, Dimension.NORMAL, prop.difficulty, prop.maxBuildHeight, prop.maxPlayers, prop.hardcore));
				session.setPlayer(new Player(session, loginPacket.getUsername()));
			} else
				session.disconnect("Failed to verify username!");
			
		} catch (Exception ex) {
			session.disconnect("Failed to verify username! [Error: " + ex + "]");
			ex.printStackTrace();
		}
	}
}
