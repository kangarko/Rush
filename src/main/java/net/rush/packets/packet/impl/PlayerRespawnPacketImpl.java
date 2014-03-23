package net.rush.packets.packet.impl;

import net.rush.packets.packet.PlayerRespawnPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerRespawnPacketImpl extends AbstractPacket implements PlayerRespawnPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int dimension;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte difficulty;
    @Serialize(type = Type.BYTE, order = 2)
    private final byte gameMode;
    @Serialize(type = Type.SHORT, order = 3)
    private final short worldHeight;
    @Serialize(type = Type.STRING, order = 4)
    private final String levelType;

    public PlayerRespawnPacketImpl(int dimension, byte difficulty, byte gameMode, short worldHeight, String levelType) {
        super();
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.gameMode = gameMode;
        this.worldHeight = worldHeight;
        this.levelType = levelType;
    }

    @Override
    public int getOpcode() {
        return 0x09;
    }

    @Override
    public int getDimension() {
        return dimension;
    }

    @Override
    public byte getDifficulty() {
        return difficulty;
    }

    @Override
    public byte getGameMode() {
        return gameMode;
    }

    @Override
    public short getWorldHeight() {
        return worldHeight;
    }

    @Override
    public String getLevelType() {
        return levelType;
    }

    @Override
    public String getToStringDescription() {
        return String.format("dimension=\"%d\",difficulty=\"%d\",gameMode=\"%d\",worldHeight=\"%d\",levelType=\"%s\"",
                dimension, difficulty, gameMode, worldHeight, levelType);
    }
}
