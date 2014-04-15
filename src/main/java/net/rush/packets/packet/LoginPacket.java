package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;
import net.rush.util.enums.Dimension;

import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.WorldType;

public class LoginPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int entityId;
	@Serialize(type = Type.STRING, order = 1)
	private final String worldType;
	@Serialize(type = Type.BYTE, order = 2)
	private final byte mode;
	@Serialize(type = Type.BYTE, order = 3)
	private final byte dimension;
	@Serialize(type = Type.BYTE, order = 4)
	private final byte difficulty;
	@Serialize(type = Type.UNSIGNED_BYTE, order = 5)
	private final int worldHeight;
	@Serialize(type = Type.UNSIGNED_BYTE, order = 6)
	private final int maxPlayers;

	@SuppressWarnings("deprecation")
	public LoginPacket(int entityId, WorldType worldType, GameMode mode, Dimension dimension, Difficulty difficulty, int worldHeight, int maxPlayers) {
		this(entityId, worldType.getName(), (byte) mode.getValue(), dimension.getValue(), (byte) difficulty.getValue(), worldHeight, maxPlayers);
	}

	public LoginPacket(int entityId, String emptyString, byte mode, byte dimension, byte difficulty, int worldHeight, int maxPlayers) {
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
