package net.rush.model.block;

import net.rush.model.Material;

public class BlockSapling extends RotatableBlock {

	public static final String[] saplingTypes = new String[] { "oak", "spruce", "birch", "jungle" };

	public BlockSapling(int id) {
		super(id, Material.PLANT);
	}
}
