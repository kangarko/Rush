package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class ItemDataPacket extends Packet {
	public ItemDataPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.SHORT, order = 0)
	private short itemType;
	@Serialize(type = Type.SHORT, order = 1)
	private short itemId;
	@Serialize(type = Type.SHORT, order = 2)
	private byte dataLength;
	@Serialize(type = Type.BYTE_ARRAY, order = 3, moreInfo = 2)
	private byte[] data;

	public ItemDataPacket(short itemType, short itemId, byte dataLength,
			byte[] data) {
		super();
		this.itemType = itemType;
		this.itemId = itemId;
		this.dataLength = dataLength;
		this.data = data;
	}

	public int getOpcode() {
		return 0x83;
	}

	public short getItemType() {
		return itemType;
	}

	public short getItemId() {
		return itemId;
	}

	public byte getDataLength() {
		return dataLength;
	}

	public byte[] getData() {
		return data;
	}

	public String getToStringDescription() {
		return String
				.format("itemType=\"%d\",itemId=\"%d\",dataLength=\"%d\",data=byte[%d]",
						itemType, itemId, dataLength, data.length);
	}

	@Override
	public void read17(ByteBufInputStream input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write17(ByteBufOutputStream output) {
		// TODO Auto-generated method stub

	}
}
