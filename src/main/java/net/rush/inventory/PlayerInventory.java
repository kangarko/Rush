package net.rush.inventory;

import net.rush.model.Item;

public class PlayerInventory extends Inventory {

    // 36 = 4 rows of 9
    // ... + 4 = armor, completed inventory
    public static final int PLAYER_INVENTORY_SIZE = 40;
    public static final int HELMET_SLOT = 36;
    public static final int CHESTPLATE_SLOT = 37;
    public static final int LEGGINGS_SLOT = 38;
    public static final int BOOTS_SLOT = 39;

    public static final SlotConverter slotConverter = new PlayerInventorySlotConverter();

    private int heldItemSlot = 0;

    public PlayerInventory() {
        // all player inventories are ID 0
        super(0, PLAYER_INVENTORY_SIZE);
    }

    public Item[] getArmorContents() {
        Item[] armor = new Item[4];

        armor[0] = this.getHelmet();
        armor[1] = this.getChestplate();
        armor[2] = this.getLeggings();
        armor[3] = this.getBoots();

        return armor;
    }

    public Item getHelmet() {
        return this.getItem(HELMET_SLOT);
    }

    public Item getChestplate() {
        return this.getItem(CHESTPLATE_SLOT);
    }

    public Item getLeggings() {
        return this.getItem(LEGGINGS_SLOT);
    }

    public Item getBoots() {
        return this.getItem(BOOTS_SLOT);
    }

    public void setArmorContents(Item[] newArmor) {
        if (newArmor.length != 4)
            throw new IllegalArgumentException();

        this.setHelmet(newArmor[0]);
        this.setChestplate(newArmor[1]);
        this.setLeggings(newArmor[2]);
        this.setBoots(newArmor[3]);
    }

    public void setHelmet(Item paramItem) {
        this.setItem(HELMET_SLOT, paramItem);
    }

    public void setChestplate(Item paramItem) {
        this.setItem(CHESTPLATE_SLOT, paramItem);
    }

    public void setLeggings(Item paramItem) {
        this.setItem(LEGGINGS_SLOT, paramItem);
    }

    public void setBoots(Item paramItem) {
        this.setItem(BOOTS_SLOT, paramItem);
    }

    public Item getItemInHand() {
        return this.getItem(heldItemSlot);
    }

    public void setItemInHand(Item paramItem) {
        this.setItem(heldItemSlot, paramItem);
    }

    public int getHeldItemSlot() {
        return heldItemSlot;
    }
    
    public void setHeldItemSlot(int heldItemSlot) {
    	this.heldItemSlot = heldItemSlot;
    }

    public SlotConverter getSlotConverter() {
        return slotConverter;
    }

    public String getName() {
        return "Player Inventory";
    }

	public int getMaxStackSize() {
		return 64;
	}
	
	public static class PlayerInventorySlotConverter implements SlotConverter {

	    private int mappings[] = { 36, 37, 38, 39, 40, 41, 42, 43,
	            44, // quickbar
	            9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
	            31, 32, 33, 34, 35, 5, 6, 7, 8 // armor
	    };

	    public int netToLocal(int net) {
	        for (int i = 0; i < mappings.length; i++) {
	            if (mappings[i] == net)
	                return i;
	        }
	        return -1;
	    }

	    public int localToNet(int local) {
	        if (local > mappings.length)
	            return -1;

	        return mappings[local];
	    }
	}

}
