package net.rush.util.enums;

public enum ColorEnum {

	WHITE(0),
	ORANGE(1),
	MAGENTA(2),
	LIGHT_BLUE(3),
	YELLOW(4),
	LIME(5),
	PINK(6),
	GRAY(7),
	SILVER(8),
	CYAN(9),
	PURPLE(10),
	BLUE(11),
	BROWN(12),
	GREEN(13),
	RED(14),
	BLACK(15);
	
	private int color;
	
	ColorEnum(int color) {
		this.color = color;
	}
	
	public int getColor() {
		return color;
	}
	
	/*public static final byte WHITE = 0;
	public static final byte ORANGE = 1;
	public static final byte MAGENTA = 2;
	public static final byte LIGHT_BLUE = 3;
	public static final byte YELLOW = 4;
	public static final byte LIME = 5;
	public static final byte PINK = 6;
	public static final byte GRAY = 7;
	public static final byte SILVER = 8;
	public static final byte CYAN = 9;
	public static final byte PURPLE = 10;
	public static final byte BLUE = 11;
	public static final byte BROWN = 12;
	public static final byte GREEN = 13;
	public static final byte RED = 14;
	public static final byte BLACK = 15;*/
}
