package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class EntityEquipmentPacket extends Packet {
	@Serialize(type = Type.INT, order = 0)
	private final int entityId;
	@Serialize(type = Type.SHORT, order = 1)
	private final short slot;
	@Serialize(type = Type.SHORT, order = 2)
	private final short itemId;
	@Serialize(type = Type.SHORT, order = 3)
	private final short dataValue;

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
