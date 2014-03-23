package net.rush.packets.packet.impl;

import net.rush.packets.packet.EncryptionKeyResponsePacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EncryptionKeyResponsePacketImpl extends AbstractPacket implements EncryptionKeyResponsePacket {
    @Serialize(type = Type.SHORT, order = 0)
    private final short secretLength;
    @Serialize(type = Type.BYTE_ARRAY, order = 1)
    private final byte[] secret;
    @Serialize(type = Type.SHORT, order = 2)
    private final short verifyTokenLength;
    @Serialize(type = Type.BYTE_ARRAY, order = 3)
    private final byte[] verifyTokenResponse;

    /**
     * Empty payload.
     */
    public EncryptionKeyResponsePacketImpl() {
        super();
        this.secretLength = (short)0;
        this.secret = new byte[]{};
        this.verifyTokenLength = (short)0;
        this.verifyTokenResponse = new byte[]{};
    }
    
    public EncryptionKeyResponsePacketImpl(short secretLength, byte[] secret, short verifyTokenLength, byte[] verifyTokenResponse) {
        super();
        this.secretLength = secretLength;
        this.secret = secret;
        this.verifyTokenLength = verifyTokenLength;
        this.verifyTokenResponse = verifyTokenResponse;
    }

    @Override
    public int getOpcode() {
        return 0xFC;
    }

    @Override
    public short getSecretLength() {
        return secretLength;
    }
    
    @Override
    public byte[] getSecret() {
        return secret;
    }
    
    @Override
    public short getVerifyTokenLength() {
        return verifyTokenLength;
    }
    
    @Override
    public byte[] getVerifyTokenResponse() {
        return verifyTokenResponse;
    }
    
    @Override
    public String getToStringDescription() {
        return String.format("reason=\"%a,%b,%c,%d\"", secretLength, secret, verifyTokenLength, verifyTokenResponse);
    }
}
