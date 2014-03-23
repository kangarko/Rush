package net.rush.model.entity;

import net.rush.model.LivingEntity;
import net.rush.util.Parameter;
import net.rush.world.World;

import org.bukkit.entity.EntityType;

public class Pig extends LivingEntity {
	
	public Pig(World world) {
		super(world, EntityType.PIG);
	}
	
	public void setSaddle(boolean hasSadle) {
		byte hasSadleByte = (byte) (hasSadle ? 1 : 0);
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 16, hasSadleByte));
	}
	
	public boolean hasSadle() {
		return ((Byte)getMetadata(16).getValue()) == 1;
	}
}
