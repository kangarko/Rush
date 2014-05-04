package net.rush.packets;

import java.util.HashMap;
import java.util.Map;

import net.rush.packets.packet.AnimationPacket;
import net.rush.packets.packet.AttachEntityPacket;
import net.rush.packets.packet.BlockActionPacket;
import net.rush.packets.packet.BlockBreakAnimationPacket;
import net.rush.packets.packet.BlockChangePacket;
import net.rush.packets.packet.ChangeGameStatePacket;
import net.rush.packets.packet.ChatPacket;
import net.rush.packets.packet.ClickWindowPacket;
import net.rush.packets.packet.ClientSettingsPacket;
import net.rush.packets.packet.ClientStatusPacket;
import net.rush.packets.packet.CloseWindowPacket;
import net.rush.packets.packet.ConfirmTransactionPacket;
import net.rush.packets.packet.CreativeInventoryActionPacket;
import net.rush.packets.packet.DestroyEntityPacket;
import net.rush.packets.packet.EnchantItemPacket;
import net.rush.packets.packet.EncryptionKeyRequestPacket;
import net.rush.packets.packet.EncryptionKeyResponsePacket;
import net.rush.packets.packet.EntityActionPacket;
import net.rush.packets.packet.EntityEffectPacket;
import net.rush.packets.packet.EntityEquipmentPacket;
import net.rush.packets.packet.EntityExistsPacket;
import net.rush.packets.packet.EntityHeadLookPacket;
import net.rush.packets.packet.EntityLookAndRelMovePacket;
import net.rush.packets.packet.EntityLookPacket;
import net.rush.packets.packet.EntityMetadataPacket;
import net.rush.packets.packet.EntityRelMovePacket;
import net.rush.packets.packet.EntityStatusPacket;
import net.rush.packets.packet.EntityTeleportPacket;
import net.rush.packets.packet.EntityVelocityPacket;
import net.rush.packets.packet.ExplosionPacket;
import net.rush.packets.packet.HandshakePacket;
import net.rush.packets.packet.HeldItemChangePacket;
import net.rush.packets.packet.IncrementStatisticPacket;
import net.rush.packets.packet.ItemCollectPacket;
import net.rush.packets.packet.MapDataPacket;
import net.rush.packets.packet.KeepAlivePacket;
import net.rush.packets.packet.KickPacket;
import net.rush.packets.packet.LoginPacket;
import net.rush.packets.packet.MapChunkPacket;
import net.rush.packets.packet.MultiBlockChangePacket;
import net.rush.packets.packet.NamedEntitySpawnPacket;
import net.rush.packets.packet.NamedSoundEffectPacket;
import net.rush.packets.packet.OpenWindowPacket;
import net.rush.packets.packet.PlayerAbilitiesPacket;
import net.rush.packets.packet.PlayerBlockPlacementPacket;
import net.rush.packets.packet.PlayerDiggingPacket;
import net.rush.packets.packet.PlayerListItemPacket;
import net.rush.packets.packet.PlayerLookPacket;
import net.rush.packets.packet.PlayerOnGroundPacket;
import net.rush.packets.packet.PlayerPositionAndLookPacket;
import net.rush.packets.packet.PlayerPositionPacket;
import net.rush.packets.packet.PlayerRespawnPacket;
import net.rush.packets.packet.PluginMessagePacket;
import net.rush.packets.packet.PreChunkPacket;
import net.rush.packets.packet.RemoveEntityEffectPacket;
import net.rush.packets.packet.ServerListPingPacket;
import net.rush.packets.packet.SetExperiencePacket;
import net.rush.packets.packet.SetSlotPacket;
import net.rush.packets.packet.SetWindowItemsPacket;
import net.rush.packets.packet.SoundOrParticleEffectPacket;
import net.rush.packets.packet.SpawnExperienceOrbPacket;
import net.rush.packets.packet.SpawnMobPacket;
import net.rush.packets.packet.SpawnObjectPacket;
import net.rush.packets.packet.SpawnPaintingPacket;
import net.rush.packets.packet.SpawnPositionPacket;
import net.rush.packets.packet.SteerVehiclePacket;
import net.rush.packets.packet.TabCompletePacket;
import net.rush.packets.packet.ThunderboltPacket;
import net.rush.packets.packet.TimeUpdatePacket;
import net.rush.packets.packet.UpdateHealthPacket;
import net.rush.packets.packet.UpdateSignPacket;
import net.rush.packets.packet.UpdateTileEntityPacket;
import net.rush.packets.packet.UpdateWindowPropertyPacket;
import net.rush.packets.packet.UseBedPacket;
import net.rush.packets.packet.UseEntityPacket;

public final class Packets {

    private static final Map<Integer, Class<? extends Packet>> classMappings = new HashMap<Integer, Class<? extends Packet>>();
    
