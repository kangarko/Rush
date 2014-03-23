package net.rush.packets.packet.impl;

import net.rush.packets.packet.UseEntityPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class UseEntityPacketImpl extends AbstractPacket implements UseEntityPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int playerEntityId;
    @Serialize(type = Type.INT, order = 1)
    private final int targetEntityId;
    @Serialize(type = Type.BOOL, order = 2)
    private final boolean isLeftClick;

    public UseEntityPacketImpl(int playerEntityId, int targetEntityId, boolean isLeftClick) {
        super();
        this.playerEntityId = playerEntityId;
        this.targetEntityId = targetEntityId;
        this.isLeftClick = isLeftClick;
    }

    @Override
    public int getOpcode() {
        return 0x07;
    }

    @Override
    public int getPlayerEntityId() {
        return playerEntityId;
    }

    @Override
    public int getTargetEntityId() {
        return targetEntityId;
    }

    @Override
    public boolean getIsLeftClick() {
        return isLeftClick;
    }

    @Override
    public String getToStringDescription() {
        return String.format("playerEntityId=\"%d\",targetEntityId=\"%d\",isLeftClick=\"%b\"",
                playerEntityId, targetEntityId, isLeftClick);
    }
}
