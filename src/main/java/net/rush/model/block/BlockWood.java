package net.rush.model.block;

import net.rush.model.Block;
import net.rush.model.Material;
import net.rush.world.World;


public class BlockWood extends Block {

	public static final String[] woodType = new String[] { "oak", "spruce", "birch", "jungle" };

	public BlockWood(int id) {
		super(id, Material.WOOD);
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int direction, float xOffset, float yOffset, float zOffset, int metadata) {
		return metadata & 3;
	}
	
	@Override
	public int damageDropped(int damage) {
		return damage & 3;
	}
}
