package net.rush.model.entity;

import net.rush.model.EntityAgeable;
import net.rush.util.Parameter;
import net.rush.util.enums.ColorEnum;
import net.rush.world.World;

import org.bukkit.entity.EntityType;

public class Wolf extends EntityAgeable {
	
	private boolean pissed = false;
	
	public Wolf(World world) {
		super(world, EntityType.WOLF);
	}
	
	// METADATA START
	
	/**
	 * Also means it he is angry, aggressive, attacking, etc.
	 * I choosed pissed xD
	 */
	public void setPissed(boolean pissedOff) {
		this.pissed = pissedOff;
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 16, (byte) (pissedOff ? 0x02: 0)));
		// TODO Do this wipe the other parameters in index 16 ?
	}
	
	public boolean isPissed() {
		return pissed;
	}
	
	public void setHealth(float health) {
		setMetadata(new Parameter<Float>(Parameter.TYPE_FLOAT, 18, health));
	}
	
	public float getHealth() {
		return ((Float)getMetadata(18).getValue());
	}
	
	public void setBegging(boolean begging) {
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 19, (byte) (begging ? 0x02: 0)));
	}
	
	public boolean isBegging() {
		return ((Byte)getMetadata(19).getValue()) == 1;
	}
	
	public void setColarColor(ColorEnum color) {
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 20, (byte)color.getColor()));
	}
	
	public byte getColarColor() {
		return ((Byte)getMetadata(20).getValue());
	}	
	
	
	// METADATA END
}
