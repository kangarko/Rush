package net.rush.packets.packet.impl;

import net.rush.model.Position;
import net.rush.packets.packet.NamedSoundEffectPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class NamedSoundEffectPacketImpl extends AbstractPacket implements NamedSoundEffectPacket {
    @Serialize(type = Type.STRING, order = 0)
    private final String soundName;
    @Serialize(type = Type.INT, order = 1)
    private final int x;
    @Serialize(type = Type.INT, order = 2)
    private final int y;
    @Serialize(type = Type.INT, order = 3)
    private final int z;
    @Serialize(type = Type.FLOAT, order = 4)
    private final float volume;
    @Serialize(type = Type.BYTE, order = 5)
    private final byte pitch;


    public NamedSoundEffectPacketImpl(String soundName, Position pos, float volume, byte pitch) {
        super();
        this.soundName = soundName;
        this.x = (int)pos.getX();
        this.y = (int)pos.getY();
        this.z = (int)pos.getZ();
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override
    public int getOpcode() {
        return 0x3E;
    }

    @Override
    public String getSoundName() {
        return soundName;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public float getVolume() {
        return volume;
    }
    
    @Override
    public byte getPitch() {
        return pitch;
    }

    @Override
    public String getToStringDescription() {
        return String.format("soundName=\"%d\",x=\"%d\",y=\"%d\",z=\"%d\",volume=\"%d\",pitch=\"%d\"",
                        soundName, x, y, z, volume, pitch);
    }
}
