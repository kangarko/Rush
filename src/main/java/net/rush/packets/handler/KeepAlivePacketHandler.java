package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.KeepAlivePacket;

/**
 * A {@link PacketHandler} which performs the initial identification with
 * clients.

 */
public final class KeepAlivePacketHandler extends PacketHandler<KeepAlivePacket> {

	@Override
	public void handle(Session session, Player player, KeepAlivePacket message) {
		if (session.getPingMessageId() == message.getToken())
            session.pong();        
	}

}

