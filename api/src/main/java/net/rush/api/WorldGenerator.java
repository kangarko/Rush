package net.rush.api;

import java.util.Random;

import net.rush.api.world.Chunk;

public interface WorldGenerator {

	public Random rand = new Random();
	
	public abstract Chunk generate(int x, int z);
}
