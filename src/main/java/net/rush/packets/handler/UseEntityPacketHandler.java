package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.UseEntityPacket;


public final class UseEntityPacketHandler extends PacketHandler<UseEntityPacket> {

	@Override
	public void handle(Session session, Player player, UseEntityPacket message) {
		System.out.println(message.toString());
	}

}

