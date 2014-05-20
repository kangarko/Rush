package net.rush.packets.handler;

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

		String text = message.getMessage();
		
		if(text == null || "".equals(text))
			session.disconnect("Cannot send an empty message");
		
		if (text.length() > 110) {
			session.disconnect("Chat message too long");
		} else {
			
			if(text.matches("(&([a-f0-9k-or]))"))
				return;
			
			text = text.replaceAll("\\s+", " ").trim();
			
			if (text.startsWith("/")) {
				session.getServer().getCommandManager().execute(player, text);
				logger.info(player.getName() + " issued server command: " + text);
			} else {
				player.getServer().broadcastMessage("<" + player.getName() + "> " + text);
				logger.info(player.getName() + ": " + text);
			}
		}
	}	
}

