package net.rush.packets.packet.impl;

import net.rush.packets.packet.ServerListPingPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ServerListPingPacketImpl extends AbstractPacket implements ServerListPingPacket {
	@Serialize(type = Type.BYTE, order = 0)
    private final byte magic;
	
    public ServerListPingPacketImpl(byte magic) {
    	this.magic = 1;
    }

    @Override
    public byte getMagic() {
        return magic;
    }
    
    @Override
    public int getOpcode() {
        return 0xFE;
    }

    @Override
    public String getToStringDescription() {
        return new String();
    }
}
