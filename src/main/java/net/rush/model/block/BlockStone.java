package net.rush.model.block;

import java.util.Random;

import net.rush.model.Block;
import net.rush.model.Material;

public class BlockStone extends Block {
	
	public BlockStone(int id) {
        super(id, Material.STONE);
       //this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public int idDropped(int par1, Random par2Random, int par3) {
        return Block.COBBLESTONE.blockID;
    }
}
