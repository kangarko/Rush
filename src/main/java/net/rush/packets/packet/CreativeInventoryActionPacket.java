package net.rush.packets.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.rush.model.ItemStack;
import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class CreativeInventoryActionPacket extends Packet {
	
	public CreativeInventoryActionPacket() {
	}

	@Serialize(type = Type.SHORT, order = 0)
	private short slot;
	@Serialize(type = Type.ITEM, order = 1)
	private ItemStack item;

	public CreativeInventoryActionPacket(short slot, ItemStack item) {
		super();
		this.slot = slot;
		this.item = item;
	}

	public int getOpcode() {
		return 0x6B;
	}

	public short getSlot() {
		return slot;
	}

	public ItemStack getItem() {
		return item;
	}

	public String getToStringDescription() {
		return String.format("slot=\"%d\",item=\"%s\"", slot, item);
	}
	
	@Override
	public void read17(ByteBufInputStream input) throws IOException {
		slot = input.readShort();
		item = readItemstack(input);
	}
	
	@Override
	public void write17(ByteBufOutputStream output) throws IOException {
		output.writeShort(slot);
		writeItemstack(item, output);
	}

}
