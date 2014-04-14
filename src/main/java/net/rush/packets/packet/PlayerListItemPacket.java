package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerListItemPacket extends Packet {
	@Serialize(type = Type.STRING, order = 0)
	private final String playerName;
	@Serialize(type = Type.BOOL, order = 1)
	private final boolean onlineStatus;
	@Serialize(type = Type.SHORT, order = 2)
	private final short ping;

	public PlayerListItemPacket(String playerName, boolean onlineStatus, short ping) {
		super();
		this.playerName = playerName;
		this.onlineStatus = onlineStatus;
		this.ping = ping;
	}

	public int getOpcode() {
		return 0xC9;
	}

	public String getPlayerName() {
		return playerName;
	}

	public boolean getOnlineStatus() {
		return onlineStatus;
	}

	public short getPing() {
		return ping;
	}

	public String getToStringDescription() {
		return String.format("playerName=\"%s\",onlineStatus=\"%b\",ping=\"%d\"", playerName, onlineStatus, ping);
	}
}
