package net.rush.model.block;

import net.rush.model.Block;
import net.rush.model.Material;
import net.rush.world.World;


public class BlockWool extends Block {

	public BlockWool(int id) {
		super(id, Material.CLOTH);
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int direction, float xOffset, float yOffset, float zOffset, int metadata) {
		return metadata & 15;
	}
	
	@Override
	public int damageDropped(int damage) {
		return damage & 15;
	}
}
