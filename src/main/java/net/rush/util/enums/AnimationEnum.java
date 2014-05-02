package net.rush.util.enums;

import net.rush.util.RushException;

public enum AnimationEnum {

	NO_ANIMATION(0),
	SWING_ARM(1),
	DAMAGE_ANIM(2),
	LEAVE_BED(3),
	EAT_FOOD(5),
	CRITICAL_EFFECT(6),
	MAGIC_CRITICAL_EFFECT(7),
	UNKNOWN(102),
	CROUNCH(104),
	UNCROUNCH(105);

	int id;

	AnimationEnum(int id) {
		this.id = id;
	}

	public byte getId() {
		return (byte)id;
	}

	public static AnimationEnum fromId(int id) {
		switch (id) {
			case 0:
				return NO_ANIMATION;
			case 1:
				return SWING_ARM;
			case 2:
				return DAMAGE_ANIM;
			case 3:
				return LEAVE_BED;
			case 5:
				return EAT_FOOD;
			case 6:
				return CRITICAL_EFFECT;
			case 7:
				return MAGIC_CRITICAL_EFFECT;
			case 102:
				return UNKNOWN;
			case 104:
				return CROUNCH;
			case 105:
				return UNCROUNCH;
			default: throw new RushException("Unknown animation id " + id);
		}
	}
}
