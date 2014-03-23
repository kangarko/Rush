package net.rush.inventory;

import org.bukkit.inventory.ItemStack;

public interface InventoryViewer {
    void onSlotSet(Inventory mainInventory, int index, ItemStack itemStack);
}
