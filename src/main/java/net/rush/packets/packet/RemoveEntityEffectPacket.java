package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface RemoveEntityEffectPacket extends Packet {
    int getEntityId();
    byte getEffectId();
}
