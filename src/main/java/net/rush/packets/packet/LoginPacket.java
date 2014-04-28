package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;
import net.rush.util.enums.Dimension;

public class LoginPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.STRING, order = 1)
	private String worldType;
	@Serialize(type = Type.BYTE, order = 2)
	private byte mode;
	@Serialize(type = Type.BYTE, order = 3)
	private byte dimension;
	@Serialize(type = Type.BYTE, order = 4)
	private byte difficulty;
	@Serialize(type = Type.UNSIGNED_BYTE, order = 5)
	private int worldHeight;
	@Serialize(type = Type.UNSIGNED_BYTE, order = 6)
	private int maxPlayers;

	public LoginPacket() {}
	
	public LoginPacket(int entityId, String worldType, int gamemode, Dimension dimension, int difficulty, int worldHeight, int maxPlayers) {
		this(entityId, worldType, (byte) gamemode, dimension.getValue(), (byte) difficulty, worldHeight, maxPlayers);
	}

	protected LoginPacket(int entityId, String emptyString, byte mode, byte dimension, byte difficulty, int worldHeight, int maxPlayers) {
		super();
		this.entityId = entityId;
		worldType = emptyString;
		this.mode = mode;
		this.dimension = dimension;
		this.difficulty = difficulty;
		this.worldHeight = worldHeight;
		this.maxPlayers = maxPlayers;
	}

	public int getEntityId() {
		return entityId;
	}

	public String getWorldType() {
		return worldType;
	}

	public byte getMode() {
		return mode;
	}

	public byte getDimension() {
		return dimension;
	}

	public byte getDifficulty() {
		return difficulty;
	}

	public int getWorldHeight() {
		return worldHeight;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public int getOpcode() {
		return 0x01;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",username=\"%s\",levelType=\"%s\"mode=\"%d\"," + "dimension=\"%d\",difficulty=\"%d\",worldHeight=\"%d\",maxPlayers=\"%d\"", 
				entityId, worldType, mode, dimension, difficulty, worldHeight, maxPlayers);
	}
}
