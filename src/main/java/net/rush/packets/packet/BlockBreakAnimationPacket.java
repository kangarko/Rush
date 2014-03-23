package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface BlockBreakAnimationPacket extends Packet {
    int getEntityId();
    int getX();
    int getY();
    int getZ();
    byte getStage();
}
