package net.rush.packets.handler;

import net.rush.cmd.CommandManager;
import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.packet.ChatPacket;

/**
 * A {@link PacketHandler} which handles {@link ChatMessage}s by processing
 * commands or broadcasting messages to every player in the server.

 */
public final class ChatPacketHandler extends PacketHandler<ChatPacket> {

	@Override
	public void handle(Session session, Player player, ChatPacket message) {
		if (player == null)
			return;

		String text = message.getPlainMessage();
		if (text.length() > 110) {
			session.disconnect("Chat message too long.");
		} else if (text.startsWith("/")) {
			CommandManager manager = session.getServer().getCommandManager();
			manager.execute(player, text);
		} else {
			player.getWorld().broadcastMessage("<" + player.getName() + "> " + text);
		}
	}

}

