package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.model.Position;
import net.rush.net.Session;
import net.rush.packets.packet.PlayerPositionPacket;

/**
 * A {@link PacketHandler} that updates a {@link Player}'s {@link Position}
 * when the server receives a {@link PositionMessage}.
 */
public final class PositionPacketHandler extends PacketHandler<PlayerPositionPacket> {

	@Override
	public void handle(Session session, Player player, PlayerPositionPacket message) {
		if (player == null)
			return;

		player.setPosition(message.getX(), message.getY(), message.getZ());
	}

}

