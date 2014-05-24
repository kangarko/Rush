package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerListItemPacket extends Packet {
	
	public PlayerListItemPacket() {
	}

	@Serialize(type = Type.STRING, order = 0)
	private String playerName;
	@Serialize(type = Type.BOOL, order = 1)
	private boolean onlineStatus;
	@Serialize(type = Type.SHORT, order = 2)
	private short ping;

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

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		playerName = readString(input, 16, false);
		onlineStatus = input.readBoolean();
		ping = input.readShort();
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		writeString(playerName, output, false);
		output.writeBoolean(onlineStatus);
		output.writeShort(ping);
	}
}
