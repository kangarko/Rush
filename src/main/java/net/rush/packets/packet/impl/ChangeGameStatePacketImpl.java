package net.rush.packets.packet.impl;

import net.rush.packets.packet.ChangeGameStatePacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ChangeGameStatePacketImpl extends AbstractPacket implements ChangeGameStatePacket {
    @Serialize(type = Type.BYTE, order = 0)
    private final byte reason;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte gameMode;

    public ChangeGameStatePacketImpl(byte reason, byte gameMode) {
        super();
        this.reason = reason;
        this.gameMode = gameMode;
    }

    @Override
    public int getOpcode() {
        return 0x46;
    }

    @Override
    public byte getReason() {
        return reason;
    }

    @Override
    public byte getGameMode() {
        return gameMode;
    }

    @Override
    public String getToStringDescription() {
        return String.format("reason=\"%d\",gameMode=\"%d\"", reason, gameMode);
    }
}
