package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface CloseWindowPacket extends Packet {
    byte getWindowId();
}
