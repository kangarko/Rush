package net.rush.world;

import net.rush.block.Block;
import net.rush.world.chunk.Chunk;

public interface WorldGenerator {

	public Chunk generate(int chunkX, int chunkZ);

	public void populate(WorldPopulate world, int chunkX, int chunkZ);

	public static class WorldPopulate {

		private final Chunk chunk;
		private final World world;

		public WorldPopulate(World world, Chunk chunk) {
			this.world = world;
			this.chunk = chunk;
		}

		public int getType(int x, int y, int z) {			
			return world.getType(x, y, z);
		}

		public void setType(int x, int y, int z, int type) {			
			world.setType(x, y, z, type); //getChunk(x, z).setType(x & 15, y, z & 15, type); // Important: Method does not send block update packet.
		}

		public void setTypeAndData(int x, int y, int z, int type, int data) {
			world.setTypeAndData(x, y, z, type, data); //getChunk(x, z).setTypeAndData(x & 15, y, z & 15, type, data); // Important: Method does not send block update packet.
		}

		public int getData(int x, int y, int z) {
			return world.getMetadata(x, y, z);
		}

		public void setData(int x, int y, int z, int data) {
			world.setMetadata(x, y, z, data); //getChunk(x, z).setData(x & 15, y, z & 15, data); // Important: Method does not send block update packet.
		}

		public int getTerrainHeight(int x, int z) {
			return world.getTerrainHeight(x, z);
		}
		
		private Chunk getChunk(int x, int z) {
		x >>= 4;
		z >>= 4;

		if (chunk.getX() == x && chunk.getZ() == z)
			return chunk;
		
		return world.getChunkFromChunkCoords(x, z);
		}
		
		public boolean canPlaceBlockAt(Block block, int x, int y, int z) {
			return block.canPlaceAt(world, x, y, z);
		}
		
		public int getX() {
			return chunk.getX();
		}
		
		public int getZ() {
			return chunk.getZ();
		}
		
		public long getSeed() {
			return world.getSeed();
		}
	}
}
