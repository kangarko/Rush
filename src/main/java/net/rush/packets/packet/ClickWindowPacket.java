package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.misc.ItemStack;

public interface ClickWindowPacket extends Packet {
    byte getWindowId();
    short getSlot();
    byte getRightClick(); // 1 when rightclicking, otherwise 0
    short getAction();
    boolean getShiftHold();
    ItemStack getClickedItem();
}
