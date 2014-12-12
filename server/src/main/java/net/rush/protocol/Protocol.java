package net.rush.protocol;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

import net.rush.api.exceptions.PacketException;
import net.rush.netty.pipeline.PacketDecoder;
import net.rush.netty.pipeline.PacketEncoder;
import net.rush.protocol.packets.Animation;
import net.rush.protocol.packets.ChangeGameState;
import net.rush.protocol.packets.ChatMessage;
import net.rush.protocol.packets.ChunkBulk;
import net.rush.protocol.packets.ClientSettings;
import net.rush.protocol.packets.ClientStatus;
import net.rush.protocol.packets.DestroyEntity;
import net.rush.protocol.packets.EntityExists;
import net.rush.protocol.packets.EntityHeadLook;
import net.rush.protocol.packets.EntityLook;
import net.rush.protocol.packets.EntityLookRelMove;
import net.rush.protocol.packets.EntityMetadata;
import net.rush.protocol.packets.EntityRelMove;
import net.rush.protocol.packets.EntityTeleport;
import net.rush.protocol.packets.Handshake;
import net.rush.protocol.packets.JoinGame;
import net.rush.protocol.packets.KeepAlive;
import net.rush.protocol.packets.Kick;
import net.rush.protocol.packets.LoginStart;
import net.rush.protocol.packets.LoginSuccess;
import net.rush.protocol.packets.PlayerListItem;
import net.rush.protocol.packets.PlayerLook;
import net.rush.protocol.packets.PlayerLookAndPosition;
import net.rush.protocol.packets.PlayerOnGround;
import net.rush.protocol.packets.PlayerPosition;
import net.rush.protocol.packets.PluginMessage;
import net.rush.protocol.packets.SpawnPlayer;
import net.rush.protocol.packets.SpawnPosition;
import net.rush.protocol.packets.StatusPing;
import net.rush.protocol.packets.StatusRequest;
import net.rush.protocol.packets.StatusResponse;

public enum Protocol {

