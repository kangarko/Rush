package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.HeldItemChangePacket;

/**
 * A {@link PacketHandler} which handles {@link ChatMessage}s by processing
 * commands or broadcasting messages to every player in the server.

 */
public final class HeldItemChangePacketHandler extends PacketHandler<HeldItemChangePacket> {

	@Override
	public void handle(Session session, Player player, HeldItemChangePacket message) {
		player.getInventory().setHeldItemSlot((int)message.getSlotId());
	}

}

