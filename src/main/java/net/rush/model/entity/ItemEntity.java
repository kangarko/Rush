package net.rush.model.entity;

import net.rush.model.Entity;
import net.rush.model.ItemStack;
import net.rush.model.Player;
import net.rush.packets.Packet;
import net.rush.packets.packet.EntityLookAndRelMovePacket;
import net.rush.packets.packet.EntityLookPacket;
import net.rush.packets.packet.EntityRelMovePacket;
import net.rush.packets.packet.EntityTeleportPacket;
import net.rush.packets.packet.ItemCollectPacket;
import net.rush.packets.packet.SpawnObjectPacket;
import net.rush.util.Parameter;
import net.rush.world.World;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

/**
 * Represents an item that is also an {@link Entity} within the world.

 */
public final class ItemEntity extends Entity {

	/**
	 * The item.
	 */
	private final ItemStack item;

	public int pickupDelay = 40;

	/**
	 * Creates a new item entity.
	 * @param world The world.
	 * @param item The item.
	 */
	public ItemEntity(World world, ItemStack item, double x, double y, double z) {
		super(world, EntityType.DROPPED_ITEM);
		this.item = item;
		this.setPosition(x, y, z);

		getRotation().setYaw((double) (Math.random() * 360.0D));
		setMetadata(new Parameter<ItemStack>(Parameter.TYPE_ITEM, 10, item), false);
	}

	/**
	 * Gets the item that this {@link ItemEntity} represents.
	 * @return The item.
	 */
	public ItemStack getItem() {
		return item;
	}

	private boolean onGround() {
		return world.getTypeId((int) (getPosition().x + motionX), (int) (getPosition().y + motionY), (int) (getPosition().z + motionZ)) != 0;
	}
	
	private boolean inBlock() {
		return world.getTypeId((int) (getPosition().x + motionX), (int) getPosition().y, (int) (getPosition().z + motionZ)) != 0;
	}
	
	// had to copy the loong double from the notchian server to make it accurate with the client
	// getting high accuracy 95-99% still W.I.P
	@Override
	public void pulse() {
		super.pulse();

		if(pickupDelay > 0)
			--pickupDelay;

		motionY -= 0.039999999105930328D;
		
		float slipperiness = 0.98F;
		
		if (onGround()) {
			slipperiness -= 0.5880001F;
			
			if(inBlock()) {
				System.out.println("entity item is in block!");
				slipperiness = 0;
			}
			//slipperiness = (sliperiness of the block item is laying on) * 0.98F;
		}
		
		motionX *= slipperiness;
		if(!inBlock())
			motionY *= 0.98000001907348633D;
		motionZ *= slipperiness;		
		
		if (onGround() && !inBlock())
			motionY *= -0.5D;
		
		// FIXME implementation for debug purposes not for real usage

		for(Entity en : getWorld().getEntities()) {
			if(en == this) 
				continue;

			if (en.getType() == EntityType.DROPPED_ITEM) 
				if(getPosition().distance(en.getPosition()) < 0.3) {
					combineWith((ItemEntity)en);
					continue;
				}

			if(pickupDelay == 0 && en.getType() == EntityType.PLAYER) {
				if(getPosition().distance(en.getPosition()) < 1D) {
					Player pl = (Player) en;
					pl.getSession().send(new ItemCollectPacket(getId(), pl.getId()));
					pl.getInventory().addItem(item);
					pl.playSound(Sound.ITEM_PICKUP, pl.getPosition(), 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
					this.destroy();
				}
				return;
			}

		}

		if (ticksLived >= 6000 || getPosition().y < 0)
			this.destroy();
	}

	public void combineWith(ItemEntity item) {
		if (item == this)
			return;
		if(item.getItem().doMaterialsMatch(this.item)) {
			this.item.count+= item.getItem().getCount();
			item.destroy();
			this.metadataChanged = true;
		}
	}

	public Packet createSpawnMessage() {
		return new SpawnObjectPacket(this, SpawnObjectPacket.ITEM, throwerId, motionX, motionY, motionZ);
	}

	public Packet createUpdateMessage() {
		if(position == null)
			throw new NullPointerException("Entity position is null!");
		
		setX(getPosition().x + motionX);
		setY(getPosition().y + motionY);
		setZ(getPosition().z + motionZ);
		
		//System.out.println("prev pos: " + previousPosition);
		//System.out.println("curr pos: " + position);
		
		boolean moved = !position.equals(previousPosition);
		boolean rotated = !rotation.equals(previousRotation);
		
		int x = position.getPixelX();
		int y = position.getPixelY();
		int z = position.getPixelZ();

		int dx = x - previousPosition.getPixelX();
		int dy = y - previousPosition.getPixelY();
		int dz = z - previousPosition.getPixelZ();

		boolean teleport = dx > Byte.MAX_VALUE || dy > Byte.MAX_VALUE || dz > Byte.MAX_VALUE || dx < Byte.MIN_VALUE || dy < Byte.MIN_VALUE || dz < Byte.MIN_VALUE;
	
		int yaw = rotation.getIntYaw();
		int pitch = rotation.getIntPitch();
		
		if (moved && teleport) {
			return new EntityTeleportPacket(id, x, y, z, yaw, pitch);
		} else if (moved && rotated) {
			return new EntityLookAndRelMovePacket(id, (byte)dx, (byte)dy, (byte)dz, (byte)yaw, (byte)pitch);
		} else if (moved) {
			return new EntityRelMovePacket(id, (byte)dx, (byte)dy, (byte)dz);
		} else if (rotated) {
			return new EntityLookPacket(id, (byte)yaw, (byte)pitch);
		}

		return null;
	}

}

