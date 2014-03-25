package net.rush.model;

import net.rush.packets.Packet;
import net.rush.packets.misc.ItemStack;
import net.rush.packets.packet.impl.SpawnObjectPacketImpl;
import net.rush.util.Parameter;
import net.rush.world.World;

import org.bukkit.entity.EntityType;

/**
 * Represents an item that is also an {@link Entity} within the world.

 */
public final class ItemEntity extends Entity {

	/**
	 * The item.
	 */
	private final Item item;

	/**
	 * Creates a new item entity.
	 * @param world The world.
	 * @param item The item.
	 */
	public ItemEntity(World world, Item item) {
		super(world, EntityType.DROPPED_ITEM);
		this.item = item;
	}

	/**
	 * Gets the item that this {@link ItemEntity} represents.
	 * @return The item.
	 */
	public Item getItem() {
		return item;
	}

	public Packet createSpawnMessage() {
		int x = position.getPixelX();
		int y = position.getPixelY();
		int z = position.getPixelZ();
		int yaw = rotation.getIntYaw();
		int pitch = rotation.getIntPitch();

		return new SpawnObjectPacketImpl(id, (byte)2, new Position(x, y, z), (byte)pitch, (byte)yaw, 0);
		//return new SpawnDroppedItemPacketImpl(id, (short)item.getId(), (byte)item.getCount(), (short)item.getDamage(), x, y, z, (byte)yaw, (byte)pitch, (byte)roll);
	}

	public void setupMetadata() {
		setMetadata(new Parameter<ItemStack>(Parameter.TYPE_ITEM, 10, new ItemStack(item.getId(), item.getCount(), item.getDamage())));
	}

	@Override
	public Packet createUpdateMessage() {
		// TODO we can probably use some generic implementation for all of
		// these
		return null;
	}

}

