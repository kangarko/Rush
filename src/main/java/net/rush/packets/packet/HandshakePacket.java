package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface HandshakePacket extends Packet {
    /*
     * the client uses this slot to send "username;host:port", the server sends a connection-hash
     */
	byte getProtocolVer();
	
    String getUsername();

    String getHost();
    
    int getPort();
}
