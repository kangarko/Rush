package net.rush.model;

import org.bukkit.Material;

public class ItemStack {

	public static final ItemStack NULL_ITEMSTACK = new ItemStack(-1, -1, -1) {
        @Override
        public int getId() {
            throw new NullPointerException("You tried to use the legendary NULL-ItemStack!");
        }

        @Override
        public int getCount() {
            throw new NullPointerException("You tried to use the legendary NULL-ItemStack!");
        }

        @Override
        public int getDamage() {
            throw new NullPointerException("You tried to use the legendary NULL-ItemStack!");
        }
        
        @Override
        public int getDataLength() {
            throw new NullPointerException("You tried to use the legendary NULL-ItemStack!");
        }

        @Override
        public byte[] getData() {
            throw new NullPointerException("You tried to use the legendary NULL-ItemStack!");
        }
    };
	
	/**
	 * The ItemStack's id.
	 */
	public int id;

	/**
	 * The number of ItemStacks within the stack.
	 */
	public int count;

	/**
	 * The ItemStack's damage.
	 */
	public int damage;
	
	/**
	 * The ItemStack's NBT data length. -1 to disable
	 */
	private final int dataLength;
	
	/**
	 * The ItemStack's NBT byte array storing data (enchantments, etc).
	 */
	private final byte[] data;

	/**
	 * Creates a single ItemStack with no damage.
	 * @param id The ItemStack id.
	 */
	public ItemStack(int id) {
		this(id, 1);
	}

	/**
	 * Creates an ItemStack with no damage.
	 * @param id The id.
	 * @param count The number of ItemStacks within the stack.
	 */
	public ItemStack(int id, int count) {
		this(id, count, 0);
	}

	/**
	 * Creates an ItemStack with the specified count and damage. Generally ItemStacks that
	 * can be damaged cannot be stacked so the count should be one.
	 * @param id The id.
	 * @param count The number of ItemStacks within the stack.
	 * @param damage The damage.
	 */
	public ItemStack(int id, int count, int damage) {
		this(id, count, damage, -1, null);
	}
	
	/**
	 * Creates an ItemStack with the specified count, damage, data length and data. Generally ItemStacks that
	 * can be damaged cannot be stacked so the count should be one.
	 * @param id The id.
	 * @param count The number of ItemStacks within the stack.
	 * @param damage The damage.
	 */
	public ItemStack(int id, int count, int damage, int dataLength, byte[] data) {
		this.id = id;
		this.count = count;
		this.damage = damage;
		this.dataLength = dataLength;
		this.data = data;
	}

	/**
	 * Gets the id of this ItemStack.
	 * @return The id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the number of ItemStacks on a stack, generally this is between 1 and 64.
	 * @return The count of this ItemStack.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Gets the damage of this ItemStack.
	 * @return The damage of this ItemStack.
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * Gets the length of NBT data byte array.
	 * @return The length of NBT data byte array.
	 */
	public int getDataLength() {
		return dataLength;
	}

	/**
	 * Gets the NBT data byte array.
	 * @return The NBT data byte array.
	 */
	public byte[] getData() {
		return data;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setCount(int count) {
		this.count = count;	
	}
	
	public boolean doMaterialsMatch(ItemStack is) {
		return this.id == is.id && this.damage == is.damage;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + damage;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemStack other = (ItemStack) obj;
		if (count != other.count)
			return false;
		if (damage != other.damage)
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		return Material.getMaterial(id) + "x" + count +  (damage != 0 ? "@" + damage : "");
		//return String.format("ItemStack [id=%s,count=%d,damage=%d])", id, count, damage);
	}
}

