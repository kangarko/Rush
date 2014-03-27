package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface OpenWindowPacket extends Packet {
    byte getWindowId();
    byte getInventoryType();
    String getWindowTitle();
    byte getNumberOfSlots();
    boolean getUseProvidedWindowTitle();
    int getHorseId();
}
