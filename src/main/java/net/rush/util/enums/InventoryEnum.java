package net.rush.util.enums;

public enum InventoryEnum {
	
	CHEST(0),
	WORKBENCH(1),
	FURNACE(2),
	DISPENSER(3),
	ENCHANTING(4),
	BREWING(5),
	VILLAGER_TRADE(6),
	BEACON(7),
	ANVIL(8),
	HOPPER(9),
	DROPPER(10),
	HORSE(11);
	
	public int id;

	InventoryEnum(int id) {
		this.id = id;
	}
}
