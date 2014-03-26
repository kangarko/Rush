package net.rush.model;

import net.rush.packets.Packet;
import net.rush.packets.packet.impl.SpawnMobPacketImpl;
import net.rush.util.Parameter;
import net.rush.world.World;

import org.bukkit.entity.EntityType;

/**
 * Represents a living entity.
 */
public class LivingEntity extends Mob {

	// TODO ageable

	/**
	 * Creates a new monster.
	 * @param world The world this monster is in.
	 * @param type The type of monster.
	 */
	protected LivingEntity(World world, EntityType type) {
		super(world, type);
		// Set health to prevent sending null metadata that crash client.
		setHealth(20F);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Packet createSpawnMessage() {
		if(position == null)
			throw new NullPointerException("Entity position is null!");
		
		int x = position.getPixelX();
		int y = position.getPixelY();
		int z = position.getPixelZ();
		int yaw = rotation.getIntYaw();
		int pitch = rotation.getIntPitch();	// FIXME byte headYaw?
		return new SpawnMobPacketImpl(id, (byte)getType().getTypeId(), new Position(x, y, z), (byte)yaw, (byte)pitch, (byte)yaw, new Position(0, 0, 0), metadata.clone());
	}


	public void setHealth(float health) {
		setMetadata(new Parameter<Float>(Parameter.TYPE_FLOAT, 6, health));
	}
	
	public float getHealth() {
		return (Float) getMetadata(6).getValue();
	}
	
	public void setName(String name) {
		setMetadata(new Parameter<String>(Parameter.TYPE_STRING, 10, name.replace("&", "§")));
	}
	
	public String getName() {
		return (String) getMetadata(10).getValue();
	}
	
	public void setNameVisible(boolean alwaysVisible) {
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 11, (byte)(alwaysVisible ? 1 : 0)));
	}
	
	public boolean getNameVisible() {
		return (Byte) getMetadata(11).getValue() == 1;
	}
	
}

