package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ChangeGameStatePacket extends Packet {
	public ChangeGameStatePacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.BYTE, order = 0)
	private byte reason;
	@Serialize(type = Type.BYTE, order = 1)
	private byte gameMode;

	public ChangeGameStatePacket(byte reason, byte gameMode) {
		super();
		this.reason = reason;
		this.gameMode = gameMode;
	}

	public int getOpcode() {
		return 0x46;
	}

	public byte getReason() {
		return reason;
	}

	public byte getGameMode() {
		return gameMode;
	}

	public String getToStringDescription() {
		return String.format("reason=\"%d\",gameMode=\"%d\"", reason, gameMode);
	}

}
