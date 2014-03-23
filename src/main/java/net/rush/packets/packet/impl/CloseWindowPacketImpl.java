package net.rush.packets.packet.impl;

import net.rush.packets.packet.CloseWindowPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class CloseWindowPacketImpl extends AbstractPacket implements CloseWindowPacket {
    @Serialize(type = Type.BYTE, order = 0)
    private final byte windowId;

    public CloseWindowPacketImpl(byte windowId) {
        super();
        this.windowId = windowId;
    }

    @Override
    public int getOpcode() {
        return 0x65;
    }

    @Override
    public byte getWindowId() {
        return windowId;
    }

    @Override
    public String getToStringDescription() {
        return String.format("windowId=\"%d\"", windowId);
    }
}
