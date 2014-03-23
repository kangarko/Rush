package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface PluginMessagePacket extends Packet {
    String getChannel();
    short getLength();
    byte[] getData();
}
