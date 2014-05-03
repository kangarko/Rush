package net.rush.packets.handler;

import java.util.logging.Logger;

import net.rush.model.Player;
import net.rush.net.Session;
import net.rush.packets.Packet;

/**
 * A {@link PacketHandler} performs some actions upon the arrival of an
 * incoming {@link Packet}.
 * @param <T> The type of {@link Packet}.
 */
public abstract class PacketHandler<T extends Packet> {

	protected Logger logger = Logger.getLogger("Minecraft");
	
	/**
	 * Handles an incoming message.
	 * @param session The session that sent the message.
	 * @param player The player that sent the message, or {@code null} if the
	 * session currently has no player.
	 * @param packet The message.
	 */
	public abstract void handle(Session session, Player player, T packet);

}

