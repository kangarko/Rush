package net.rush.packets.packet.impl;

import net.rush.packets.packet.PlayerAbilitiesPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerAbilitiesPacketImpl extends AbstractPacket implements PlayerAbilitiesPacket {
    @Serialize(type = Type.BYTE, order = 0)
    private final byte flags;
    @Serialize(type = Type.FLOAT, order = 1)
    private final float flySpeed;
    @Serialize(type = Type.FLOAT, order = 2)
    private final float walkSpeed;

    public PlayerAbilitiesPacketImpl(byte flags, float flySpeed, float walkSpeed) {
        super();
        this.flags = flags;
        this.flySpeed = flySpeed;
        this.walkSpeed = walkSpeed;
    }

    @Override
    public int getOpcode() {
        return 0xCA;
    }

    @Override
    public byte getFlags() {
        return flags;
    }
    
    @Override
    public float getFlySpeed() {
        return flySpeed;
    }
    
    @Override
    public float getWalkSpeed() {
        return walkSpeed;
    }


    @Override
    public String getToStringDescription() {
        return String.format("flags=\"%b\",flySpeed=\"%b\",walkSpeed=\"%b\"",flags,flySpeed,walkSpeed);
    }
}
