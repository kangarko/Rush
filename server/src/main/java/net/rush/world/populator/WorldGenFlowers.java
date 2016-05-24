package net.rush.world.populator;

import java.util.Random;

import lombok.RequiredArgsConstructor;
import net.rush.world.World;

@RequiredArgsConstructor
public class WorldGenFlowers extends WorldPopulator {

	private final int flowerId, flowerData;

	public WorldGenFlowers(int flowerId) {
		this(flowerId, 0);
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		/*for (int tries = 0; tries < 64; ++tries) {
			int xPos = x + random.nextInt(8) - random.nextInt(8);
			int yPos = y + random.nextInt(4) - random.nextInt(4);
			int zPos = z + random.nextInt(8) - random.nextInt(8);
			
			// rush start
			if (yPos < 0)
				return true;
			// rush end
			
			if (world.getType(xPos, yPos, zPos) == 0 && Block.byId(flowerId).canPlaceAt(world, xPos, yPos - 1, zPos))
				world.setTypeAndData(xPos, yPos, zPos, flowerId, flowerData);
		}*/

		return true;
	}
}
