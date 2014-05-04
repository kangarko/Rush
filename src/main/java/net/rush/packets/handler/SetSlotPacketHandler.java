package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.SetSlotPacket;

public class SetSlotPacketHandler extends PacketHandler<SetSlotPacket> {

	@Override
	public void handle(Session session, Player player, SetSlotPacket packet) {
		player.getInventory().setItem(packet.getSlot(), packet.getItem());
	}

}
