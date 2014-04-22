package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.net.Session.State;
import net.rush.packets.packet.HandshakePacket;
import net.rush.packets.packet.LoginPacket;
import net.rush.util.ThreadLoginVerifier;
import net.rush.util.enums.Dimension;

/**
 * A {@link PacketHandler} which performs the initial handshake with clients.
 */
public final class HandshakePacketHandler extends PacketHandler<HandshakePacket> {

	@Override
	public void handle(Session session, Player player, HandshakePacket message) {
		Session.State state = session.getState();
		
		if (state == Session.State.EXCHANGE_HANDSHAKE) {
			session.setState(State.EXCHANGE_IDENTIFICATION);

			if(session.getServer().isInOnlineMode()) {
				new ThreadLoginVerifier(session, message).start();
			} else {
				session.send(new LoginPacket(
						0, 
						session.getServer().getWorldType(),
						session.getServer().getGameMode(), 
						Dimension.NORMAL, 
						session.getServer().getDifficulty(), 
						session.getServer().getWorld().getMaxHeight(), 
						session.getServer().getMaxPlayers()));
				session.setPlayer(new Player(session, message.getUsername()));
			}

		} else {
			session.disconnect("Handshake already exchanged.");
		}
	}

}

