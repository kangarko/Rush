package net.rush.model.block;

import net.rush.model.Material;
import net.rush.world.World;

public class BlockLog extends RotatableBlock {

	public static final String[] logTypes = new String[] { "oak", "spruce", "birch", "jungle" };

	public BlockLog(int id) {
		super(id, Material.WOOD);
	}

	/**
	 * Called on server worlds only when the block has been replaced by a different block ID, or the same block with a
	 * different metadata value, but before the new metadata value is set. Args: World, x, y, z, old block ID, old
	 * metadata
	 */
	@Override
	public void breakBlock(World world, int x, int y, int z, int oldId, int oldData) {
		/*byte four = 4;
		int var8 = four + 1;

		if (world.checkChunksExist(x - var8, y - var8, z - var8, x + var8, y + var8, z + var8)) {
			for (int var9 = -four; var9 <= four; ++var9) {
				for (int var10 = -four; var10 <= four; ++var10) {
					for (int var11 = -four; var11 <= four; ++var11) {
						int var12 = world.getBlockId(x + var9, y + var10, z + var11);

						if (var12 == Block.LEAVES.blockID) {
							int var13 = world.getBlockMetadata(x + var9, y + var10, z + var11);

							if ((var13 & 8) == 0) {
								world.setBlockMetadata(x + var9, y + var10, z + var11, var13 | 8, 4);
							}
						}
					}
				}
			}
		}*/
	}

	/**
	 * returns a number between 0 and 3
	 */
	public static int limitToValidMetadata(int blockRotation) {
		return blockRotation & 3;
	}
}
