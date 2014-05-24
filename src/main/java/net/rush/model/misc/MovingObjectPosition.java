package net.rush.model.misc;

import net.rush.model.Entity;

public class MovingObjectPosition {
	/** What type of ray trace hit was this? 0 = block, 1 = entity */
	public HitType hittype;

	/** x coordinate of the block ray traced against */
	public int x;

	/** y coordinate of the block ray traced against */
	public int y;

	/** z coordinate of the block ray traced against */
	public int z;

	/**
	 * Which side was hit. If its -1 then it went the full length of the ray trace. Bottom = 0, Top = 1, East = 2, West
	 * = 3, North = 4, South = 5.
	 */
	public int sideHit;

	/** The vector position of the hit */
	public Vec3 vectorHit;

	/** The hit entity */
	public Entity entityHit;

	public MovingObjectPosition(int x, int y, int z, int sideHit, Vec3 vectorHit) {
		this.hittype = HitType.TILE;
		this.x = x;
		this.y = y;
		this.z = z;
		this.sideHit = sideHit;
		this.vectorHit = vectorHit.myVec3LocalPool.getVecFromPool(vectorHit.xCoord, vectorHit.yCoord, vectorHit.zCoord);
	}

	public MovingObjectPosition(Entity entity) {
		this.hittype = HitType.ENTITY;
		this.entityHit = entity;
		this.vectorHit = entity.getWorld().vectorPool.getVecFromPool(entity.getPosition().x, entity.getPosition().y, entity.getPosition().z);
	}

	public enum HitType {
		TILE, ENTITY;
	}

}
