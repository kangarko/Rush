package net.rush.packets.misc;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.rush.packets.Packet;
import net.rush.packets.packet.HandshakePacket;
import net.rush.packets.packet.KeepAlivePacket;
import net.rush.packets.packet.KickPacket;
import net.rush.packets.packet.LoginPacket;
import net.rush.packets.packet.PacketLoginRequest;
import net.rush.packets.packet.PacketLoginSuccess;
import net.rush.packets.packet.PacketPingTime;
import net.rush.packets.packet.PacketStatusRequest;

import com.google.common.base.Preconditions;

public enum Protocol {

	// Undef
	HANDSHAKE {

		{
			TO_SERVER.registerPacket(0x00, HandshakePacket.class);
		}
	},
	// 0
	GAME {

		{
			TO_CLIENT.registerPacket(0x00, KeepAlivePacket.class);
			TO_CLIENT.registerPacket(0x01, LoginPacket.class);
			/*TO_CLIENT.registerPacket(0x02, ChatPacket.class);
			TO_CLIENT.registerPacket(0x03, TimeUpdatePacket.class);
			TO_CLIENT.registerPacket(0x04, EntityEquipmentPacket.class);
			TO_CLIENT.registerPacket(0x05, SpawnPositionPacket.class);
			TO_CLIENT.registerPacket(0x06, UpdateHealthPacket.class);
			TO_CLIENT.registerPacket(0x07, PlayerRespawnPacket.class);
			TO_CLIENT.registerPacket(0x08, PlayerPositionAndLookPacket.class);
			TO_CLIENT.registerPacket(0x09, HeldItemChangePacket.class);
			TO_CLIENT.registerPacket(0x0A, UseBedPacket.class); //Packet17EntityLocationAction
			TO_CLIENT.registerPacket(0x0B, AnimationPacket.class);
			TO_CLIENT.registerPacket(0x0C, NamedEntitySpawnPacket.class);
			TO_CLIENT.registerPacket(0x0D, ItemCollectPacket.class);
			TO_CLIENT.registerPacket(0x0E, SpawnObjectPacket.class);
			TO_CLIENT.registerPacket(0x0F, SpawnMobPacket.class);
			TO_CLIENT.registerPacket(0x10, SpawnPaintingPacket.class);
			TO_CLIENT.registerPacket(0x11, SpawnExperienceOrbPacket.class);
			TO_CLIENT.registerPacket(0x12, EntityVelocityPacket.class);
			TO_CLIENT.registerPacket(0x13, DestroyEntityPacket.class);
			TO_CLIENT.registerPacket(0x14, EntityExistsPacket.class);
			TO_CLIENT.registerPacket(0x15, EntityRelMovePacket.class);
			TO_CLIENT.registerPacket(0x16, EntityLookPacket.class);
			TO_CLIENT.registerPacket(0x17, EntityLookAndRelMovePacket.class);
			TO_CLIENT.registerPacket(0x18, EntityTeleportPacket.class);
			TO_CLIENT.registerPacket(0x19, EntityHeadLookPacket.class);
			TO_CLIENT.registerPacket(0x1A, EntityStatusPacket.class);
			TO_CLIENT.registerPacket(0x1B, AttachEntityPacket.class);
			TO_CLIENT.registerPacket(0x1C, EntityMetadataPacket.class);
			TO_CLIENT.registerPacket(0x1D, EntityEffectPacket.class);
			TO_CLIENT.registerPacket(0x1E, RemoveEntityEffectPacket.class);
			TO_CLIENT.registerPacket(0x1F, SetExperiencePacket.class);
			//TO_CLIENT.registerPacket(0x20, Packet44UpdateAttributes.class);
			TO_CLIENT.registerPacket(0x21, MapChunkPacket.class);
			TO_CLIENT.registerPacket(0x22, MultiBlockChangePacket.class);
			TO_CLIENT.registerPacket(0x23, BlockChangePacket.class);
			TO_CLIENT.registerPacket(0x24, BlockActionPacket.class);
			TO_CLIENT.registerPacket(0x25, BlockBreakAnimationPacket.class);
			TO_CLIENT.registerPacket(0x26, PreChunkPacket.class);
			TO_CLIENT.registerPacket(0x27, ExplosionPacket.class);
			TO_CLIENT.registerPacket(0x28, SoundOrParticleEffectPacket.class);
			TO_CLIENT.registerPacket(0x29, NamedSoundEffectPacket.class);
			//TO_CLIENT.registerPacket(0x2A, Packet63WorldParticles.class);
			TO_CLIENT.registerPacket(0x2B, ChangeGameStatePacket.class);
			TO_CLIENT.registerPacket(0x2C, ThunderboltPacket.class);
			TO_CLIENT.registerPacket(0x2D, OpenWindowPacket.class);
			TO_CLIENT.registerPacket(0x2E, CloseWindowPacket.class);
			TO_CLIENT.registerPacket(0x2F, SetSlotPacket.class);
			TO_CLIENT.registerPacket(0x30, SetWindowItemsPacket.class);
			TO_CLIENT.registerPacket(0x31, UpdateWindowPropertyPacket.class);
			TO_CLIENT.registerPacket(0x32, ConfirmTransactionPacket.class);
			TO_CLIENT.registerPacket(0x33, UpdateSignPacket.class);
			TO_CLIENT.registerPacket(0x34, ItemDataPacket.class);
			TO_CLIENT.registerPacket(0x35, UpdateTileEntityPacket.class);
			//TO_CLIENT.registerPacket(0x36, Packet133OpenTileEntity.class);
			//TO_CLIENT.registerPacket(0x37, Packet200Statistic.class);
			TO_CLIENT.registerPacket(0x38, PlayerListItemPacket.class);
			TO_CLIENT.registerPacket(0x39, PlayerAbilitiesPacket.class);
			TO_CLIENT.registerPacket(0x3A, TabCompletePacket.class);
			//TO_CLIENT.registerPacket(0x3B, Packet206SetScoreboardObjective.class);
			//TO_CLIENT.registerPacket(0x3C, Packet207SetScoreboardScore.class);
			//TO_CLIENT.registerPacket(0x3D, Packet208SetScoreboardDisplayObjective.class);
			//TO_CLIENT.registerPacket(0x3E, Packet209SetScoreboardTeam.class);
			TO_CLIENT.registerPacket(0x3F, PluginMessagePacket.class);*/
			TO_CLIENT.registerPacket(0x40, KickPacket.class);

			TO_SERVER.registerPacket(0x00, KeepAlivePacket.class);
			/*TO_SERVER.registerPacket(0x01, ChatPacket.class);
			TO_SERVER.registerPacket(0x02, UseEntityPacket.class);
			TO_SERVER.registerPacket(0x03, PlayerOnGroundPacket.class);
			TO_SERVER.registerPacket(0x04, PlayerPositionPacket.class);
			TO_SERVER.registerPacket(0x05, PlayerLookPacket.class);
			TO_SERVER.registerPacket(0x06, PlayerPositionAndLookPacket.class);
			TO_SERVER.registerPacket(0x07, PlayerDiggingPacket.class);
			TO_SERVER.registerPacket(0x08, PlayerBlockPlacementPacket.class);
			TO_SERVER.registerPacket(0x09, HeldItemChangePacket.class);
			TO_SERVER.registerPacket(0x0A, AnimationPacket.class);
			TO_SERVER.registerPacket(0x0B, EntityActionPacket.class);
			TO_SERVER.registerPacket(0x0C, SteerVehiclePacket.class);
			TO_SERVER.registerPacket(0x0D, CloseWindowPacket.class);
			TO_SERVER.registerPacket(0x0E, ClickWindowPacket.class);
			TO_SERVER.registerPacket(0x0F, ConfirmTransactionPacket.class);
			TO_SERVER.registerPacket(0x10, CreativeInventoryActionPacket.class);
			TO_SERVER.registerPacket(0x11, EnchantItemPacket.class);
			TO_SERVER.registerPacket(0x12, UpdateSignPacket.class);
			TO_SERVER.registerPacket(0x13, PlayerAbilitiesPacket.class);
			TO_SERVER.registerPacket(0x14, TabCompletePacket.class);
			TO_SERVER.registerPacket(0x15, ClientSettingsPacket.class);
			TO_SERVER.registerPacket(0x16, ClientStatusPacket.class);
			TO_SERVER.registerPacket(0x17, PluginMessagePacket.class)*/;

		}
	},
	// 1
	STATUS {

		{
			TO_CLIENT.registerPacket(0x00, KickPacket.class);
			TO_CLIENT.registerPacket(0x01, PacketPingTime.class);

			TO_SERVER.registerPacket(0x00, PacketStatusRequest.class);
			TO_SERVER.registerPacket(0x01, PacketPingTime.class);
		}
	},
	//2
	LOGIN {

		{
			TO_CLIENT.registerPacket(0x00, KickPacket.class);
			//TO_CLIENT.registerPacket(0x01, EncryptionKeyRequestPacket.class);
			TO_CLIENT.registerPacket(0x02, PacketLoginSuccess.class);

			TO_SERVER.registerPacket(0x00, PacketLoginRequest.class);
			//TO_SERVER.registerPacket(0x01, EncryptionKeyResponsePacket.class);
		}
	};
	/*========================================================================*/
	public static final int MAX_PACKET_ID = 0xFF;
	public static final int PROTOCOL_VERSION = 0x04;
	public static final String MINECRAFT_VERSION = "1.7.5";
	/*========================================================================*/
	public final ProtocolDirection TO_SERVER = new ProtocolDirection("TO_SERVER");
	public final ProtocolDirection TO_CLIENT = new ProtocolDirection("TO_CLIENT");

