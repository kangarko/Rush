package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.util.Parameter;

public interface EntityMetadataPacket extends Packet {
    int getEntityId();
    Parameter<?>[] getMetadata();
}
