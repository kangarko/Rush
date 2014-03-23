package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface AnimationPacket extends Packet {
    int getEntityId();
    byte getAnimation();
}
