package net.rush.packets.packet;

import net.rush.packets.Packet;

public interface EncryptionKeyResponsePacket extends Packet {
 
	short getSecretLength();
	
	byte[] getSecret();
	
	short getVerifyTokenLength();
	
	byte[] getVerifyTokenResponse();

}
