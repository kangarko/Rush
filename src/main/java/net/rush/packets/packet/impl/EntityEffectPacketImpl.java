package net.rush.packets.packet.impl;

import net.rush.packets.packet.EntityEffectPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityEffectPacketImpl extends AbstractPacket implements EntityEffectPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int entityId;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte effectId;
    @Serialize(type = Type.BYTE, order = 2)
    private final byte amplifier;
    @Serialize(type = Type.SHORT, order = 3)
    private final short duration;

    public EntityEffectPacketImpl(int entityId, byte effectId, byte amplifier, short duration) {
        super();
        this.entityId = entityId;
        this.effectId = effectId;
        this.amplifier = amplifier;
        this.duration = duration;
    }

    @Override
    public int getOpcode() {
        return 0x29;
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
    public byte getAmplifier() {
        return amplifier;
    }

    @Override
    public short getDuration() {
        return duration;
    }

    @Override
    public String getToStringDescription() {
        return String.format("entityId=\"%d\",effectId=\"%d\",amplifier=\"%d\",duration=\"%d\"",
                entityId, effectId, amplifier, duration);
    }
}
