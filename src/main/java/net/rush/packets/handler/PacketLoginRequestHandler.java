package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.PacketLoginRequest;

/**
 * A {@link PacketHandler} which performs the initial handshake with clients.
 */
public final class PacketLoginRequestHandler extends PacketHandler<PacketLoginRequest> {

	@Override
	public void handle(Session session, Player player, PacketLoginRequest message) {
		session.disconnect("logging' not yet!");
		
		//Session.State state = session.getState();

		//session.send(new PacketLoginSuccess("0-0-0-0-0", message.name));
		
		//ServerProperties prop = session.getServer().getProperties();
		//session.send(new LoginPacket(0, prop.levelType, prop.gamemode, Dimension.NORMAL, prop.difficulty, prop.maxBuildHeight, prop.maxPlayers));
		//session.setPlayer(new Player(session, message.name));
	}

}

