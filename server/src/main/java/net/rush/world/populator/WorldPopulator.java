package net.rush.world.populator;

import java.util.Random;

import net.rush.world.WorldGenerator.WorldPopulate;

public abstract class WorldPopulator {
	
	protected WorldPopulator() {
	}

	public abstract boolean generate(WorldPopulate world, Random rand, int x, int y, int z);

	public void setTreeGeneratorScale(double x, double y, double z) {
	}
}
