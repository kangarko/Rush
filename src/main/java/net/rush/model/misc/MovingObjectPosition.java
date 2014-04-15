package net.rush.model.misc;

import net.rush.model.Entity;

public class MovingObjectPosition {
	/** What type of ray trace hit was this? 0 = block, 1 = entity */
	public MovingType typeOfHit;

	/** x coordinate of the block ray traced against */
	public int blockX;

	/** y coordinate of the block ray traced against */
	public int blockY;

	/** z coordinate of the block ray traced against */
	public int blockZ;

	/**
	 * Which side was hit. If its -1 then it went the full length of the ray trace. Bottom = 0, Top = 1, East = 2, West
	 * = 3, North = 4, South = 5.
	 */
	public int sideHit;

	/** The vector position of the hit */
	public Vec3 vectorHit;

	/** The hit entity */
	public Entity entityHit;

	public MovingObjectPosition(int blockX, int blockY, int blockZ, int sideHit, Vec3 vectorHit) {
		this.typeOfHit = MovingType.TILE;
		this.blockX = blockX;
		this.blockY = blockY;
		this.blockZ = blockZ;
		this.sideHit = sideHit;
		this.vectorHit = vectorHit.myVec3LocalPool.getVecFromPool(vectorHit.xCoord, vectorHit.yCoord, vectorHit.zCoord);
	}

	public MovingObjectPosition(Entity entity) {
		this.typeOfHit = MovingType.ENTITY;
		this.entityHit = entity;
		this.vectorHit = entity.getWorld().vectorPool.getVecFromPool(entity.getPosition().getX(), entity.getPosition().getY(), entity.getPosition().getZ());
	}

	public enum MovingType {
		TILE, ENTITY;
	}

}
