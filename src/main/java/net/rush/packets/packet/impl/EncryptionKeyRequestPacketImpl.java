package net.rush.packets.packet.impl;

import net.rush.packets.packet.EncryptionKeyRequestPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EncryptionKeyRequestPacketImpl extends AbstractPacket implements EncryptionKeyRequestPacket {
    @Serialize(type = Type.STRING, order = 0)
    private final String serverId;
    @Serialize(type = Type.SHORT, order = 1)
    private final short publicKeyLength;
    @Serialize(type = Type.BYTE_ARRAY, order = 2)
    private final byte[] publicKey;
    @Serialize(type = Type.SHORT, order = 3)
    private final short verifyTokenLength;
    @Serialize(type = Type.BYTE_ARRAY, order = 4)
    private final byte[] verifyToken;

    public EncryptionKeyRequestPacketImpl(String serverId, short publicKeyLength, byte[] publicKey, short verifyTokenLength, byte[] verifyToken) {
        super();
        this.serverId = serverId;
        this.publicKeyLength = publicKeyLength;
        this.publicKey = publicKey;
        this.verifyTokenLength = verifyTokenLength;
        this.verifyToken = verifyToken;
    }

    @Override
    public int getOpcode() {
        return 0xFD;
    }

    @Override
    public String getServerId() {
        return serverId;
    }
    
    @Override
    public short getPublicKeyLength() {
        return publicKeyLength;
    }
    
    @Override
    public byte[] getPublicKey() {
        return publicKey;
    }
    
    @Override
    public short getVerifyTokenLength() {
        return verifyTokenLength;
    }
    
    @Override
    public byte[] getVerifyToken() {
        return verifyToken;
    }

    @Override
    public String getToStringDescription() {
        return String.format("reason=\"%s\"", serverId);
    }
}
