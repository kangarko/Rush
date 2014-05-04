package net.rush.model.entity;

import net.rush.model.Entity;
import net.rush.model.ItemStack;
import net.rush.model.Player;
import net.rush.packets.Packet;
import net.rush.packets.packet.ItemCollectPacket;
import net.rush.packets.packet.SpawnObjectPacket;
import net.rush.util.Parameter;
import net.rush.util.enums.SoundNames;
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

	public int pickupDelay = 60;

	/**
	 * Creates a new item entity.
	 * @param world The world.
	 * @param item The item.
	 */
	public ItemEntity(World world, double x, double y, double z, final ItemStack item) {
		super(world, EntityType.DROPPED_ITEM);
		this.item = item;
		setPosition(x, y, z);
		getRotation().setYaw((double) (Math.random() * 360.0D));
		setMetadata(new Parameter<ItemStack>(Parameter.TYPE_ITEM, 10, item));
	}

	/**
	 * Gets the item that this {@link ItemEntity} represents.
	 * @return The item.
	 */
	public ItemStack getItem() {
		return item;
	}

	@Override
	public void pulse() {
		super.pulse();

		// FIXME implementation for debug purposes not for real usage
		if(ticksLived > 40)
			for(Entity en : getWorld().getEntities()) {
				if(en == this) 
					continue;
				if(en.getType() == EntityType.PLAYER) 
					if(getPosition().distance(en.getPosition()) < 0.9D) {
						Player pl = (Player) en;
						pl.getSession().send(new ItemCollectPacket(getId(), pl.getId()));
						pl.getInventory().addItem(item);
						pl.playSound(SoundNames.RandomPop, pl.getPosition(), 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
						this.destroy();
						return;
					}
			}

		if(getPosition().getY() < 0)
			this.destroy();
	}

	public Packet createSpawnMessage() {
		int yaw = rotation.getIntYaw();
		int pitch = rotation.getIntPitch();

		return new SpawnObjectPacket(id, SpawnObjectPacket.ITEM, position, yaw, pitch);
	}

	public Packet createUpdateMessage() {
		// TODO we can probably use some generic implementation for all of these
		return null;
	}

}

