package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EncryptionKeyRequestPacket extends Packet {
	@Serialize(type = Type.STRING, order = 0)
	private String serverId;
	@Serialize(type = Type.SHORT, order = 1)
	private short publicKeyLength;
	@Serialize(type = Type.BYTE_ARRAY, order = 2)
	private byte[] publicKey;
	@Serialize(type = Type.SHORT, order = 3)
	private short verifyTokenLength;
	@Serialize(type = Type.BYTE_ARRAY, order = 4)
	private byte[] verifyToken;

	public EncryptionKeyRequestPacket() {
	}

	public EncryptionKeyRequestPacket(String serverId, short publicKeyLength, byte[] publicKey, short verifyTokenLength, byte[] verifyToken) {
		super();
		this.serverId = serverId;
		this.publicKeyLength = publicKeyLength;
		this.publicKey = publicKey;
		this.verifyTokenLength = verifyTokenLength;
		this.verifyToken = verifyToken;
	}

	public int getOpcode() {
		return 0xFD;
	}

	public String getServerId() {
		return serverId;
	}

	public short getPublicKeyLength() {
		return publicKeyLength;
	}

	public byte[] getPublicKey() {
		return publicKey;
	}

	public short getVerifyTokenLength() {
		return verifyTokenLength;
	}

	public byte[] getVerifyToken() {
		return verifyToken;
	}

	public String getToStringDescription() {
		return String.format("reason=\"%s\"", serverId);
	}

}
