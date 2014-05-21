package net.rush.model;

import java.util.ArrayList;
import java.util.List;

import net.rush.model.entity.ai.EntityAI;
import net.rush.model.entity.ai.WorldThreadAI;
import net.rush.packets.Packet;
import net.rush.packets.packet.EntityLookAndRelMovePacket;
import net.rush.packets.packet.EntityLookPacket;
import net.rush.packets.packet.EntityRelMovePacket;
import net.rush.packets.packet.EntityTeleportPacket;
import net.rush.packets.packet.SpawnMobPacket;
import net.rush.util.Parameter;
import net.rush.world.World;

import org.bukkit.entity.EntityType;

/**
 * Represents a living entity.
 */
public class LivingEntity extends Mob {

	// TODO Horse, Bat, Sheep
	
	protected int health;
	protected int maxHealth;
	protected List<EntityAI> aiTasks = new ArrayList<EntityAI>();
	
	/**
	 * Creates a new living entity (e.g. zombie or a pig).
	 * @param world The world this living entity is in.
	 * @param type The type of living entity.
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
		return new SpawnMobPacket(id, (byte)getType().getTypeId(), new Position(x, y, z), (byte)yaw, (byte)pitch, (byte)yaw, new Position(0, 0, 0), metadata.clone());
	}
	
	@Override
	public Packet createUpdateMessage() {		
		if(position == null)
			throw new NullPointerException("Entity position is null!");
		
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
	
	@Override
	public void pulse() {
		for(EntityAI task : aiTasks) {
			WorldThreadAI.addTask(task);
		}
	}

	// METADATA START
	
	public float getHealth() {
		return (Float) getMetadata(6).getValue();
	}

	public void setHealth(float health) {
		setMetadata(new Parameter<Float>(Parameter.TYPE_FLOAT, 6, health));
		this.health = (int)health;
	}
	
	public int getPotionEffectColor() {
		return (Integer) getMetadata(7).getValue();
	}
	
	public void setPotionEffectColor(int effectColor) {
		setMetadata(new Parameter<Integer>(Parameter.TYPE_INT, 7, effectColor));
	}
	
	public byte getIsPotionEffectAmbient() {
		return (Byte) getMetadata(8).getValue();
	}
	
	public void setIsPotionEffectAmbient(boolean ambient) {
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 8, ambient ? (byte)1 : (byte)0));
	}
	
	public byte getArrowsInEntity() {
		return (Byte) getMetadata(9).getValue();
	}
	
	public void setArrowsInEntity(int howManyArrows) {
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 9, (byte)howManyArrows));
	}
	
	public String getName() {
		return (String) getMetadata(10).getValue();
	}
	
	public void setName(String name) {
		setMetadata(new Parameter<String>(Parameter.TYPE_STRING, 10, name.replace("&", "§")));
	}
	
	public boolean getNameVisible() {
		return (Byte) getMetadata(11).getValue() == 1;
	}
	
	public void setNameVisible(boolean alwaysVisible) {
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 11, (byte)(alwaysVisible ? 1 : 0)));
	}
	
	// METADATA END
}

