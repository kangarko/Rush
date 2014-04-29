package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.PacketPingTime;

/**
 * A {@link PacketHandler} which disconnects clients when they send a
 * {@link KickMessage} to the server.
 */
public final class PacketPingTimeHandler extends PacketHandler<PacketPingTime> {

	@Override
	public void handle(Session session, Player player, PacketPingTime packet) {
		session.send(new PacketPingTime(packet.time));
	}

}

