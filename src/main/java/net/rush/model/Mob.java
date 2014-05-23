package net.rush.model;

import net.rush.world.World;

import org.bukkit.entity.EntityType;

/**
 * A Mob is a {@link Player} or {@link LivingEntity}.
.
 */
public abstract class Mob extends Entity {

	/**
	 * Creates a mob within the specified world.
	 * @param world The world.
	 */
	public Mob(World world, EntityType type) {
		super(world, type);
		
		world.spawnEntity(this);
	}
}

