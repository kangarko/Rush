package net.rush.packets.handler;

import net.rush.Server;
import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.KickPacket;
import net.rush.packets.packet.impl.PlayerListItemPacketImpl;

/**
 * A {@link PacketHandler} which disconnects clients when they send a
 * {@link KickMessage} to the server.

 */
public final class KickPacketHandler extends PacketHandler<KickPacket> {

	@Override
	public void handle(Session session, Player player, KickPacket message) {
		session.disconnect("Goodbye!");
		for(Player pl : session.getServer().getWorld().getRushPlayers()) {
			pl.getSession().send(new PlayerListItemPacketImpl(player.getName(), false, (short)100));
		}
		Server.getLogger().info(player.getName() + " lost connection: " + message.getReason());
	}

}

