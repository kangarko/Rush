package net.rush.packets;

import net.rush.packets.serialization.HashcodeAndEqualsStub;

public abstract class Packet extends HashcodeAndEqualsStub {
	

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append("@").append(Integer.toHexString(hashCode()));
        sb.append(String.format(" (%1$d/0x0%1$X) ", getOpcode()));
        sb.append("[").append(getToStringDescription()).append("]");
        return sb.toString();
    }

    public Class<? extends Packet> getPacketType() {
    	return super.getInterface();
    }
    
    public abstract String getToStringDescription();
    public abstract int getOpcode();
}
