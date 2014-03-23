package net.rush.packets.packet.impl;

import net.rush.packets.packet.ItemCollectPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ItemCollectPacketImpl extends AbstractPacket implements ItemCollectPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int collected;
    @Serialize(type = Type.INT, order = 1)
    private final int collector;

    public ItemCollectPacketImpl(int collected, int collector) {
        super();
        this.collected = collected;
        this.collector = collector;
    }

    @Override
    public int getOpcode() {
        return 0x16;
    }

    @Override
    public int getCollected() {
        return collected;
    }

    @Override
    public int getCollector() {
        return collector;
    }

    @Override
    public String getToStringDescription() {
        return String.format("collected=\"%d\",collector=\"%d\"", collected, collector);
    }
}
