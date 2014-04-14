package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ChangeGameStatePacket extends Packet {
	@Serialize(type = Type.BYTE, order = 0)
	private final byte reason;
	@Serialize(type = Type.BYTE, order = 1)
	private final byte gameMode;

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
