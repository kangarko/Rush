package net.rush.model.entity;

import net.rush.model.EntityAgeable;
import net.rush.util.Parameter;
import net.rush.world.World;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

@SuppressWarnings("deprecation")
public class Enderman extends EntityAgeable {
	
	public Enderman(World world) {
		super(world, EntityType.ENDERMAN);
	}
	
	// METADATA START
	
	public void setCarriedBlock(Material mat) {
		setMetadata(new Parameter<Short>(Parameter.TYPE_SHORT, 16, (short)mat.getId()));
	}
	
	public Material getCarriedBlock() {
		return Material.getMaterial((int)((Short)getMetadata(16).getValue()));
	}
	
	public void setCarriedBlockData(int data) {
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 17, (byte)data));
	}
	
	public byte getCarriedBlockData() {
		return ((Byte)getMetadata(17).getValue());
	}
	
	public void setScreaming(boolean isScreaming) {
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 18, (byte) (isScreaming ? 1 : 0)));
	}
	
	public boolean isScreaming() {
		return ((Byte)getMetadata(18).getValue()) == 1;
	}
	
	// METADATA END
}
