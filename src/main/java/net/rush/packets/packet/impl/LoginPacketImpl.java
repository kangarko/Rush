package net.rush.packets.packet.impl;

import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.WorldType;

import net.rush.packets.packet.LoginPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;
import net.rush.util.enums.Dimension;

public class LoginPacketImpl extends AbstractPacket implements LoginPacket {
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
	public LoginPacketImpl(int entityId, WorldType worldType, GameMode mode, Dimension dimension, Difficulty difficulty, int worldHeight, int maxPlayers) {
        this(entityId, worldType.getName(), (byte)mode.getValue(), dimension.getValue(), (byte)difficulty.getValue(), worldHeight, maxPlayers);
    }
    
    public LoginPacketImpl(int entityId, String emptyString, byte mode, byte dimension, byte difficulty, int worldHeight, int maxPlayers) {
        super();
        this.entityId = entityId;
        this.worldType = emptyString;
        this.mode = mode;
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.worldHeight = worldHeight;
        this.maxPlayers = maxPlayers;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public String getWorldType() {
        return worldType;
    }
    
    @Override
    public byte getMode() {
        return mode;
    }

    @Override
    public byte getDimension() {
        return dimension;
    }

    @Override
    public byte getDifficulty() {
        return difficulty;
    }

    @Override
    public int getWorldHeight() {
        return worldHeight;
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public int getOpcode() {
        return 0x01;
    }

    @Override
    public String getToStringDescription() {
        return String.format("entityId=\"%d\",username=\"%s\",levelType=\"%s\"mode=\"%d\","
                + "dimension=\"%d\",difficulty=\"%d\",worldHeight=\"%d\",maxPlayers=\"%d\"",
                entityId, worldType, mode, dimension, difficulty,
                worldHeight, maxPlayers);
    }
}
