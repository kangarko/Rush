package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.misc.ServerPing;
import net.rush.packets.misc.ServerPing.Players;
import net.rush.packets.misc.ServerPing.Protocol;
import net.rush.packets.packet.KickPacket;
import net.rush.packets.packet.PacketStatusRequest;

import org.bukkit.ChatColor;

/**
 * A {@link PacketHandler} which disconnects clients when they send a
 * {@link KickMessage} to the server.
 */
public final class PacketStatusRequestHandler extends PacketHandler<PacketStatusRequest> {

	@Override
	public void handle(Session session, Player player, PacketStatusRequest message) {
		
		ServerPing response = new ServerPing(
				new Protocol(session.getClientVersion().getVersion(), session.getClientVersion().getProtocol()), 
				new Players(session.getServer().getProperties().maxPlayers, session.getServer().getWorld().getPlayers().size()),
				session.getServer().getProperties().motd + "\n" + ChatColor.GREEN + "You are displaying Rush on " + session.getClientVersion().toString(), 
				session.getServer().getProperties().favicon);
		
		session.send(new KickPacket(response));
	}

}