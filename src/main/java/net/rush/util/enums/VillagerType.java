package net.rush.util.enums;

public enum VillagerType {

	FARMER(0),
	LIBRARIAN(1),
	PRIEST(2),
	BLACKSMITH(3),
	BUTCHER(4);
	
	private int type;
	
	VillagerType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}
