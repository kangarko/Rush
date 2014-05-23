package net.rush.model.block;

import java.util.Random;

import net.rush.model.Block;
import net.rush.model.Item;
import net.rush.util.RushException;
import net.rush.world.World;

public class BlockCrops extends BlockFlower {

	public BlockCrops(int id) {
		super(id);
		setTickRandomly(true);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		int ground = world.getTypeId(x, y, z);
		return ground == Block.SOIL.id;
	}

	@Override
	public void dropBlockWithChance(World world, int x, int y, int z, int damage, float chance, int bonus) {
		// min 0 wheat, max 3
		int quantity = world.rand.nextInt(4);

		if(world.getBlockData(x, y, z) > 2)
			world.dropItem(x, y, z, idDropped(), 1, 0);
			
		if(world.getBlockData(x, y, z) < 7)
			return;

		for (int i = 0; i < quantity; ++i)
			if (world.rand.nextFloat() <= .50)
				world.dropItem(x, y, z, idDropped(), 1, 0);

		for (int i = 0; i < quantity; ++i)
			if (world.rand.nextFloat() <= .50)
				world.dropItem(x, y, z, Item.WHEAT.id, 1, 0);
	}

	@Override
	public void tick(World world, int x, int y, int z, Random rand) {
		if(world.getTypeId(x, y, z) != this.id)
			throw new RushException("Illegal block found instead of " + this.getName() + " when ticking! ID:" + world.getTypeId(x, y, z));

		if(world.getTypeId(x, y - 1, z) == Block.SOIL.id && world.getBlockData(x, y - 1, z) == 1) {
			int stage = world.getBlockData(x, y, z);

			System.out.println("Ticking crops, its stage is: " + stage);

			if(stage < 7)
				world.setTypeAndData(x, y, z, id, stage + 1, true);
		} else
			System.out.println("Crops wont grow because soil is not watered!");
	}

	@Override
	public boolean grow(World world, int x, int y, int z, boolean bonemeal) {
		if (bonemeal)
			world.setTypeAndData(x, y, z, id, 7, true);
		else {
			int data = world.getBlockData(x, y, z);
			if(data < 7)
				world.setTypeAndData(x, y, z, id, data++, true);
		}
		return true;
	}

	@Override
	public int idDropped() {
		return Item.SEEDS.id;
	}
}
