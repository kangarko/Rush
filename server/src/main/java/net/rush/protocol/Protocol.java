package net.rush.protocol;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

import net.rush.exceptions.PacketException;
import net.rush.netty.pipeline.PacketDecoder;
import net.rush.netty.pipeline.PacketEncoder;
import net.rush.protocol.packets.Handshake;
import net.rush.protocol.packets.KeepAlive;
import net.rush.protocol.packets.Kick;
import net.rush.protocol.packets.LoginStart;
import net.rush.protocol.packets.LoginSuccess;
import net.rush.protocol.packets.StatusPing;
import net.rush.protocol.packets.StatusRequest;
import net.rush.protocol.packets.StatusResponse;

public enum Protocol {

	HANDSHAKE {
		{
			TO_SERVER.registerPacket(0x00, Handshake.class);
		}
	},
	// 0
	GAME {
		{
			TO_CLIENT.registerPacket(0x00, KeepAlive.class);
			/*TO_CLIENT.registerPacket(0x01, Login.class);
			TO_CLIENT.registerPacket(0x02, Chat.class);
			TO_CLIENT.registerPacket(0x07, Respawn.class);
			TO_CLIENT.registerPacket(0x38, PlayerListItem.class);
			TO_CLIENT.registerPacket(0x3A, TabCompleteResponse.class);
			TO_CLIENT.registerPacket(0x3B, ScoreboardObjective.class);
			TO_CLIENT.registerPacket(0x3C, ScoreboardScore.class);
			TO_CLIENT.registerPacket(0x3D, ScoreboardDisplay.class);
			TO_CLIENT.registerPacket(0x3E, Team.class);
			TO_CLIENT.registerPacket(0x3F, PluginMessage.class);
			TO_CLIENT.registerPacket(0x40, Kick.class);
			TO_CLIENT.registerPacket(0x45, Title.class);
			TO_CLIENT.registerPacket(0x46, SetCompression.class);
			TO_CLIENT.registerPacket(0x47, PlayerListHeaderFooter.class);
			
			*/TO_SERVER.registerPacket(0x00, KeepAlive.class);
			/*TO_SERVER.registerPacket(0x01, Chat.class);
			TO_SERVER.registerPacket(0x14, TabCompleteRequest.class);
			TO_SERVER.registerPacket(0x15, ClientSettings.class);
			TO_SERVER.registerPacket(0x17, PluginMessage.class);*/
		}
	},
	// 1
	STATUS {
		{
			TO_CLIENT.registerPacket(0x00, StatusResponse.class);
			TO_CLIENT.registerPacket(0x01, StatusPing.class);

			TO_SERVER.registerPacket(0x00, StatusRequest.class);
			TO_SERVER.registerPacket(0x01, StatusPing.class);
		}
	},
	// 2
	LOGIN {
		{
			TO_CLIENT.registerPacket(0x00, Kick.class);
			//TO_CLIENT.registerPacket(0x01, EncryptionRequest.class);
			TO_CLIENT.registerPacket(0x02, LoginSuccess.class);
			//TO_CLIENT.registerPacket(0x03, SetCompression.class);

			TO_SERVER.registerPacket(0x00, LoginStart.class);
			//TO_SERVER.registerPacket(0x01, EncryptionResponse.class);
		}
	};
	
	private final int MAX_PACKET_ID = 0xFF;
	
	public final PacketDirection TO_SERVER = new PacketDirection();
	public final PacketDirection TO_CLIENT = new PacketDirection();

	@SuppressWarnings("unchecked")
	public class PacketDirection {

		private final HashMap<Class<? extends Packet>, Integer> packetIdMap = new HashMap<>(MAX_PACKET_ID);
		private final Class<? extends Packet>[] packetClasses = new Class[MAX_PACKET_ID];
		
		public boolean hasPacket(int id) {
			return id < MAX_PACKET_ID && packetClasses[id] != null;
		}

		public Packet newPacket(int id) {
			if (id > MAX_PACKET_ID)
				throw new PacketException("Packet id " + id + " outside of range");
			
			if (packetClasses[id] == null) 
				throw new PacketException("Not implemented packet id " + id);			

			try {
				return packetClasses[id].newInstance();
			} catch (ReflectiveOperationException ex) {
				throw new PacketException("Failed constructing packet id " + id, ex);
			}
		}

		protected void registerPacket(int id, Class<? extends Packet> packetClass) {
			try {
				packetClass.getDeclaredConstructor();
			} catch (NoSuchMethodException ex) {
				throw new PacketException("Error creating instance without constructors from " + packetClass);
			}
			packetClasses[id] = packetClass;
			packetIdMap.put(packetClass, id);
		}

		/*protected void unregisterPacket(int id) {
			packetIdMap.remove(packetClasses[id]);
			packetClasses[id] = null;
		}*/

		public final int getId(Class<? extends Packet> packetClass) {
			if (!packetIdMap.containsKey(packetClass))
				throw new PacketException("Cannot get ID for packet " + packetClass);
			
			return packetIdMap.get(packetClass);
		}
		
		public void setProtocol(ChannelHandlerContext channel, Protocol prot) {
			channel.pipeline().get(PacketDecoder.class).protocol = prot;
			channel.pipeline().get(PacketEncoder.class).protocol = prot;
		}
	}
}
