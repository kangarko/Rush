package net.rush.packets.packet.impl;

import net.rush.packets.packet.SoundOrParticleEffectPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SoundOrParticleEffectPacketImpl extends AbstractPacket implements
        SoundOrParticleEffectPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int effectId;
    @Serialize(type = Type.INT, order = 1)
    private final int x;
    @Serialize(type = Type.BYTE, order = 2)
    private final byte y;
    @Serialize(type = Type.INT, order = 3)
    private final int z;
    @Serialize(type = Type.INT, order = 4)
    private final int data;
    @Serialize(type = Type.BOOL, order = 5)
    private final boolean relativeVolume;

    public SoundOrParticleEffectPacketImpl(int effectId, int x, byte y, int z, int data, boolean relativeVolume) {
        super();
        this.effectId = effectId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.data = data;
        this.relativeVolume = relativeVolume;
    }

    @Override
    public int getOpcode() {
        return 0x3D;
    }

    @Override
    public int getEffectId() {
        return effectId;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public byte getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public int getData() {
        return data;
    }
    
    @Override
    public boolean getRelativeVolume() {
        return relativeVolume;
    }

    @Override
    public String getToStringDescription() {
        return String.format("effectId=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\",data=\"%d\"",
                effectId, x, y, z, data);
    }
}
