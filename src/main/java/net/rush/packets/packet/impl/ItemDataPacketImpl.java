package net.rush.packets.packet.impl;

import net.rush.packets.packet.ItemDataPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ItemDataPacketImpl extends AbstractPacket implements ItemDataPacket {
    @Serialize(type = Type.SHORT, order = 0)
    private final short itemType;
    @Serialize(type = Type.SHORT, order = 1)
    private final short itemId;
    @Serialize(type = Type.SHORT, order = 2)
    private final byte dataLength;
    @Serialize(type = Type.BYTE_ARRAY, order = 3, moreInfo = 2)
    private final byte[] data;

    public ItemDataPacketImpl(short itemType, short itemId, byte dataLength, byte[] data) {
        super();
        this.itemType = itemType;
        this.itemId = itemId;
        this.dataLength = dataLength;
        this.data = data;
    }

    @Override
    public int getOpcode() {
        return 0x83;
    }

    @Override
    public short getItemType() {
        return itemType;
    }

    @Override
    public short getItemId() {
        return itemId;
    }

    @Override
    public byte getDataLength() {
        return dataLength;
    }

    @Override
    public byte[] getData() {
        return data;
    }

    @Override
    public String getToStringDescription() {
        return String.format("itemType=\"%d\",itemId=\"%d\",dataLength=\"%d\",data=byte[%d]",
                itemType, itemId, dataLength, data.length);
    }
}
