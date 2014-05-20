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
			session.setClientVersion(message.protocolVersion == 4 ? "1.7.2" : message.protocolVersion == 5 ? "1.7.6" : message.protocolVersion == 12 ? "1.8" : "unsure", message.protocolVersion);
			return;
		}
		
		if(message.getProtocolVer() < 78) {
			session.disconnect("Outdated client! (Connect with 1.6.4)");
			return;
		} else if (message.getProtocolVer() > 78) {
			session.disconnect("Outdated server!");
			return;
		}
		
		Session.State state = session.getState();
		
		if (state == Session.State.EXCHANGE_HANDSHAKE) {
			session.setState(State.EXCHANGE_IDENTIFICATION);

			if(session.getServer().getProperties().onlineMode) {
				new ThreadLoginVerifier(session, message).start();
			} else {
				ServerProperties prop = session.getServer().getProperties();
				session.send(new LoginPacket(0, prop.levelType, prop.gamemode, Dimension.NORMAL, prop.difficulty, prop.maxBuildHeight, prop.maxPlayers, prop.hardcore));
				session.setPlayer(new Player(session, message.getUsername()));
			}

		} else {
			session.disconnect("Handshake already exchanged.");
		}
	}

}

