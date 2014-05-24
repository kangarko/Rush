package net.rush.model.block;

import java.util.Random;

import net.rush.model.Block;
import net.rush.model.Material;
import net.rush.util.RushException;
import net.rush.util.StringUtils;
import net.rush.world.World;

public class BlockSoil extends Block {

	public BlockSoil(int id) {
		super(id, Material.DIRT);
		setTickRandomly(true);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockId) {
		Block block = Block.byId[world.getTypeId(x, y + 1, z)];
		
		if(block == null)
			return;

		Material mat = block.material;
		if(block.id != 0 && mat.isSolid()) {
			world.setTypeWithNotify(x, y, z, Block.DIRT.id, true);
			System.out.println("setting soil to dirt @ " + StringUtils.serializeLoc(x, y, z) + " as the block is: " + block.getName());
		}
	}
	
	@Override
	public void tick(World world, int x, int y, int z, Random rand) {
		if(world.getTypeId(x, y, z) != this.id)
			throw new RushException("Illegal block found instead of " + this.getName() + " when ticking! ID:" + world.getTypeId(x, y, z));
		
		boolean watered = world.getBlockData(x, y, z) == 1;
		
		if(!watered)
			world.setTypeAndData(x, y, z, id, 1, true);
	}

	@Override
	public int idDropped() {
		return Block.DIRT.id;
	}
}
