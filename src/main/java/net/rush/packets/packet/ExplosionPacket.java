package net.rush.packets.packet;

import java.util.Set;

import net.rush.model.Coordinate;
import net.rush.packets.Packet;

public interface ExplosionPacket extends Packet {
    double getX();
    double getY();
    double getZ();
    float getSize();
    Set<Coordinate> getDestroyedBlocks(); // these BlockCoords are relative!
}
