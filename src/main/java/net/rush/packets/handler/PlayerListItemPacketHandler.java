package net.rush.packets.handler;

import net.rush.Server;
import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.PlayerListItemPacket;


public final class PlayerListItemPacketHandler extends PacketHandler<PlayerListItemPacket> {

	@Override
	public void handle(Session session, Player player, PlayerListItemPacket message) {
		Server.getLogger().info("got name: " + message.getPlayerName());
	}

}

