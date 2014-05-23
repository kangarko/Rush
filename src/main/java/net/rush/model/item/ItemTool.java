package net.rush.model.item;

import net.rush.model.Item;
import net.rush.util.enums.EnumToolMaterial;

public class ItemTool extends Item {

	public ItemTool(int id, EnumToolMaterial toolMaterial) {
		super(id);
		setMaxStackSize(1);
		setMaxDamage(toolMaterial.getMaxUses());
	}
}
