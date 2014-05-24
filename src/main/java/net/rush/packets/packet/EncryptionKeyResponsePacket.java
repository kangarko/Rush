package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EncryptionKeyResponsePacket extends Packet {
	@Serialize(type = Type.SHORT, order = 0)
	private short secretLength;
	@Serialize(type = Type.BYTE_ARRAY, order = 1)
	private byte[] secret;
	@Serialize(type = Type.SHORT, order = 2)
	private short verifyTokenLength;
	@Serialize(type = Type.BYTE_ARRAY, order = 3)
	private byte[] verifyTokenResponse;

	/**
	 * Empty payload.
	 */
	public EncryptionKeyResponsePacket() {
		super();
		secretLength = (short) 0;
		secret = new byte[] {};
		verifyTokenLength = (short) 0;
		verifyTokenResponse = new byte[] {};
	}

	public EncryptionKeyResponsePacket(short secretLength, byte[] secret, short verifyTokenLength, byte[] verifyTokenResponse) {
		super();
		this.secretLength = secretLength;
		this.secret = secret;
		this.verifyTokenLength = verifyTokenLength;
		this.verifyTokenResponse = verifyTokenResponse;
	}

	public int getOpcode() {
		return 0xFC;
	}

	public short getSecretLength() {
		return secretLength;
	}

	public byte[] getSecret() {
		return secret;
	}

	public short getVerifyTokenLength() {
		return verifyTokenLength;
	}

	public byte[] getVerifyTokenResponse() {
		return verifyTokenResponse;
	}

	public String getToStringDescription() {
		return String.format("reason=\"%a,%b,%c,%d\"", secretLength, secret, verifyTokenLength, verifyTokenResponse);
	}

}
