package net.rush.packets.packet.impl;

import net.rush.packets.packet.RemoveEntityEffectPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class RemoveEntityEffectPacketImpl extends AbstractPacket implements RemoveEntityEffectPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int entityId;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte effectId;

    public RemoveEntityEffectPacketImpl(int entityId, byte effectId) {
        super();
        this.entityId = entityId;
        this.effectId = effectId;
    }

    @Override
    public int getOpcode() {
        return 0x2A;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public byte getEffectId() {
        return effectId;
    }

    @Override
    public String getToStringDescription() {
        return String.format("entityId=\"%d\",effect=\"%d\"", entityId, effectId);
    }
}
