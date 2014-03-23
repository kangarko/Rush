package net.rush.packets.packet.impl;

import net.rush.packets.Packet;
import net.rush.packets.serialization.HashcodeAndEqualsStub;

abstract class AbstractPacket extends HashcodeAndEqualsStub implements Packet {
    @Override
    public Class<? extends Packet> getPacketType() {
        return super.getInterface();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append("@").append(Integer.toHexString(hashCode()));
        sb.append(String.format(" (%1$d/0x0%1$X) ", getOpcode()));
        sb.append("[").append(getToStringDescription()).append("]");
        return sb.toString();
    }

    public abstract String getToStringDescription();
}
