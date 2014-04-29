package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.PacketLoginRequest;
import net.rush.packets.packet.PacketLoginSuccess;

/**
 * A {@link PacketHandler} which performs the initial handshake with clients.
 */
public final class LoginRequestPacketHandler extends PacketHandler<PacketLoginRequest> {

	@Override
	public void handle(Session session, Player player, PacketLoginRequest message) {
		/*Session.State state = session.getState();

		ServerProperties prop = session.getServer().getProperties();
		session.send(new LoginPacket(0, prop.levelType, prop.gamemode, Dimension.NORMAL, prop.difficulty, prop.maxBuildHeight, prop.maxPlayers));
		session.setPlayer(new Player(session, message.name));*/
		
		session.send(new PacketLoginSuccess("0-0-0-0-0", message.name));
	}

}

