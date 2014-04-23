package net.rush.util.enums;

import net.rush.model.Block;
import net.rush.model.Item;

public enum EnumToolMaterial {
	
	WOOD(0, 59, 2.0F, 0.0F, 15), 
	STONE(1, 131, 4.0F, 1.0F, 5), 
	IRON(2, 250, 6.0F, 2.0F, 14), 
	DIAMOND(3, 1561, 8.0F, 3.0F, 10), 
	GOLD(0, 32, 12.0F, 0.0F, 22);

	/**
	 * The level of material this tool can harvest (3 = DIAMOND, 2 = IRON, 1 = STONE, 0 = IRON/GOLD)
	 */
	private final int harvestLevel;

	/**
	 * The number of uses this material allows. (wood = 59, stone = 131, iron = 250, diamond = 1561, gold = 32)
	 */
	private final int maxUses;

	/**
	 * The strength of this tool material against blocks which it is effective against.
	 */
	private final float properEfficiency;

	/** Damage versus entities. */
	private final float damageVsEntity;

	/** Defines the natural enchantability factor of the material. */
	private final int enchantability;

	private EnumToolMaterial(int harvestLevel, int maxUses, float properEfficiency, float damageVsEntity, int enchantability) {
		this.harvestLevel = harvestLevel;
		this.maxUses = maxUses;
		this.properEfficiency = properEfficiency;
		this.damageVsEntity = damageVsEntity;
		this.enchantability = enchantability;
	}

	/**
	 * The number of uses this material allows. (wood = 59, stone = 131, iron = 250, diamond = 1561, gold = 32)
	 */
	public int getMaxUses() {
		return maxUses;
	}

	/**
	 * The strength of this tool material against blocks which it is effective against.
	 */
	public float getProperEfficiency() {
		return properEfficiency;
	}

	/**
	 * Returns the damage against a given entity.
	 */
	public float getDamageVsEntity() {
		return damageVsEntity;
	}

	/**
	 * The level of material this tool can harvest (3 = DIAMOND, 2 = IRON, 1 = STONE, 0 = IRON/GOLD)
	 */
	public int getHarvestLevel() {
		return harvestLevel;
	}

	/**
	 * Return the natural enchantability factor of the material.
	 */
	public int getEnchantability() {
		return enchantability;
	}

	/**
	 * Return the crafting material for this tool material, used to determine the item that can be used to repair a tool
	 * with an anvil
	 */
	public int getCraftingMaterial() {
		return this == WOOD ? Block.WOOD.id : this == STONE ? Block.COBBLESTONE.id : this == GOLD ? Item.GOLD_INGOT.id : this == IRON ? Item.IRON_INGOT.id : this == DIAMOND ? Item.DIAMOND.id : 0;
	}
}
