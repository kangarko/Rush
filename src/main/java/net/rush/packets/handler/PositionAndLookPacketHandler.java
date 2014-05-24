package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.model.Position;
import net.rush.model.Rotation;
import net.rush.net.Session;
import net.rush.packets.packet.EntityHeadLookPacket;
import net.rush.packets.packet.PlayerPositionAndLookPacket;

/**
 * A {@link PacketHandler} that updates a {@link Player}'s {@link Position}
 * and {@link Rotation} when the server receives a
 * {@link PositionRotationMessage}.

 */
public final class PositionAndLookPacketHandler extends PacketHandler<PlayerPositionAndLookPacket> {

	@Override
	public void handle(Session session, Player player, PlayerPositionAndLookPacket message) {
		if (player == null)
			return;

		player.setPosition(message.getX(), message.getYOrStance(), message.getZ());
		player.setRotation(message.getYaw(), message.getPitch());
		
		session.getServer().broadcastPacket(new EntityHeadLookPacket(player.getId(), (byte) player.getRotation().getIntYaw()));
	}

}

