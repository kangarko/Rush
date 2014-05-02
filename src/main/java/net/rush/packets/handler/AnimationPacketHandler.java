package net.rush.packets.handler;

import net.rush.model.Entity;
import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.Packet;
import net.rush.packets.packet.AnimationPacket;
import net.rush.util.enums.AnimationEnum;

/**
 * A {@link PacketHandler} which handles {@link Entity} animations.
 */
public final class AnimationPacketHandler extends PacketHandler<AnimationPacket> {

	@Override
	public void handle(Session session, Player player, AnimationPacket message) {
		Packet packet = new AnimationPacket(player.getId(), AnimationEnum.fromId(message.getAnimation()));
		for (Player p : player.getWorld().getPlayers())
			if (p != player)
				p.getSession().send(packet);
	}

}

