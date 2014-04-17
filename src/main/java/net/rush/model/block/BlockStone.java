package net.rush.model.block;

import net.rush.model.Block;
import net.rush.model.Material;

public class BlockStone extends Block {
	
	public BlockStone(int id) {
        super(id, Material.STONE);
    }

	@Override
    public int idDropped() {
        return Block.COBBLESTONE.id;
    }
}
