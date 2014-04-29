package net.rush.packets.handler;

import net.rush.ServerProperties;
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
		// 1.7 clients are not logging in that way
		if (!session.isCompat()) {
			session.setClientVersion(message.protocolVersion == 4 ? "1.7.2" : message.protocolVersion == 5 ? "1.7.6" : "unsure", message.protocolVersion);
			return;
		}
		
		Session.State state = session.getState();
		
		if (state == Session.State.EXCHANGE_HANDSHAKE) {
			session.setState(State.EXCHANGE_IDENTIFICATION);

			if(session.getServer().getProperties().onlineMode) {
				new ThreadLoginVerifier(session, message).start();
			} else {
				ServerProperties prop = session.getServer().getProperties();
				session.send(new LoginPacket(0, prop.levelType, prop.gamemode, Dimension.NORMAL, prop.difficulty, prop.maxBuildHeight, prop.maxPlayers));
				session.setPlayer(new Player(session, message.getUsername()));
			}

		} else {
			session.disconnect("Handshake already exchanged.");
		}
	}

}

