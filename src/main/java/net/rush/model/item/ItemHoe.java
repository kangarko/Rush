package net.rush.model.item;

import net.rush.model.Block;
import net.rush.model.ItemStack;
import net.rush.model.Player;
import net.rush.util.enums.EnumToolMaterial;
import net.rush.world.World;

public class ItemHoe extends ItemTool {

	public ItemHoe(int id, EnumToolMaterial toolMaterial) {
		super(id, toolMaterial);
	}

	@Override
	public boolean onItemUse(ItemStack item, Player player, World world, int x, int y, int z, int direction, float xOffset, float yOffset, float zOffset) {		
		int groundBlock = world.getTypeId(x, y, z);
		int upperBlock = world.getTypeId(x, y + 1, z);

		if (direction != 0 && upperBlock == 0 && (groundBlock == Block.GRASS.id || groundBlock == Block.DIRT.id)) {
			Block soil = Block.SOIL;

			world.setTypeId(x, y, z, soil.id, true);
			world.playSound(x + 0.5F, y + 0.5F, z + 0.5F, soil.sound.getStepSound(), (soil.sound.getVolume() + 1.0F) / 2.0F, soil.sound.getPitch() * 0.8F);
			
			return true;
		}
		
		return false;
	}
}
