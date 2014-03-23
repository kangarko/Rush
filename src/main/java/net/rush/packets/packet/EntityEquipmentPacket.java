package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface EntityEquipmentPacket extends Packet {
    int getEntityId();
    short getSlot();
    short getItemId();
    short getDataValue();
}
