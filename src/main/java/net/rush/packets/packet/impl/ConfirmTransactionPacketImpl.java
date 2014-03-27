package net.rush.packets.packet.impl;

import net.rush.packets.packet.ConfirmTransactionPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ConfirmTransactionPacketImpl extends AbstractPacket implements ConfirmTransactionPacket {
    @Serialize(type = Type.BYTE, order = 0)
    private final byte windowId;
    @Serialize(type = Type.SHORT, order = 1)
    private final short action;
    @Serialize(type = Type.BOOL, order = 2)
    private final boolean accepted;

    public ConfirmTransactionPacketImpl(byte windowId, short action, boolean accepted) {
        super();
        this.windowId = windowId;
        this.action = action;
        this.accepted = accepted;
    }

    @Override
    public int getOpcode() {
        return 0x6A;
    }

    @Override
    public byte getWindowId() {
        return windowId;
    }

    @Override
    public short getAction() {
        return action;
    }

    @Override
    public boolean getAccepted() {
        return accepted;
    }

    @Override
    public String getToStringDescription() {
        return String.format("windowId=\"%d\",action=\"%d\",accepted=\"%b\"", windowId, action, accepted);
    }
}
