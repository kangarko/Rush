package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityEquipmentPacket extends Packet {
	public EntityEquipmentPacket() {
		// TODO Auto-generated constructor stub
	}

	@Serialize(type = Type.INT, order = 0)
	private int entityId;
	@Serialize(type = Type.SHORT, order = 1)
	private short slot;
	@Serialize(type = Type.SHORT, order = 2)
	private short itemId;
	@Serialize(type = Type.SHORT, order = 3)
	private short dataValue;

	public EntityEquipmentPacket(int entityId, short slot, short itemId, short dataValue) {
		super();
		this.entityId = entityId;
		this.slot = slot;
		this.itemId = itemId;
		this.dataValue = dataValue;
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

}
