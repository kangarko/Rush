package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface AttachEntityPacket extends Packet {
    int getEntityId();
    int getVehicleId(); // -1 for unattaching
    byte getLeash();
}
