package net.rush.world.populator;

import java.util.Random;

import imported.MathHelper;
import net.rush.block.Block;
import net.rush.world.WorldGenerator.WorldPopulate;

public class WorldGenClay extends WorldPopulator {

	private int clayId;
	private int density;

	public WorldGenClay(int density) {
		clayId = Block.CLAY_BLOCK.id;
		this.density = density;
	}

	@Override
	public boolean generate(WorldPopulate world, Random random, int x, int y, int z) {
		//if (world.getMaterial(x, y, z) != Material.WATER)
		// TODO	return false;

		if (true)
			return true;
		// TODO
		
		float f = random.nextFloat() * 3.1415927F;
		double d0 = x + 8 + MathHelper.floor_float(f) * density / 8.0F;
		double d1 = x + 8 - MathHelper.floor_float(f) * density / 8.0F;
		double d2 = z + 8 + MathHelper.ceiling_float_int(f) * density / 8.0F;
		double d3 = z + 8 - MathHelper.ceiling_float_int(f) * density / 8.0F;
		double d4 = y + random.nextInt(3) + 2;
		double d5 = y + random.nextInt(3) + 2;

		for (int l = 0; l <= density; ++l) {
			double d6 = d0 + (d1 - d0) * l / density;
			double d7 = d4 + (d5 - d4) * l / density;
			double d8 = d2 + (d3 - d2) * l / density;
			double d9 = random.nextDouble() * density / 16.0D;
			double d10 = (MathHelper.floor_double(l * 3.1415927F / density) + 1.0F) * d9 + 1.0D;
			double d11 = (MathHelper.floor_double(l * 3.1415927F / density) + 1.0F) * d9 + 1.0D;

			for (int xPos = (int) (d6 - d10 / 2.0D); xPos <= (int) (d6 + d10 / 2.0D); ++xPos)
				for (int yPos = (int) (d7 - d11 / 2.0D); yPos <= (int) (d7 + d11 / 2.0D); ++yPos)
					for (int zPos = (int) (d8 - d10 / 2.0D); zPos <= (int) (d8 + d10 / 2.0D); ++zPos) {
						double d12 = (xPos + 0.5D - d6) / (d10 / 2.0D);
						double d13 = (yPos + 0.5D - d7) / (d11 / 2.0D);
						double d14 = (zPos + 0.5D - d8) / (d10 / 2.0D);

						if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
							int blockId = world.getType(xPos, yPos, zPos);

							if (blockId == Block.SAND.id)
								world.setType(xPos, yPos, zPos, clayId);
						}
					}
		}

		return true;
	}
}
