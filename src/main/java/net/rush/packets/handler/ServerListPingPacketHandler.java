package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.ServerListPingPacket;
import net.rush.packets.packet.impl.KickPacketImpl;

/**
 * A {@link PacketHandler} which disconnects clients when they send a
 * {@link KickMessage} to the server.

 */
public final class ServerListPingPacketHandler extends PacketHandler<ServerListPingPacket> {

	@Override
	public void handle(Session session, Player player, ServerListPingPacket message) {
		session.send(new KickPacketImpl("[1.6.4] Rush server" + "\u00A7" + session.getServer().getWorld().getRushPlayers().size() + "\u00A7" + "20"));
	}

}

