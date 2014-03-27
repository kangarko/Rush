package net.rush.model.entity;

import net.rush.model.EntityAgeable;
import net.rush.util.Parameter;
import net.rush.util.enums.VillagerType;
import net.rush.world.World;

import org.bukkit.entity.EntityType;

public class Villager extends EntityAgeable {
	
	public Villager(World world) {
		super(world, EntityType.VILLAGER);
	}
	
	// METADATA START
	
	public void setVillagerType(VillagerType type) {
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 16, (byte)type.getType()));
	}
	
	public byte getVillagerType() {
		return ((Byte)getMetadata(16).getValue());
	}
	
	// METADATA END
}
