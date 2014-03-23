package net.rush.util.enums;

public enum Dimension {
	NETHER(-1),
	OVERWORLD(0),
	NORMAL(0),
	END(1);
	
	int value;
	
	Dimension(int value) {
		this.value = value;
	}
	
	public byte getValue() {
		return (byte)value;
	}
}
