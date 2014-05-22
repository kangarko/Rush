package net.rush.packets.handler;

import java.util.HashMap;
import java.util.Map;

import net.rush.packets.Packet;
import net.rush.packets.packet.AnimationPacket;
import net.rush.packets.packet.ChatPacket;
import net.rush.packets.packet.ClickWindowPacket;
import net.rush.packets.packet.CreativeInventoryActionPacket;
import net.rush.packets.packet.EntityActionPacket;
import net.rush.packets.packet.HandshakePacket;
import net.rush.packets.packet.HeldItemChangePacket;
import net.rush.packets.packet.KeepAlivePacket;
import net.rush.packets.packet.KickPacket;
import net.rush.packets.packet.PacketLoginRequest;
import net.rush.packets.packet.PacketPingTime;
import net.rush.packets.packet.PacketStatusRequest;
import net.rush.packets.packet.PlayerBlockPlacementPacket;
import net.rush.packets.packet.PlayerDiggingPacket;
import net.rush.packets.packet.PlayerLookPacket;
import net.rush.packets.packet.PlayerOnGroundPacket;
import net.rush.packets.packet.PlayerPositionAndLookPacket;
import net.rush.packets.packet.PlayerPositionPacket;
import net.rush.packets.packet.PluginMessagePacket;
import net.rush.packets.packet.ServerListPingPacket;
import net.rush.packets.packet.UseEntityPacket;

/**
 * A class used to look up message handlers.

 */
public final class HandlerLookupService {

	/**
	 * A table which maps messages to their handles.
	 */
	private static final Map<Class<? extends Packet>, PacketHandler<?>> handlers = new HashMap<Class<? extends Packet>, PacketHandler<?>>();

	/**
	 * Populates the table with message handlers.
	 */
	static {
		try {
			bind(HandshakePacket.class, HandshakePacketHandler.class);
			bind(ChatPacket.class, ChatPacketHandler.class);
			bind(PlayerPositionPacket.class, PositionPacketHandler.class);
			bind(PlayerLookPacket.class, LookPacketHandler.class);
			bind(PlayerPositionAndLookPacket.class, PositionAndLookPacketHandler.class);
			bind(KickPacket.class, KickPacketHandler.class);
			bind(PlayerDiggingPacket.class, DiggingPacketHandler.class);
			bind(AnimationPacket.class, AnimationPacketHandler.class);
			bind(EntityActionPacket.class, EntityActionPacketHandler.class);
			bind(PlayerBlockPlacementPacket.class, BlockPlacementPacketHandler.class);
			bind(KeepAlivePacket.class, KeepAlivePacketHandler.class);
			bind(PlayerOnGroundPacket.class, PlayerOnGroundPacketHandler.class);
			bind(PluginMessagePacket.class, PluginMessagePacketHandler.class);
			bind(HeldItemChangePacket.class, HeldItemChangePacketHandler.class);
			bind(ServerListPingPacket.class, ServerListPingPacketHandler.class);
			bind(CreativeInventoryActionPacket.class, CreativeInventoryActionPacketHandler.class);
			bind(ClickWindowPacket.class, ClickWindowPacketHandler.class);
			bind(UseEntityPacket.class, UseEntityPacketHandler.class);
			// 1.7
			bind(PacketStatusRequest.class, PacketStatusRequestHandler.class);
			bind(PacketLoginRequest.class, PacketLoginRequestHandler.class);
			bind(PacketPingTime.class, PacketPingTimeHandler.class);
		} catch (Exception ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Binds a handler by adding entries for it in the table.
	 * @param <T> The type of message.
	 * @param clazz The message's class.
	 * @param handlerClass The handler's class.
	 * @throws InstantiationException if the handler could not be instantiated.
	 * @throws IllegalAccessException if the handler could not be instantiated
	 * due to an access violation.
	 */
	private static <T extends Packet> void bind(Class<T> clazz, Class<? extends PacketHandler<T>> handlerClass) throws InstantiationException, IllegalAccessException {
		PacketHandler<T> handler = handlerClass.newInstance();
		handlers.put(clazz, handler);
	}

	/**
	 * Finds a handler by message class.
	 * @param <T> The type of message.
	 * @param clazz The message's class.
	 * @return The message's handler, or {@code null} if no handler exists.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Packet> PacketHandler<T> find(Class<T> clazz) {
		return (PacketHandler<T>) handlers.get(clazz);
	}

	/**
	 * Default private constructor to prevent instantiation.
	 */
	private HandlerLookupService() {}

}

