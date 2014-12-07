package net.rush.protocol;

import java.util.logging.Logger;

import net.rush.api.ChatColor;
import net.rush.api.Difficulty;
import net.rush.api.Environment;
import net.rush.api.GameMode;
import net.rush.model.Session;
import net.rush.model.entity.RushPlayer;
import net.rush.protocol.packets.Handshake;
import net.rush.protocol.packets.JoinGame;
import net.rush.protocol.packets.LoginStart;
import net.rush.protocol.packets.LoginSuccess;
import net.rush.protocol.packets.StatusPing;
import net.rush.protocol.packets.StatusRequest;
import net.rush.protocol.packets.StatusResponse;

public class PacketHandler {

	private Logger logger = Logger.getLogger("Minecraft");
	
	public <T extends Packet> void handle(Session session, T packet) {
		try {			
			getClass().getMethod("handle", Session.class, packet.getClass()).invoke(this, session, packet);
			System.out.println("Handling packet: " + packet);
		} catch (NoSuchMethodException ex) {
			logger.info("Missing handler for packet: " + packet.getClass().getSimpleName());
		
		} catch (ReflectiveOperationException ex) {
			ex.printStackTrace();
		}
	}
	
	public void handle(Session session, Handshake p) {
		
	}
	
	public void handle(Session session, LoginStart p) {
		session.sendPacket(new LoginSuccess("0-0-0-0-0", p.getName()));
		session.sendPacket(new JoinGame(0, "DEFAULT", GameMode.SURVIVAL, Environment.NORMAL, Difficulty.NORMAL, 256, 20, false));
		
		session.setPlayer(new RushPlayer(session, p.getName()));
	}
	
	public void handle(Session session, StatusPing p) {
		session.sendPacket(new StatusPing(p.getTime()));
	}
	
	public void handle(Session session, StatusRequest p) {
		ServerPing response = new ServerPing(new ServerPing.Protocol("1.7.10", 5), ChatColor.GOLD + "Hello World" + ChatColor.RESET + "\nThis is a Test :)", "", new ServerPing.Players(20, 0));
		session.sendPacket(new StatusResponse(response));
	}
}
