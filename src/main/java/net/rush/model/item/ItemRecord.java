package net.rush.model.item;

import java.util.HashMap;
import java.util.Map;

import net.rush.model.Item;
import net.rush.model.ItemStack;
import net.rush.model.Player;
import net.rush.world.World;
public class ItemRecord extends Item {
	
	private final Map<String, ItemRecord> records = new HashMap<String, ItemRecord>();

	public final String recordName;

	public ItemRecord(int id, String recordName) {
		super(id);
		this.recordName = recordName;
		maxStackSize = 1;
		records.put(recordName, this);
	}

	@Override
	public boolean onItemUse(ItemStack item, Player player, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		/*if (world.getTypeId(x, y, z) == Block.JUKEBOX.id && world.getBlockData(x, y, z) == 0) {
			((BlockJukeBox) Block.JUKEBOX).insertRecord(world, x, y, z, item);
			world.playAuxSFXAtEntity((EntityPlayer) null, 1005, x, y, z, id);
			--item.count;
			return true;			
		}*/
		return false;
	}
}
