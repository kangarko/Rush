package net.rush.model.entity;

import net.rush.model.EntityAgeable;
import net.rush.util.Parameter;
import net.rush.world.World;

import org.bukkit.entity.EntityType;

public class Spider extends EntityAgeable {
	
	public Spider(World world) {
		super(world, EntityType.SPIDER);
	}
	
	@Override
	public String getHurtSound() {
		return "mob.spider.say";
	}
	
	// METADATA
	
	public void setClimbing(boolean climbing) {
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 16, (byte) (climbing ? 1 : 0)));
	}
	
	public boolean isClimbing() {
		return ((Byte)getMetadata(16).getValue()) == 1;
	}
}
