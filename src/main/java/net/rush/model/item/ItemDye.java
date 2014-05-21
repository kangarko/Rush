package net.rush.model.item;

import org.bukkit.GameMode;

import net.rush.model.Block;
import net.rush.model.Item;
import net.rush.model.ItemStack;
import net.rush.model.Player;
import net.rush.util.MathHelper;
import net.rush.world.World;

public class ItemDye extends Item {

	public static final String colorNames[] = { "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", 
		"yellow", "lightBlue", "magenta", "orange", "white" };
	public static final int colorCodes[] = { 0x1e1b1b, 0xb3312c, 0x3b511a, 0x51301a, 0x253192, 0x7b2fbe, 0x287697, 0x287697, 0x434343, 0xd88198,
		0x41cd34, 0xdecf2a, 0x6689d3, 0xc354cd, 0xeb8844, 0xf0f0f0 };

	public ItemDye(int id) {
		super(id);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	public String getBlockName(ItemStack item) {
		int color = MathHelper.getValueInBounds(item.getDamage(), 0, 15);
		return super.getName() + "." + colorNames[color];
	}

	@Override
	public boolean onItemUse(ItemStack item, Player player, World world, int x, int y, int z, int direction, float xOffset, float yOffset, float zOffset) {
		
		if (item.getDamage() == 15) {
			
			boolean success = false;
			
			int id = world.getTypeId(x, y, z);
			
			if (id == Block.SAPLING.id //|| id == Block.BROWN_MUSHROOM.id || id == Block.RED_MUSHROOM.id 
					/*|| id == Block.MELON_STEM.id || id == Block.PUMPKIN_STEM.id*/ || id == Block.CROPS.id) {
				success = Block.byId[id].grow(world, x, y, z, true);
			}
			if (id == Block.GRASS.id) {

				mainLoop: for (int tries = 0; tries < 128; tries++) {

					int posX = x;
					int posY = y + 1;
					int posZ = z;

					// randomize location
					for (int i = 0; i < tries / 16; i++) {
						posX += rand.nextInt(3) - 1;
						posY += (rand.nextInt(3) - 1) * rand.nextInt(3) / 2;
						posZ += rand.nextInt(3) - 1;
						
						if (world.getTypeId(posX, posY - 1, posZ) != Block.GRASS.id)
							continue mainLoop;
					}

					if (world.getTypeId(posX, posY, posZ) != 0)
						continue;

					// grass
					if (rand.nextInt(10) != 0) {
						world.setTypeAndData(posX, posY, posZ, Block.TALL_GRASS.id, 1, true);
						continue;
					}

					// dandelion
					if (rand.nextInt(3) != 0)
						world.setTypeId(posX, posY, posZ, Block.YELLOW_FLOWER.id, true);

					// rose
					else
						world.setTypeId(posX, posY, posZ, Block.RED_ROSE.id, true);
				}
				success = true;
			}
			
			if(success)
				if(player.getGamemode() != GameMode.CREATIVE)
					item.count--;
			
			return success;
		}
		return false;
	}

}
