package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface SpawnPositionPacket extends Packet {
    int getX();

    int getY();

    int getZ();
}
