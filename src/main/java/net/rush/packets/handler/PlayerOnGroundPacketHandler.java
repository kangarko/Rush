package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.PlayerOnGroundPacket;

public final class PlayerOnGroundPacketHandler extends PacketHandler<PlayerOnGroundPacket> {

	@Override
	public void handle(Session session, Player player, PlayerOnGroundPacket message) {
		if(player == null)
			return;
		player.setOnGround(message.getOnGround());
	}

}

