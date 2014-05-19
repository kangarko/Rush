package net.rush.packets.packet;

import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.model.ItemStack;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityEquipmentPacket extends Packet {
	
    public static final int HELD_ITEM = 0;
    public static final int BOOTS_SLOT = 1;
    public static final int LEGGINGS_SLOT = 2;
    public static final int CHESTPLATE_SLOT = 3;
    public static final int HELMET_SLOT = 4;
	
	public EntityEquipmentPacket() {
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.SHORT, order = 1)
	private short slot;
	@Serialize(type = Type.SHORT, order = 2)
	private short itemId;
	@Serialize(type = Type.SHORT, order = 3)
	private short dataValue;

	private ItemStack item;
	
	public EntityEquipmentPacket(int entityId, short slot, short itemId, short dataValue) {
		super();
		this.entityId = entityId;
		this.slot = slot;
		this.itemId = itemId;
		this.dataValue = dataValue;
		this.item = new ItemStack(itemId, 1, dataValue);
	}

	public int getOpcode() {
		return 0x05;
	}

	public int getEntityId() {
		return entityId;
	}

	public short getSlot() {
		return slot;
	}

	public short getItemId() {
		return itemId;
	}

	public short getDataValue() {
		return dataValue;
	}

	public String getToStringDescription() {
		return String.format("entityId=\"%d\",slot=\"%d\",itemId=\"%d\",dataValue=\"%d\"", entityId, slot, itemId, dataValue);
	}

	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeInt(entityId);
		output.writeShort(slot);
		writeItemstack(item, output);
	}
}
