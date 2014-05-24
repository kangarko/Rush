package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ClientSettingsPacket extends Packet {

	public ClientSettingsPacket() {
	}

	@Serialize(type = Type.STRING, order = 0)
	private String locale;
	@Serialize(type = Type.BYTE, order = 1)
	private byte viewDistance;
	@Serialize(type = Type.BYTE, order = 2)
	private byte chatFlags;
	@Serialize(type = Type.BYTE, order = 3)
	private byte difficulty;
	@Serialize(type = Type.BOOL, order = 4)
	private boolean showCape;

	public boolean chatColours;

	public ClientSettingsPacket(String locale, byte viewDistance, byte chatFlags, byte difficulty, boolean showCape) {
		super();
		this.locale = locale;
		this.viewDistance = viewDistance;
		this.chatFlags = chatFlags;
		this.difficulty = difficulty;
		this.showCape = showCape;
	}

	public int getOpcode() {
		return 0xCC;
	}

	public String getLocale() {
		return locale;
	}

	public byte getViewDistance() {
		return viewDistance;
	}

	public byte getChatFlags() {
		return chatFlags;
	}

	public byte getDifficulty() {
		return difficulty;
	}

	public boolean getShowCape() {
		return showCape;
	}

	public String getToStringDescription() {
		return String.format("locale=%s,viewDistance=%d,chatFlags=%d,difficulty=%d", locale, viewDistance, chatFlags, difficulty);
	}

	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		locale = readString(input, 7, false);
		viewDistance = input.readByte();
		chatFlags = input.readByte();
		chatColours = input.readBoolean();
		difficulty = input.readByte();
		showCape = input.readBoolean();
	}
}
