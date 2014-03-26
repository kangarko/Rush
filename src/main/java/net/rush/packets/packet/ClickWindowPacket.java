package net.rush.packets.packet;

import net.rush.model.Item;
import net.rush.packets.Packet;

public interface ClickWindowPacket extends Packet {
    byte getWindowId();
    short getSlot();
    byte getRightClick(); // 1 when rightclicking, otherwise 0
    short getAction();
    boolean getShiftHold();
    Item getClickedItem();
}