    /* TODO Explosion packet
    	IncrementStatisticPacket
    	MapDataPacket - check 1.7
    	SpawnExperienceOrbPacket - check fixed-point number (*32)
    	ThunderBoltPacket - check fixed-point number
    */
    static {
        registerPacket(0x00, KeepAlivePacket.class);
        registerPacket(0x01, LoginPacket.class);
        registerPacket(0x02, HandshakePacket.class);
        registerPacket(0x03, ChatPacket.class);
        registerPacket(0x04, TimeUpdatePacket.class);
        registerPacket(0x05, EntityEquipmentPacket.class);
        registerPacket(0x06, SpawnPositionPacket.class);
        registerPacket(0x07, UseEntityPacket.class);
        registerPacket(0x08, UpdateHealthPacket.class);
        registerPacket(0x09, PlayerRespawnPacket.class);
        registerPacket(0x0A, PlayerOnGroundPacket.class);
        registerPacket(0x0B, PlayerPositionPacket.class);
        registerPacket(0x0C, PlayerLookPacket.class);
        registerPacket(0x0D, PlayerPositionAndLookPacket.class);
        registerPacket(0x0E, PlayerDiggingPacket.class);
        registerPacket(0x0F, PlayerBlockPlacementPacket.class);
        registerPacket(0x10, HeldItemChangePacket.class);
        registerPacket(0x11, UseBedPacket.class);
        registerPacket(0x12, AnimationPacket.class);
        registerPacket(0x13, EntityActionPacket.class);
        registerPacket(0x14, NamedEntitySpawnPacket.class);
        registerPacket(0x16, ItemCollectPacket.class);
        registerPacket(0x17, SpawnObjectPacket.class);
        registerPacket(0x18, SpawnMobPacket.class);
        registerPacket(0x19, SpawnPaintingPacket.class);
        registerPacket(0x1A, SpawnExperienceOrbPacket.class);
        registerPacket(0x1C, EntityVelocityPacket.class);
        registerPacket(0x1D, DestroyEntityPacket.class);
        registerPacket(0x1E, EntityExistsPacket.class);
        registerPacket(0x1F, EntityRelMovePacket.class);
        registerPacket(0x20, EntityLookPacket.class);
        registerPacket(0x21, EntityLookAndRelMovePacket.class);
        registerPacket(0x22, EntityTeleportPacket.class);
        registerPacket(0x23, EntityHeadLookPacket.class);
        registerPacket(0x26, EntityStatusPacket.class);
        registerPacket(0x27, AttachEntityPacket.class);
        registerPacket(0x28, EntityMetadataPacket.class);
        registerPacket(0x29, EntityEffectPacket.class);
        registerPacket(0x2A, RemoveEntityEffectPacket.class);
        registerPacket(0x2B, SetExperiencePacket.class);
        registerPacket(0x38, PreChunkPacket.class);
        registerPacket(0x33, MapChunkPacket.class);
        registerPacket(0x34, MultiBlockChangePacket.class);
        registerPacket(0x35, BlockChangePacket.class);
        registerPacket(0x36, BlockActionPacket.class);
        registerPacket(0x3C, ExplosionPacket.class);
        registerPacket(0x3D, SoundOrParticleEffectPacket.class);
        registerPacket(0x46, ChangeGameStatePacket.class);
        registerPacket(0x47, ThunderboltPacket.class);
        registerPacket(0x64, OpenWindowPacket.class);
        registerPacket(0x65, CloseWindowPacket.class);
        registerPacket(0x66, ClickWindowPacket.class);
        registerPacket(0x67, SetSlotPacket.class);
        registerPacket(0x68, SetWindowItemsPacket.class);
        registerPacket(0x69, UpdateWindowPropertyPacket.class);
        registerPacket(0x6A, ConfirmTransactionPacket.class);
        registerPacket(0x6B, CreativeInventoryActionPacket.class);
        registerPacket(0x6C, EnchantItemPacket.class);
        registerPacket(0x82, UpdateSignPacket.class);
        registerPacket(0x83, MapDataPacket.class);
        registerPacket(0x84, UpdateTileEntityPacket.class);
        registerPacket(0xC8, IncrementStatisticPacket.class);
        registerPacket(0xC9, PlayerListItemPacket.class);
        registerPacket(0xCA, PlayerAbilitiesPacket.class);
        registerPacket(0xFA, PluginMessagePacket.class);
        registerPacket(0xFE, ServerListPingPacket.class);
        registerPacket(0xFF, KickPacket.class);
        // 1.3.2
        registerPacket(0xFC, EncryptionKeyResponsePacket.class);
        registerPacket(0xFD, EncryptionKeyRequestPacket.class);
        registerPacket(0xCD, ClientStatusPacket.class);
        registerPacket(0x37, BlockBreakAnimationPacket.class);
        registerPacket(0x14, NamedEntitySpawnPacket.class);
        registerPacket(0xCB, TabCompletePacket.class);
        registerPacket(0xCC, ClientSettingsPacket.class);
        registerPacket(0x3E, NamedSoundEffectPacket.class);
        // 1.5.1
        registerPacket(0x1B, SteerVehiclePacket.class);
        // TODO EntityProperties, Scoreboard stuff
        
    }

    public static Class<? extends Packet> lookupPacket(int opcode) throws UnknownPacketException {
        Class<? extends Packet> clazz = classMappings.get(opcode);
        if (clazz == null)
            throw new UnknownPacketException(opcode);
        return clazz;
    }

    public static void registerPacket(int opcode, Class<? extends Packet> clazz) {
        classMappings.put(opcode, clazz);
    }
}
