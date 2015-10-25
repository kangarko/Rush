package net.rush.world.populator;

import java.util.Random;

import imported.MathHelper;
import net.rush.block.Block;
import net.rush.world.World;

public class WorldGenMinable extends WorldPopulator {

	private int blockId;
	private int density;

	public WorldGenMinable(int blockId, int density) {
		this.blockId = blockId;
		this.density = density;
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		float probability = random.nextFloat() * 3.1415927F;
		double xVein1 = x + 8 + MathHelper.floor_double(probability) * density / 8.0F;
		double xVein2 = x + 8 - MathHelper.floor_double(probability) * density / 8.0F;
		double zVein1 = z + 8 + MathHelper.ceiling_float_int(probability) * density / 8.0F;
		double zVein2 = z + 8 - MathHelper.ceiling_float_int(probability) * density / 8.0F;
		double yVein1 = y + random.nextInt(3) + 2;
		double yVein2 = y + random.nextInt(3) + 2;

		for (int tries = 0; tries <= density; ++tries) {
			double xVeinWidth = xVein1 + (xVein2 - xVein1) * tries / density;
			double yVeinWidth = yVein1 + (yVein2 - yVein1) * tries / density;
			double zVeinWidth = zVein1 + (zVein2 - zVein1) * tries / density;
			double veinSize = random.nextDouble() * density / 16.0D;
			double veinSizeX = (MathHelper.floor_double(tries * 3.1415927F / density) + 1.0F) * veinSize + 1.0D;
			double veinSizeZ = (MathHelper.floor_double(tries * 3.1415927F / density) + 1.0F) * veinSize + 1.0D;

			for (int posX = (int) (xVeinWidth - veinSizeX / 2.0D); posX <= (int) (xVeinWidth + veinSizeX / 2.0D); ++posX)
				for (int posY = (int) (yVeinWidth - veinSizeZ / 2.0D); posY <= (int) (yVeinWidth + veinSizeZ / 2.0D); ++posY)
					for (int posZ = (int) (zVeinWidth - veinSizeX / 2.0D); posZ <= (int) (zVeinWidth + veinSizeX / 2.0D); ++posZ) {
						double sizeX = (posX + 0.5D - xVeinWidth) / (veinSizeX / 2.0D);
						double sizeY = (posY + 0.5D - yVeinWidth) / (veinSizeZ / 2.0D);
						double sizeZ = (posZ + 0.5D - zVeinWidth) / (veinSizeX / 2.0D);

						// rush start
						if (posY < 0) 
							posY = 0;
						// rush end
						
						if (sizeX * sizeX + sizeY * sizeY + sizeZ * sizeZ < 1.0D && world.getType(posX, posY, posZ) == Block.STONE.id)
							world.setType(posX, posY, posZ, blockId);
					}
		}

		return true;
	}
}
