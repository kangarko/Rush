package net.rush.packets.packet.impl;

import net.rush.packets.packet.AnimationPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class AnimationPacketImpl extends AbstractPacket implements AnimationPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int entityId;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte animation;

    public AnimationPacketImpl(int entityId, byte animation) {
        super();
        this.entityId = entityId;
        this.animation = animation;
    }

    @Override
    public int getOpcode() {
        return 0x12;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public byte getAnimation() {
        return animation;
    }
    
    @Override
    public String getToStringDescription() {
        return String.format("entityId=\"%d\",animation=\"%d\"", entityId, animation);
    }
}
