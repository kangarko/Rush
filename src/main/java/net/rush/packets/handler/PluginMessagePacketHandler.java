package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.PluginMessagePacket;

/**
 * A {@link PacketHandler} which disconnects clients when they send a
 * {@link KickMessage} to the server.

 */
public final class PluginMessagePacketHandler extends PacketHandler<PluginMessagePacket> {

	@Override
	public void handle(Session session, Player player, PluginMessagePacket message) {
		System.out.println("pluginMessage channel: " + message.getChannel());
		/*if(message.getChannel().equals("MC|PingHost")) {
			session.send(new KickPacketImpl("[1.6.4] Rush server" + "\u00A7" + session.getServer().getWorld().getRushPlayers().size() + "\u00A7" + "20"));
		}*/
	}

}

