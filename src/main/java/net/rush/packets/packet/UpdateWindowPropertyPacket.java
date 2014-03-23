package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface UpdateWindowPropertyPacket extends Packet {
    byte getWindowId();
    short getProperty();
    short getValue();
}
