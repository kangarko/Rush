package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.util.Parameter;

public interface SpawnMobPacket extends Packet {
    int getEntityId();
    byte getEntityType();
    int getX();
    int getY();
    int getZ();
    byte getYaw();
    byte getPitch();
    byte getHeadYaw();
    short getVelocityX();
    short getVelocityY();
    short getVelocityZ();
    Parameter<?>[] getMetadata();
}