	// Undef
	HANDSHAKE {
		{
			TO_SERVER.registerPacket(0x00, Handshake.class);
		}
	},
	// 0
	GAME {
		{
			TO_CLIENT.registerPacket(0x00, KeepAlive.class);
			TO_CLIENT.registerPacket(0x01, JoinGame.class);
			TO_CLIENT.registerPacket(0x02, ChatMessage.class);
			/*TO_CLIENT.registerPacket(0x03, PacketTimeUpdate.class);
			TO_CLIENT.registerPacket(0x04, PacketEntityEquipment.class);
			*/TO_CLIENT.registerPacket(0x05, SpawnPosition.class);
			/*TO_CLIENT.registerPacket(0x06, PacketUpdateHealth.class);
			TO_CLIENT.registerPacket(0x07, PacketRespawn.class);
			*/TO_CLIENT.registerPacket(0x08, PlayerLookAndPosition.class);
			/*TO_CLIENT.registerPacket(0x09, PacketHeldItemChange.class);
			TO_CLIENT.registerPacket(0x0A, PacketUseBed.class); //Packet17EntityLocationAction*/
			TO_CLIENT.registerPacket(0x0B, Animation.class);
			TO_CLIENT.registerPacket(0x0C, SpawnPlayer.class);
			/*TO_CLIENT.registerPacket(0x0D, PacketItemCollect.class);
			TO_CLIENT.registerPacket(0x0E, PacketSpawnObject.class);
			TO_CLIENT.registerPacket(0x0F, PacketSpawnMob.class);
			TO_CLIENT.registerPacket(0x10, PacketSpawnPainting.class);
			TO_CLIENT.registerPacket(0x11, PacketSpawnExpOrb.class);
			TO_CLIENT.registerPacket(0x12, PacketEntityVelocity.class);
			*/TO_CLIENT.registerPacket(0x13, DestroyEntity.class);
			TO_CLIENT.registerPacket(0x14, EntityExists.class);
			TO_CLIENT.registerPacket(0x15, EntityRelMove.class);
			TO_CLIENT.registerPacket(0x16, EntityLook.class);			
			TO_CLIENT.registerPacket(0x17, EntityLookRelMove.class);			
			TO_CLIENT.registerPacket(0x18, EntityTeleport.class);			
			TO_CLIENT.registerPacket(0x19, EntityHeadLook.class);	
			/*TO_CLIENT.registerPacket(0x1A, PacketEntityStatus.class); // inherit from entityexist
			TO_CLIENT.registerPacket(0x1B, PacketAttachEntity.class); // inherit from entityexist
			*/TO_CLIENT.registerPacket(0x1C, EntityMetadata.class);
			/*TO_CLIENT.registerPacket(0x1D, PacketEntityEffect.class);
			TO_CLIENT.registerPacket(0x1E, PacketRemoveEntityEffect.class);
			TO_CLIENT.registerPacket(0x1F, PacketSetExperience.class);
			TO_CLIENT.registerPacket(0x20, Packet44UpdateAttributes.class);
			*/TO_CLIENT.registerPacket(0x21, ChunkBulk.class);
			/*TO_CLIENT.registerPacket(0x22, PacketMultiBlockChange.class);
			TO_CLIENT.registerPacket(0x23, PacketBlockChange.class);
			TO_CLIENT.registerPacket(0x24, PacketBlockAction.class);
			TO_CLIENT.registerPacket(0x25, PacketBlockBreakAnim.class);
			TO_CLIENT.registerPacket(0x26, PacketChunkBulk.class);
			TO_CLIENT.registerPacket(0x27, PacketExplosion.class);
			TO_CLIENT.registerPacket(0x28, PacketSoundOrParticleEffect.class);
			TO_CLIENT.registerPacket(0x29, PacketNamedSoundEffect.class);
			TO_CLIENT.registerPacket(0x2A, Packet63WorldParticles.class);
			*/TO_CLIENT.registerPacket(0x2B, ChangeGameState.class);
			/*TO_CLIENT.registerPacket(0x2C, PacketThunderbolt.class);
			TO_CLIENT.registerPacket(0x2D, PacketOpenWindow.class);
			TO_CLIENT.registerPacket(0x2E, PacketCloseWindow.class);
			TO_CLIENT.registerPacket(0x2F, PacketSetSlot.class);
			TO_CLIENT.registerPacket(0x30, PacketSetWindowItems.class);
			TO_CLIENT.registerPacket(0x31, PacketUpdateWindowProperty.class);
			TO_CLIENT.registerPacket(0x32, PacketConfirmTransaction.class);
			TO_CLIENT.registerPacket(0x33, PacketUpdateSign.class);
			TO_CLIENT.registerPacket(0x34, PacketMap.class);
			TO_CLIENT.registerPacket(0x35, PacketUpdateTileEntity.class);
			TO_CLIENT.registerPacket(0x36, Packet133OpenTileEntity.class);
			TO_CLIENT.registerPacket(0x37, Packet200Statistic.class);
			*/TO_CLIENT.registerPacket(0x38, PlayerListItem.class);
			/*TO_CLIENT.registerPacket(0x39, PacketPlayerAbilities.class);
			TO_CLIENT.registerPacket(0x3A, PacketTabComplete.class);
			TO_CLIENT.registerPacket(0x3B, Packet206SetScoreboardObjective.class);
			TO_CLIENT.registerPacket(0x3C, Packet207SetScoreboardScore.class);
			TO_CLIENT.registerPacket(0x3D, Packet208SetScoreboardDisplayObjective.class);
			TO_CLIENT.registerPacket(0x3E, Packet209SetScoreboardTeam.class);*/
			TO_CLIENT.registerPacket(0x3F, PluginMessage.class);
			TO_CLIENT.registerPacket(0x40, Kick.class);

			TO_SERVER.registerPacket(0x00, KeepAlive.class);
			TO_SERVER.registerPacket(0x01, ChatMessage.class);
			/*TO_SERVER.registerPacket(0x02, PacketUseEntity.class);
			*/TO_SERVER.registerPacket(0x03, PlayerOnGround.class);
			TO_SERVER.registerPacket(0x04, PlayerPosition.class);
			TO_SERVER.registerPacket(0x05, PlayerLook.class);
			TO_SERVER.registerPacket(0x06, PlayerLookAndPosition.class);
			/*TO_SERVER.registerPacket(0x07, PacketDigging.class);
			TO_SERVER.registerPacket(0x08, PacketBlockPlacement.class);
			TO_SERVER.registerPacket(0x09, PacketHeldItemChange.class);
			*/TO_SERVER.registerPacket(0x0A, Animation.class);
			/*TO_SERVER.registerPacket(0x0B, PacketEntityAction.class);
			TO_SERVER.registerPacket(0x0C, PacketSteerVehicle.class);
			TO_SERVER.registerPacket(0x0D, PacketCloseWindow.class);
			TO_SERVER.registerPacket(0x0E, PacketClickWindow.class);
			TO_SERVER.registerPacket(0x0F, PacketConfirmTransaction.class);
			TO_SERVER.registerPacket(0x10, PacketCreativeInventoryAction.class);
			TO_SERVER.registerPacket(0x11, PacketEnchantItem.class);
			TO_SERVER.registerPacket(0x12, PacketUpdateSign.class);
			TO_SERVER.registerPacket(0x13, PacketPlayerAbilities.class);
			TO_SERVER.registerPacket(0x14, PacketTabComplete.class);
			*/TO_SERVER.registerPacket(0x15, ClientSettings.class);
			TO_SERVER.registerPacket(0x16, ClientStatus.class);
			TO_SERVER.registerPacket(0x17, PluginMessage.class);
			// 1.8
			//TO_CLIENT.registerPacket(0x45, Packet18Title.class);
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
	//2
	LOGIN {
		{
			TO_CLIENT.registerPacket(0x00, Kick.class);
			//TO_CLIENT.registerPacket(0x01, PacketEncryptionRequest.class);
			TO_CLIENT.registerPacket(0x02, LoginSuccess.class);

			TO_SERVER.registerPacket(0x00, LoginStart.class);
			/*TO_SERVER.registerPacket(0x01, PacketEncryptionResponse.class);
			// 1.8
			TO_CLIENT.registerPacket(0x3, LoginCompression.class);*/
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
				throw new PacketException("Unknown packet id " + id);			

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
				throw new PacketException("Protocol " + Protocol.this + " doesn't contains packet " + packetClass.getSimpleName());

			return packetIdMap.get(packetClass);
		}

		public void setProtocol(ChannelHandlerContext channel, Protocol prot) {
			channel.pipeline().get(PacketDecoder.class).protocol = prot;
			channel.pipeline().get(PacketEncoder.class).protocol = prot;
		}
	}
}
