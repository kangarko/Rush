package net.rush.packets.handler;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.PluginMessagePacket;
import net.rush.packets.packet.impl.KickPacketImpl;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

/**
 * A {@link PacketHandler} which disconnects clients when they send a
 * {@link KickMessage} to the server.

 */
public final class PluginMessagePacketHandler extends PacketHandler<PluginMessagePacket> {

	@Override
	public void handle(Session session, Player player, PluginMessagePacket message) {
		System.out.println("pluginMessage channel: " + message.getChannel());
		if(message.getChannel().equals("MC|PingHost")) {
			// pre 1.6 -> session.send(new KickPacketImpl("[1.6.4] Rush server" + "\u00A7" + session.getServer().getWorld().getPlayers().size() + "\u00A7" + "20"));
			
			// hard copy from notchian server start
			Object[] infos = new Object[] { 1, 78, "1.6.4", ChatColor.GREEN + "Rush server "  + ChatColor.YELLOW + "(Working MOTD!)", session.getServer().getWorld().getPlayers().size(), 20 };
			StringBuilder builder = new StringBuilder();
			
			for (Object info : infos) {
				if (builder.length() == 0)
					builder.append('\u00A7');
				else
					builder.append('\0');

				builder.append(StringUtils.replace(info.toString(), "\0", ""));
			}
			// hard copy from notchian server stop
			
			session.send(new KickPacketImpl(builder.toString()));
		}
	}

}

