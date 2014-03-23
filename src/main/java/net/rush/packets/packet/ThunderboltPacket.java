package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface ThunderboltPacket extends Packet {
    int getEntityId();
    byte getUnknown_byte_0(); // I'm not sure here... wiki.vg says it's a boolean
                              // but the minecraft-source says it's a byte
    int getX();
    int getY();
    int getZ();
}
