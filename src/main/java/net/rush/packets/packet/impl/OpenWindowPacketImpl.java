package net.rush.packets.packet.impl;

import net.rush.packets.packet.OpenWindowPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class OpenWindowPacketImpl extends AbstractPacket implements OpenWindowPacket {
    @Serialize(type = Type.BYTE, order = 0)
    private final byte windowId;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte inventoryType;
    @Serialize(type = Type.STRING, order = 2)
    private final String windowTitle;
    @Serialize(type = Type.BYTE, order = 3)
    private final byte numberOfSlots;
    @Serialize(type = Type.BOOL, order = 4)
    private final boolean useProvidedWindowTitle;
    @Serialize(type = Type.INT, order = 5)
    private final int horseId;
    
    public OpenWindowPacketImpl(byte windowId, byte inventoryType, String windowTitle, byte numberOfSlots, boolean useProvidedWindowTitle, int horseId) {
        super();
        this.windowId = windowId;
        this.inventoryType = inventoryType;
        this.windowTitle = windowTitle;
        this.numberOfSlots = numberOfSlots;
        this.useProvidedWindowTitle = useProvidedWindowTitle;
        this.horseId = horseId;
    }

    @Override
    public int getOpcode() {
        return 0x64;
    }

    @Override
    public byte getWindowId() {
        return windowId;
    }

    @Override
    public byte getInventoryType() {
        return inventoryType;
    }

    @Override
    public String getWindowTitle() {
        return windowTitle;
    }

    @Override
    public byte getNumberOfSlots() {
        return numberOfSlots;
    }
    
    @Override
    public boolean getUseProvidedWindowTitle() {
        return useProvidedWindowTitle;
    }
    
    @Override
    public int getHorseId() {
        return horseId;
    }

    @Override
    public String getToStringDescription() {
        return String.format("windowId=\"%d\",inventoryType=\"%d\",windowTitle=\"%s\",numberOfSlots=\"%d\",useProvidedTitle=\"%b\",horseIdé\"%d\"",
                windowId, inventoryType, windowTitle, numberOfSlots, useProvidedWindowTitle, horseId);
    }
}
