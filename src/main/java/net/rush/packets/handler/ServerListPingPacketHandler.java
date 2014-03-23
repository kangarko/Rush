package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.ServerListPingPacket;

/**
 * A {@link PacketHandler} which disconnects clients when they send a
 * {@link KickMessage} to the server.

 */
public final class ServerListPingPacketHandler extends PacketHandler<ServerListPingPacket> {

	@Override
	public void handle(Session session, Player player, ServerListPingPacket message) {
		// moved into PluginMessageHandler.java
	}

}