	@SuppressWarnings("unchecked")
	public class ProtocolDirection {

		public ProtocolDirection(String name) {
			this.name = name;
		}

		private final String name;
		private final TObjectIntMap<Class<? extends Packet>> packetMap = new TObjectIntHashMap<Class<? extends Packet>>(MAX_PACKET_ID);
		private final Class<? extends Packet>[] packetClasses = new Class[MAX_PACKET_ID];
		//private final Constructor<? extends Packet>[] packetConstructors = new Constructor[MAX_PACKET_ID];

		public boolean hasPacket(int id) {
			return id < MAX_PACKET_ID && /*packetConstructors*/packetClasses[id] != null;
		}

		@Override
		public String toString() {
			return name;
		}

		public final Class<? extends Packet> createPacket(int id) {
			if (id > MAX_PACKET_ID) 
				throw new RuntimeException("Packet with id " + id + " outside of range ");
			
			/*if (packetConstructors[id] == null) 
				throw new RuntimeException("No packet with id " + id);*/		

			try {
				return packetClasses[id];
			} catch (Exception ex) {
				throw new RuntimeException("Could not construct packet with id " + id, ex);
			}
		}

		protected final void registerPacket(int id, Class<? extends Packet> packetClass) {
			/*try {
				packetConstructors[id] = packetClass.getDeclaredConstructor();
			} catch (NoSuchMethodException ex) {
				throw new RuntimeException("No NoArgsConstructor for packet class " + packetClass);
			}*/
			packetClasses[id] = packetClass;
			packetMap.put(packetClass, id);
		}

		protected final void unregisterPacket(int id) {
			packetMap.remove(packetClasses[id]);
			packetClasses[id] = null;
			//packetConstructors[id] = null;
		}

		public final int getId(Class<? extends Packet> packet) {
			Preconditions.checkArgument(packetMap.containsKey(packet), "Cannot get ID for packet " + packet);

			return packetMap.get(packet);
		}
	}
}
