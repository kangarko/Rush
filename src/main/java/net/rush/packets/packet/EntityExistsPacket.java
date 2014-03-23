package net.rush.packets.packet;

import net.rush.packets.Packet;

// yay for useless packets of doom...
public interface EntityExistsPacket extends Packet {
    int getEntityId();
}
