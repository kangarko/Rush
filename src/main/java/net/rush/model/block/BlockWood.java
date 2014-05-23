package net.rush.model.block;

import net.rush.model.Block;
import net.rush.model.Material;


public class BlockWood extends Block {

	public static final String[] woodType = new String[] { "oak", "spruce", "birch", "jungle" };

	public BlockWood(int id) {
		super(id, Material.WOOD);
	}

	@Override
	public int damageDropped(int damage) {
		return damage;
	}
}
