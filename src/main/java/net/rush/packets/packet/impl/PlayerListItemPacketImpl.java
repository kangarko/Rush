package net.rush.packets.packet.impl;

import net.rush.packets.packet.PlayerListItemPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerListItemPacketImpl extends AbstractPacket implements PlayerListItemPacket {
    @Serialize(type = Type.STRING, order = 0)
    private final String playerName;
    @Serialize(type = Type.BOOL, order = 1)
    private final boolean onlineStatus;
    @Serialize(type = Type.SHORT, order = 2)
    private final short ping;

    public PlayerListItemPacketImpl(String playerName, boolean onlineStatus, short ping) {
        super();
        this.playerName = playerName;
        this.onlineStatus = onlineStatus;
        this.ping = ping;
    }

    @Override
    public int getOpcode() {
        return 0xC9;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public boolean getOnlineStatus() {
        return onlineStatus;
    }

    @Override
    public short getPing() {
        return ping;
    }

    @Override
    public String getToStringDescription() {
        return String.format("playerName=\"%s\",onlineStatus=\"%b\",ping=\"%d\"", playerName,
                onlineStatus, ping);
    }
}
