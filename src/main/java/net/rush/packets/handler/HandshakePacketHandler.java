package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.net.Session.State;
import net.rush.packets.packet.HandshakePacket;
import net.rush.packets.packet.LoginPacket;
import net.rush.util.enums.Dimension;

import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.WorldType;

/**
 * A {@link PacketHandler} which performs the initial handshake with clients.
 */
public final class HandshakePacketHandler extends PacketHandler<HandshakePacket> {

	@Override
	public void handle(Session session, Player player, HandshakePacket message) {
		Session.State state = session.getState();
		if (state == Session.State.EXCHANGE_HANDSHAKE) {
			session.setState(State.EXCHANGE_IDENTIFICATION);
			session.send(new LoginPacket(0, WorldType.NORMAL, GameMode.CREATIVE, Dimension.NORMAL, Difficulty.NORMAL, session.getServer().getWorld().getMaxHeight(), 30));
			session.setPlayer(new Player(session, message.getUsername(), GameMode.CREATIVE));
		} else {
			session.disconnect("Handshake already exchanged.");
		}
	}

}

