package net.rush.model.entity;

import net.rush.model.Entity;
import net.rush.model.ItemStack;
import net.rush.model.Rotation;
import net.rush.packets.Packet;
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
	private final ItemStack item;

	/**
	 * Creates a new item entity.
	 * @param world The world.
	 * @param item The item.
	 */
	public ItemEntity(World world, ItemStack item) {
		super(world, EntityType.DROPPED_ITEM);
		this.item = item;
		this.rotation = new Rotation((float) (Math.random() * 360D), 0);
	}

	/**
	 * Gets the item that this {@link ItemEntity} represents.
	 * @return The item.
	 */
	public ItemStack getItem() {
		return item;
	}

	public Packet createSpawnMessage() {
		int yaw = rotation.getIntYaw();
		int pitch = rotation.getIntPitch();
		
		return new SpawnObjectPacketImpl(id, (byte)2, position, (byte)yaw, (byte)pitch);
	}
	
	public void handleMetadata() {
		setMetadata(new Parameter<ItemStack>(Parameter.TYPE_ITEM, 5, item));
	}

	public Packet createUpdateMessage() {
		// TODO we can probably use some generic implementation for all of these
		return null;
	}

}

