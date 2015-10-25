package net.rush.world.decorator;

import imported.MathHelper;
import net.rush.block.Block;

public class MapGenCaves extends MapGenBase {

	protected void generateCave(int chunkX, int chunkZ, byte[] blockArray, double x, double y, double z) {
		this.generateCave(chunkX, chunkZ, blockArray, x, y, z, 1.0F + rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
	}

	protected void generateCave(int chunkX, int chunkZ, byte[] blockArray, double x, double y, double z, float f, float f1, float f2, int k, int l, double d3) {
		double xOffset = chunkX * 16 + 8;
		double zOffset = chunkZ * 16 + 8;
		float f3 = 0.0F;
		float f4 = 0.0F;
		rand.setSeed(rand.nextLong());

		if (l <= 0) {
			int i1 = radius * 16 - 16;

			l = i1 - rand.nextInt(i1 / 4);
		}

		boolean flag = false;

		if (k == -1) {
			k = l / 2;
			flag = true;
		}

		int j1 = rand.nextInt(l / 2) + l / 4;

		for (boolean flag1 = rand.nextInt(6) == 0; k < l; ++k) {
			double d6 = 1.5D + MathHelper.floor_double(k * 3.1415927F / l) * f * 1.0F;
			double d7 = d6 * d3;
			float f5 = MathHelper.ceiling_float_int(f2);
			float f6 = MathHelper.floor_float(f2);

			x += MathHelper.ceiling_float_int(f1) * f5;
			y += f6;
			z += MathHelper.floor_float(f1) * f5;
			if (flag1)
				f2 *= 0.92F;
			else
				f2 *= 0.7F;

			f2 += f4 * 0.1F;
			f1 += f3 * 0.1F;
			f4 *= 0.9F;
			f3 *= 0.75F;
			f4 += (rand.nextFloat() - rand.nextFloat()) * rand.nextFloat() * 2.0F;
			f3 += (rand.nextFloat() - rand.nextFloat()) * rand.nextFloat() * 4.0F;
			if (!flag && k == j1 && f > 1.0F) {
				this.generateCave(chunkX, chunkZ, blockArray, x, y, z, rand.nextFloat() * 0.5F + 0.5F, f1 - 1.5707964F, f2 / 3.0F, k, l, 1.0D);
				this.generateCave(chunkX, chunkZ, blockArray, x, y, z, rand.nextFloat() * 0.5F + 0.5F, f1 + 1.5707964F, f2 / 3.0F, k, l, 1.0D);
				return;
			}

			if (flag || rand.nextInt(4) != 0) {
				double d8 = x - xOffset;
				double d9 = z - zOffset;
				double d10 = l - k;
				double d11 = f + 2.0F + 16.0F;

				if (d8 * d8 + d9 * d9 - d10 * d10 > d11 * d11)
					return;

				if (x >= xOffset - 16.0D - d6 * 2.0D && z >= zOffset - 16.0D - d6 * 2.0D && x <= xOffset + 16.0D + d6 * 2.0D && z <= zOffset + 16.0D + d6 * 2.0D) {
					int k1 = MathHelper.ceiling_double_int(x - d6) - chunkX * 16 - 1;
					int l1 = MathHelper.ceiling_double_int(x + d6) - chunkX * 16 + 1;
					int i2 = MathHelper.ceiling_double_int(y - d7) - 1;
					int j2 = MathHelper.ceiling_double_int(y + d7) + 1;
					int k2 = MathHelper.ceiling_double_int(z - d6) - chunkZ * 16 - 1;
					int l2 = MathHelper.ceiling_double_int(z + d6) - chunkZ * 16 + 1;

					if (k1 < 0)
						k1 = 0;

					if (l1 > 16)
						l1 = 16;

					if (i2 < 1)
						i2 = 1;

					if (j2 > 120)
						j2 = 120;

					if (k2 < 0)
						k2 = 0;

					if (l2 > 16)
						l2 = 16;

					boolean flag2 = false;

					int index1;
					int j3;

					for (j3 = k1; !flag2 && j3 < l1; ++j3)
						for (int k3 = k2; !flag2 && k3 < l2; ++k3)
							for (int l3 = j2 + 1; !flag2 && l3 >= i2 - 1; --l3) {
								index1 = (j3 * 16 + k3) * 128 + l3;
								if (l3 >= 0 && l3 < 128) {
									if (blockArray[index1] == Block.WATER.id || blockArray[index1] == Block.STATIONARY_WATER.id)
										flag2 = true;

									if (l3 != i2 - 1 && j3 != k1 && j3 != l1 - 1 && k3 != k2 && k3 != l2 - 1)
										l3 = i2;
								}
							}

					if (!flag2) {
						for (j3 = k1; j3 < l1; ++j3) {
							double d12 = (j3 + chunkX * 16 + 0.5D - x) / d6;

							for (index1 = k2; index1 < l2; ++index1) {
								double d13 = (index1 + chunkZ * 16 + 0.5D - z) / d6;
								int index2 = (j3 * 16 + index1) * 128 + j2;
								boolean flag3 = false;

								for (int j4 = j2 - 1; j4 >= i2; --j4) {
									double d14 = (j4 + 0.5D - y) / d7;

									if (d14 > -0.7D && d12 * d12 + d14 * d14 + d13 * d13 < 1.0D) {
										byte b0 = blockArray[index2];

										if (b0 == Block.GRASS.id)
											flag3 = true;

										if (b0 == Block.STONE.id || b0 == Block.DIRT.id || b0 == Block.GRASS.id)
											if (j4 < 10)
												blockArray[index2] = (byte) Block.LAVA.id;
											else {
												blockArray[index2] = 0;
												if (flag3 && blockArray[index2 - 1] == Block.DIRT.id)
													blockArray[index2 - 1] = (byte) Block.GRASS.id;
											}
									}

									--index2;
								}
							}
						}

						if (flag)
							break;
					}
				}
			}
		}
	}

	@Override
	protected void decorate(long seed, int xPos, int yPos, int chunkX, int chunkZ, byte[] blockArray) {
		int tries = rand.nextInt(rand.nextInt(rand.nextInt(40) + 1) + 1);

		if (rand.nextInt(15) != 0)
			tries = 0;

		for (int i = 0; i < tries; ++i) {
			double x = xPos * 16 + rand.nextInt(16);
			double y = rand.nextInt(rand.nextInt(120) + 8);
			double z = yPos * 16 + rand.nextInt(16);
			int tries2 = 1;

			if (rand.nextInt(4) == 0) {
				this.generateCave(chunkX, chunkZ, blockArray, x, y, z);
				tries2 += rand.nextInt(4);
			}

			for (int i2 = 0; i2 < tries2; ++i2) {
				float f = rand.nextFloat() * 3.1415927F * 2.0F;
				float f1 = (rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float f2 = rand.nextFloat() * 2.0F + rand.nextFloat();

				this.generateCave(chunkX, chunkZ, blockArray, x, y, z, f2, f, f1, 0, 0, 1.0D);
			}
		}
	}
}
