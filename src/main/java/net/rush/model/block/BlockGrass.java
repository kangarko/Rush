package net.rush.model.block;

import java.util.Random;

import net.rush.model.Block;
import net.rush.model.Material;
import net.rush.world.World;

public class BlockGrass extends Block {

	public BlockGrass(int id) {
		super(id, Material.GRASS);
	}

	@Override
	public void tick(World world, int x, int y, int z, Random rand) {
		if (world.getBlockLightValue(x, y + 1, z) < 4 /*&& Block.lightOpacity[world.getTypeId(x, y + 1, z)] > 2*/) {
			world.setTypeId(x, y, z, Block.DIRT.id, true);
			//System.out.println("dirt @" + x + ", " + y + ", " + z);
		} /*else if (world.getBlockLightValue(x, y + 1, z) >= 9) {
			for (int i = 0; i < 4; ++i) {
				
				int up1 = x + rand.nextInt(3) - 1;
				int up2 = y + rand.nextInt(5) - 3;
				int up3 = z + rand.nextInt(3) - 1;
				int up = world.getTypeId(up1, up2 + 1, up3);

				if (world.getTypeId(up1, up2, up3) == Block.DIRT.blockID && world.getBlockLightValue(up1, up2 + 1, up3) >= 4 && Block.lightOpacity[up] <= 2) {
					world.setTypeId(up1, up2, up3, this.blockID);
				}
			}
		}*/
		//world.setTypeId(x, y, z, Block.DIRT.id);
	}

	@Override
	public int idDropped() {
		return Block.DIRT.id;
	}
}
