package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.ClientStatusPacket;

/**
 * A {@link PacketHandler} which handles joining (payload of 0) and respawning (payload of 1).

 */
public final class ClientStatusPacketHandler extends PacketHandler<ClientStatusPacket> {

	@Override
	public void handle(Session session, Player player, ClientStatusPacket message) {
		// TODO handle respawn
		System.out.println("*** CLIENT STATUS ! ***");
	}

}

