package net.rush.packets.handler;

import net.rush.model.Entity;
import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.EntityActionPacket;

/**
 * A {@link PacketHandler} which handles {@link Entity} action messages.

 */
public final class EntityActionPacketHandler extends PacketHandler<EntityActionPacket> {

	@Override
	public void handle(Session session, Player player, EntityActionPacket message) {
		switch (message.getActionId()) {
		case EntityActionPacket.ACTION_CROUCH:
			player.setCrouching(true);
			break;
		case EntityActionPacket.ACTION_UNCROUCH:
			player.setCrouching(false);
			break;
		case EntityActionPacket.START_SPRINTING:
			player.setSprinting(true);
			break;
		case EntityActionPacket.STOP_SPRINTING:
			player.setSprinting(false);
			break;
		default:
			// TODO: bed support
			return;
		}
	}

}

