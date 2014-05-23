package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.CreativeInventoryActionPacket;

public final class CreativeInventoryActionPacketHandler extends PacketHandler<CreativeInventoryActionPacket> {

	@Override
	public void handle(Session session, Player player, CreativeInventoryActionPacket message) {
				
		if(message.getSlot() == -1)
			player.throwItemFromPlayer(message.getItem(), message.getItem().count);
		else
			player.getInventory().setItem(player.getInventory().getSlotConverter().netToLocal(message.getSlot()), message.getItem());
	}

}

