package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface SpawnExperienceOrbPacket extends Packet {
    int getEntityId();
    int getX();
    int getY();
    int getZ();
    short getCount();
}
