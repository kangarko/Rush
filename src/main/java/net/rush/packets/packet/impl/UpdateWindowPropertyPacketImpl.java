package net.rush.packets.packet.impl;

import net.rush.packets.packet.UpdateWindowPropertyPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class UpdateWindowPropertyPacketImpl extends AbstractPacket implements
        UpdateWindowPropertyPacket {
    @Serialize(type = Type.BYTE, order = 0)
    private final byte windowId;
    @Serialize(type = Type.SHORT, order = 1)
    private final short property;
    @Serialize(type = Type.SHORT, order = 2)
    private final short value;

    public UpdateWindowPropertyPacketImpl(byte windowId, short property, short value) {
        super();
        this.windowId = windowId;
        this.property = property;
        this.value = value;
    }

    @Override
    public int getOpcode() {
        return 0x69;
    }

    @Override
    public byte getWindowId() {
        return windowId;
    }

    @Override
    public short getProperty() {
        return property;
    }

    @Override
    public short getValue() {
        return value;
    }

    @Override
    public String getToStringDescription() {
        return String.format("windowId=\"%d\",property=\"%d\",value=\"%d\"", windowId, property, value);
    }
}
