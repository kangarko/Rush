package net.rush.util.enums;

public enum GameStateReason {

	INVALID_BED(0, false),
	RAIN_END(1, false),
	RAIN_BEGIN(2, false),
	
	/**
	 *  Additional info: 0 - Survival, 1 - Creative, 2 - Adventure.
	 */
	CHANGE_GAMEMODE(3, true),
	ENTER_CREDITS(4, false),
	
	/**
	 *  Additional info: 0 - Show welcome to demo screen, 101 - Tell movement controls, 102 - Tell jump control, 103 - Tell inventory control.
	 */
	DEMO_MESSAGES(5, true),
	ARROW_HIT(6, false),
	
	/**
	 * Additional info: The current darkness value. 1 = Dark, 0 = Bright,
	 * Setting the value higher causes the game to change color and freeze.
	 * Set 0 to reset.
	 */
	FADE_VALUE(7, true),
	
	/**
	 *  additional info: Time in ticks for the sky to fade 
	 *  <hr>
	 *  My research however discovered that this also starts raining and the more value you set the lower FPS client get,
	 *  setting the other value to 10 will lag client, setting it to 40 will crash it.
	 *  Set 0 to reset.
	 */
	FADE_TIME(8, true);
	
	int value;
	boolean moreInfo;
	
	GameStateReason(int value, boolean moreInfo) {
		this.value = value;
		this.moreInfo = moreInfo;
	}
	
	public int getValue() {
		return value;
	}
	
	public boolean needsMoreInfo() {
		return moreInfo;
	}
}
