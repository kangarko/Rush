package net.rush.packets.handler;

import net.rush.model.LivingEntity;
import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.UseEntityPacket;

public final class UseEntityPacketHandler extends PacketHandler<UseEntityPacket> {

	@Override
	public void handle(Session session, Player player, UseEntityPacket message) {
		
		LivingEntity en = (LivingEntity) session.getServer().getWorld().getEntities().getEntity(message.getTargetEntityId());
		
		if(message.getRightclick())
			en.onPlayerInteract(player);
		else
			en.onPlayerHit(player);
	}

}

