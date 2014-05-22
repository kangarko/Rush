package net.rush.model.block;

import net.rush.model.Block;
import net.rush.model.Material;
import net.rush.model.Player;
import net.rush.util.enums.InventoryEnum;
import net.rush.world.World;

public class BlockCraftingTable extends Block {
	
	public BlockCraftingTable(int id) {
        super(id, Material.STONE);
    }

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, Player player, int direction, float xOffset, float yOffset, float zOffset) {
		player.openInventory(InventoryEnum.WORKBENCH);
		return true;
	}
}
