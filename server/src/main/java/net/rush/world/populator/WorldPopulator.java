package net.rush.world.populator;

import java.util.Random;

import net.rush.world.World;

public abstract class WorldPopulator {
	
	protected WorldPopulator() {
	}

	public abstract boolean generate(World world, Random rand, int x, int y, int z);

	public void setTreeGeneratorScale(double x, double y, double z) {
	}
}
