package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface NamedEntitySpawnPacket extends Packet {
    int getEntityId();

    String getEntityName();

    int getX();

    int getY();

    int getZ();

    byte getYaw();

    byte getPitch();

    short getCurrentItem();
}
