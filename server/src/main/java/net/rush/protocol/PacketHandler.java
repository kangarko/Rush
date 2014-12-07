package net.rush.protocol;

import java.util.logging.Logger;

import net.rush.api.ChatColor;
import net.rush.model.Session;
import net.rush.protocol.packets.Handshake;
import net.rush.protocol.packets.LoginStart;
import net.rush.protocol.packets.StatusPing;
import net.rush.protocol.packets.StatusRequest;
import net.rush.protocol.packets.StatusResponse;
import net.rush.utils.JsonUtils;

public class PacketHandler {

	private Logger logger = Logger.getLogger("Minecraft");
	
	public <T extends Packet> void handle(Session con, T packet) {
		try {			
			getClass().getMethod("handle", Session.class, packet.getClass()).invoke(this, con, packet);
			System.out.println("Handling packet: " + packet);
		} catch (NoSuchMethodException ex) {
			logger.info("Missing handler for packet: " + packet.getClass().getSimpleName());
		
		} catch (ReflectiveOperationException ex) {
			ex.printStackTrace();
		}
	}
	
	public void handle(Session con, Handshake p) {
		
	}
	
	public void handle(Session session, LoginStart p) {
		session.disconnect("Not implemented!");
	}
	
	public void handle(Session session, StatusPing p) {
		session.sendPacket(new StatusPing(p.getTime()));
	}
	
	public void handle(Session con, StatusRequest p) {
		ServerPing response = new ServerPing(new ServerPing.Protocol("1.7.10", 5), ChatColor.GOLD + "Hello World" + ChatColor.RESET + "\nThis is a Test :)", "", new ServerPing.Players(20, 0));
		con.sendPacket(new StatusResponse(JsonUtils.jsonizeServerPing(response)));
	}
}
