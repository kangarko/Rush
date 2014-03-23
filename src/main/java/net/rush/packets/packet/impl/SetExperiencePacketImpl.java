package net.rush.packets.packet.impl;

import net.rush.packets.packet.SetExperiencePacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class SetExperiencePacketImpl extends AbstractPacket implements SetExperiencePacket {
    @Serialize(type = Type.FLOAT, order = 0)
    private final float experienceBar;
    @Serialize(type = Type.SHORT, order = 1)
    private final short level;
    @Serialize(type = Type.SHORT, order = 2)
    private final short totalExperience;

    public SetExperiencePacketImpl(float experienceBar, short level, short totalExperience) {
        super();
        this.experienceBar = experienceBar;
        this.level = level;
        this.totalExperience = totalExperience;
    }

    @Override
    public int getOpcode() {
        return 0x2B;
    }

    @Override
    public float getExperienceBar() {
        return experienceBar;
    }

    @Override
    public short getLevel() {
        return level;
    }

    @Override
    public short getTotalExperience() {
        return totalExperience;
    }

    @Override
    public String getToStringDescription() {
        return String.format("experienceBar=\"%d\",level=\"%d\",totalExperience=\"%d\"",
                experienceBar, level, totalExperience);
    }
}
