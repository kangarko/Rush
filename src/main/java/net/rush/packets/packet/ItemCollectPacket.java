package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface ItemCollectPacket extends Packet {
    // these are entity-ids
    int getCollected();
    int getCollector();
}
