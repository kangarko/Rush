package net.rush.model.entity;

import net.rush.model.EntityAgeable;
import net.rush.util.Parameter;
import net.rush.world.World;

import org.bukkit.entity.EntityType;

public class Zombie extends EntityAgeable {
	
	public Zombie(World world) {
		super(world, EntityType.ZOMBIE);
	}
	
	// METADATA START
	
	public void setChild(boolean isChild) {
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 12, (byte) (isChild ? 1 : 0)));
	}
	
	public boolean isChild() {
		return ((Byte)getMetadata(12).getValue()) == 1;
	}
	
	public void setVillager(boolean isVillager) {
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 13, (byte) (isVillager ? 1 : 0)));
	}
	
	public boolean isVillager() {
		return ((Byte)getMetadata(13).getValue()) == 1;
	}
	
	public void setConverting(boolean isConverting) {
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 14, (byte) (isConverting ? 1 : 0)));
	}
	
	public boolean isConverting() {
		return ((Byte)getMetadata(14).getValue()) == 1;
	}
	
	// METADATA END
}
