package net.rush.packets.packet.impl;

import net.rush.packets.packet.HandshakePacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class HandshakePacketImpl extends AbstractPacket implements HandshakePacket {
    @Serialize(type = Type.BYTE, order = 0)
    private final byte protocolVer;
    @Serialize(type = Type.STRING, order = 1)
    private final String username;
    @Serialize(type = Type.STRING, order = 2)
    private final String host;
    @Serialize(type = Type.INT, order = 3)
    private final int port;

    public HandshakePacketImpl(byte protocolVer, String username, String host, int port) {
        super();
        this.protocolVer = protocolVer;
        this.username = username;
        this.host = host;
        this.port = port;
    }
    
    @Override
    public byte getProtocolVer() {
        return protocolVer;
    }

    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
    public String getHost() {
        return host;
    }
    
    @Override
    public int getPort() {
        return port;
    }

    @Override
    public int getOpcode() {
        return 0x02;
    }

    @Override
    public String getToStringDescription() {
        return "message=\"protocol:" + protocolVer + ",nick:"+ username + ",host:"+ host + ",port:" + port + "\"";
    }
}
