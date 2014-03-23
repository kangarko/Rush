package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface ConfirmTransactionPacket extends Packet {
    byte getWindowId();
    short getAction();
    boolean getAccepted();
}
