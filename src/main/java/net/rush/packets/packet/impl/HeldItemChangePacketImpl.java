package net.rush.packets.packet.impl;

import net.rush.packets.packet.HeldItemChangePacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class HeldItemChangePacketImpl extends AbstractPacket implements HeldItemChangePacket {
    @Serialize(type = Type.SHORT, order = 0)
    private final short slotId;

    public HeldItemChangePacketImpl(short slotId) {
        super();
        this.slotId = slotId;
    }

    @Override
    public int getOpcode() {
        return 0x10;
    }

    @Override
    public short getSlotId() {
        return slotId;
    }

    @Override
    public String getToStringDescription() {
        return String.format("slotId=\"%d\"", slotId);
    }
}
