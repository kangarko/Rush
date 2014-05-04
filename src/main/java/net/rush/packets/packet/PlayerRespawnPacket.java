package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerRespawnPacket extends Packet {
	
	public PlayerRespawnPacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int dimension;
	@Serialize(type = Type.BYTE, order = 1)
	private byte difficulty;
	@Serialize(type = Type.BYTE, order = 2)
	private byte gameMode;
	@Serialize(type = Type.SHORT, order = 3)
	private short worldHeight;
	@Serialize(type = Type.STRING, order = 4)
	private String levelType;

	public PlayerRespawnPacket(int dimension, byte difficulty, byte gameMode, short worldHeight, String levelType) {
		super();
		this.dimension = dimension;
		this.difficulty = difficulty;
		this.gameMode = gameMode;
		this.worldHeight = worldHeight;
		this.levelType = levelType;
	}

	public int getOpcode() {
		return 0x09;
	}

	public int getDimension() {
		return dimension;
	}

	public byte getDifficulty() {
		return difficulty;
	}

	public byte getGameMode() {
		return gameMode;
	}

	public short getWorldHeight() {
		return worldHeight;
	}

	public String getLevelType() {
		return levelType;
	}

	public String getToStringDescription() {
		return String.format("dimension=\"%d\",difficulty=\"%d\",gameMode=\"%d\",worldHeight=\"%d\",levelType=\"%s\"", dimension, difficulty, gameMode, worldHeight, levelType);
	}
	
	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		dimension = input.readInt();
		difficulty = (byte)input.readUnsignedByte();
		gameMode = (byte)input.readUnsignedByte();
		levelType = readString(input, 65000, false);
	}
	
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeInt(dimension);
		output.writeByte(difficulty);
		output.writeByte(gameMode);
		writeString(levelType, output, false);
	}

}
