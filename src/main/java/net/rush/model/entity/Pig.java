package net.rush.model.entity;

import net.rush.model.EntityAgeable;
import net.rush.model.entity.ai.EntityAITest;
import net.rush.util.Parameter;
import net.rush.world.World;

import org.bukkit.entity.EntityType;

public class Pig extends EntityAgeable {
	
	public Pig(World world) {
		super(world, EntityType.PIG);
		
		aiTasks.add(new EntityAITest(this));
	}
	
	@Override
	public String getHurtSound() {
		return "mob.pig.say";
	}
	
	// METADATA
	
	public void setSaddle(boolean hasSadle) {
		byte hasSadleByte = (byte) (hasSadle ? 1 : 0);
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 16, hasSadleByte));
	}
	
	public boolean hasSadle() {
		return ((Byte)getMetadata(16).getValue()) == 1;
	}
}
