package net.rush.packets.packet.impl;

import net.rush.packets.packet.PlayerOnGroundPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerOnGroundPacketImpl extends AbstractPacket implements PlayerOnGroundPacket {
    @Serialize(type = Type.BOOL, order = 0)
    private final boolean onGround;

    public PlayerOnGroundPacketImpl(boolean onGround) {
        super();
        this.onGround = onGround;
    }

    @Override
    public int getOpcode() {
        return 0x0A;
    }

    @Override
    public boolean getOnGround() {
        return onGround;
    }

    @Override
    public String getToStringDescription() {
        return String.format("onGround=\"%b\"", onGround);
    }
}
