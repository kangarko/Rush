package net.rush.packets.packet.impl;

import net.rush.packets.packet.UpdateHealthPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class UpdateHealthPacketImpl extends AbstractPacket implements UpdateHealthPacket {
    @Serialize(type = Type.FLOAT, order = 0)
    private final float health;
    @Serialize(type = Type.SHORT, order = 1)
    private final short food;
    @Serialize(type = Type.FLOAT, order = 2)
    private final float saturation;

    public UpdateHealthPacketImpl(float health, short food, float saturation) {
        super();
        this.health = health;
        this.food = food;
        this.saturation = saturation;
    }
    
    @Override
    public int getOpcode() {
        return 0x08;
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public short getFood() {
        return food;
    }

    @Override
    public float getSaturation() {
        return saturation;
    }

    @Override
    public String getToStringDescription() {
        return String.format("health=\"%d\",food=\"%d\",saturation=\"%d\"", health, food, saturation);
    }
}
