package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface SpawnPaintingPacket extends Packet {
    int getEntityId();
    String getTitle(); // stringly typed programming, mojang! :/
    int getX();
    int getY();
    int getZ();
    int getDirection();
}
