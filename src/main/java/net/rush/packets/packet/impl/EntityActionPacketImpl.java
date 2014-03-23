package net.rush.packets.packet.impl;

import net.rush.packets.packet.EntityActionPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityActionPacketImpl extends AbstractPacket implements EntityActionPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int entityId;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte actionId;
    @Serialize(type = Type.INT, order = 2)
    private final int horseJumpBoost;

    public EntityActionPacketImpl(int entityId, byte actionId, int horseJumpBoost) {
        super();
        this.entityId = entityId;
        this.actionId = actionId;
        this.horseJumpBoost = horseJumpBoost;
    }

    @Override
    public int getOpcode() {
        return 0x13;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public byte getActionId() {
        return actionId;
    }

    @Override
    public int getHorseJumpBoost() {
        return horseJumpBoost;
    }
    
    @Override
    public String getToStringDescription() {
        return String.format("entityId=\"%d\",actionId=\"%d\"", entityId, actionId);
    }
}
