package net.rush.api.world;

/**
 * Represents a single chunk in the world.
 */
public interface Chunk {

	/**
	 * @return the x coordinates of this chunk
	 */
	public int getX();
	
	/**
	 * @return the z coordinates of this chunk
	 */
	public int getZ();
	
	/**
	 * @return block ID at the location within this chunk.
	 */
	public int getType(int x, int y, int z);
	
	/**
	 * Sets block ID at the location within this chunk.
	 */
	public void setType(int x, int y, int z, int type);

	/**
	 * @return metadata of the block at the location within this chunk.
	 */
	public int getMetadata(int x, int y, int z);
	
	/**
	 * Sets the metadata of the block at the location within this chunk.
	 */
	public void setMetadata(int x, int y, int z, int metadata);

	/**
	 * Sets block ID and metadata at the location within this chunk.
	 */
	public void setTypeAndData(int x, int y, int z, int type, int data);
}
