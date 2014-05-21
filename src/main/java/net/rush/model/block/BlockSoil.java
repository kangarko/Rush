package net.rush.model.block;

import net.rush.model.Block;
import net.rush.model.Material;

public class BlockSoil extends Block {

	public BlockSoil(int id) {
		super(id, Material.DIRT);
	}

	@Override
	public int idDropped() {
		return Block.DIRT.id;
	}
}
