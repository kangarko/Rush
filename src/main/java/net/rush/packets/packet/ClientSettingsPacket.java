package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface ClientSettingsPacket extends Packet {
    String getLocale();
    byte getViewDistance();
    byte getChatFlags();
    byte getDifficulty();
    boolean getShowCape();
}
