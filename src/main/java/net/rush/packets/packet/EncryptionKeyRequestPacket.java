package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface EncryptionKeyRequestPacket extends Packet {
    String getServerId();

    short getPublicKeyLength();
    
    byte[] getPublicKey();
    
    short getVerifyTokenLength();
    
    byte[] getVerifyToken();
}
