package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.KickPacket;
import net.rush.packets.packet.ServerListPingPacket;

/**
 * A {@link PacketHandler} which disconnects clients when they send a
 * {@link KickMessage} to the server.
 */
public final class ServerListPingPacketHandler extends PacketHandler<ServerListPingPacket> {

	@Override
	public void handle(Session session, Player player, ServerListPingPacket message) {
		// 1.3 and older -> session.send(new KickPacketImpl("[1.6.4] Rush server" + "\u00A7" + session.getServer().getWorld().getPlayers().size() + "\u00A7" + "20"));
		// 1.4 - 1.5 -> String old = 1+"\00"+78+"\00"+"1.4.7"+"\00"+ChatColor.translateAlternateColorCodes('&', "&bRush server &6(Working MOTD!)")+"\00"+session.getServer().getWorld().getPlayers().size()+"\00"+20;
		
		Object[] infos = { 1, 78, "1.6.4", session.getServer().getProperties().motd, session.getServer().getWorld().getPlayers().size(), session.getServer().getProperties().maxPlayers };
		StringBuilder builder = new StringBuilder();
		
		for (Object info : infos) {
			if (builder.length() == 0)
				builder.append('\u00A7');
			else
				builder.append('\0');
				
			builder.append(info.toString().replace("\0", ""));
		}

		session.send(new KickPacket(builder.toString()));
	}

}

