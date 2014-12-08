package net.rush.protocol;

import java.util.logging.Logger;

import net.rush.api.ChatColor;
import net.rush.api.Difficulty;
import net.rush.api.Environment;
import net.rush.api.GameMode;
import net.rush.model.Session;
import net.rush.model.entity.RushPlayer;
import net.rush.protocol.packets.ChatMessage;
import net.rush.protocol.packets.EntityHeadLook;
import net.rush.protocol.packets.Handshake;
import net.rush.protocol.packets.JoinGame;
import net.rush.protocol.packets.KeepAlive;
import net.rush.protocol.packets.LoginStart;
import net.rush.protocol.packets.LoginSuccess;
import net.rush.protocol.packets.PlayerLook;
import net.rush.protocol.packets.PlayerLookAndPosition;
import net.rush.protocol.packets.PlayerOnGround;
import net.rush.protocol.packets.PlayerPosition;
import net.rush.protocol.packets.StatusPing;
import net.rush.protocol.packets.StatusRequest;
import net.rush.protocol.packets.StatusResponse;

public class PacketHandler {

	private Logger logger = Logger.getLogger("Minecraft");
	
	public <T extends Packet> void handle(Session session, T packet) {
		try {			
			getClass().getMethod("handle", Session.class, packet.getClass()).invoke(this, session, packet);
		} catch (NoSuchMethodException ex) {
			logger.info("Missing handler for packet: " + packet.getClass().getSimpleName());
		
		} catch (ReflectiveOperationException ex) {
			ex.printStackTrace();
		}
	}
	
	public void handle(Session session, KeepAlive packet) {
		System.out.println("Handling packet: " + packet);
		if (session.pingToken == packet.getToken())
			session.pong();
	}
	
	public void handle(Session session, ChatMessage packet) {
		session.server.broadcastMessage(session.player.name + ": " + packet.getMessage());
	}
	
	public void handle(Session session, Handshake packet) {
		System.out.println("Handling packet: " + packet);
	}
	
	public void handle(Session session, PlayerPosition packet) {
		handlePosition(session, true, false, packet);
	}
	
	public void handle(Session session, PlayerLook packet) {
		handlePosition(session, false, true, packet);
	}
	
	public void handle(Session session, PlayerLookAndPosition packet) {
		handlePosition(session, true, true, packet);
	}
	
	public void handle(Session session, PlayerOnGround packet) {
		handlePosition(session, false, false, packet);
	}
	
	public void handlePosition(Session session, boolean hasPos, boolean hasLook, PlayerOnGround packet) {		
		if (hasPos)
			session.player.setPosition(packet.getX(), packet.getYOrStance(), packet.getZ());
		
		if (hasLook) {
			session.player.position.setRotation(packet.getYaw(), packet.getPitch());
			session.server.sessionRegistry.broadcastPacket(new EntityHeadLook(session.player.id, (int)session.player.position.yaw)); // TODO Send to self? +Fix incorrect yaw.
		}
			
		session.player.onGround = packet.isOnGround();
	}
	
	public void handle(Session session, LoginStart packet) {
		System.out.println("Handling packet: " + packet);
		
		if(session.server.getPlayer(packet.getName()) != null)
			session.server.getPlayer(packet.getName()).session.disconnect("You are logged from another location");
		
		session.sendPacket(new LoginSuccess("0-0-0-0-0", packet.getName()));
		session.sendPacket(new JoinGame(0, "DEFAULT", GameMode.SURVIVAL, Environment.NORMAL, Difficulty.NORMAL, 256, 20, false));		
		session.setPlayer(new RushPlayer(session, packet.getName()));
	}
	
	public void handle(Session session, StatusPing packet) {
		System.out.println("Handling packet: " + packet);
		session.sendPacket(new StatusPing(packet.getTime()));
	}
	
	public void handle(Session session, StatusRequest packet) {
		System.out.println("Handling packet: " + packet);
		ServerPing response = new ServerPing(new ServerPing.Protocol("1.7.10", 5), ChatColor.GOLD + "Hello World" + ChatColor.RESET + "\nThis is a Test :)", "", new ServerPing.Players(20, 0));
		session.sendPacket(new StatusResponse(response));
	}
}
