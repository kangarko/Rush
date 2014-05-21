package net.rush.model.entity.ai;

import java.util.Random;

import net.rush.model.LivingEntity;

public abstract class EntityAI {

	protected final LivingEntity entity;
	protected final Random rand = new Random();
	
	protected EntityAI(LivingEntity entity) {
		this.entity = entity;
	}
	
	/**
	 * Returns whether the task requires multiple updates or not
	 */
	public boolean isContinuous() {
		return true;
	}

	public abstract void pulse();
}
