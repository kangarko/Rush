package net.rush.model;


/**
 * An immutable class which represents an in-game item (this also includes
 * blocks which are dropped on the floor or placed in the player's inventory).

 */
public class Item {

	public static final Item NULL_ITEM = new Item(-1, -1, -1) {
        @Override
        public int getId() {
            throw new NullPointerException("You tried to use the NULL-Item!");
        }

        @Override
        public int getCount() {
            throw new NullPointerException("You tried to use the NULL-Item!");
        }

        @Override
        public int getDamage() {
            throw new NullPointerException("You tried to use the NULL-Item!");
        }
        
        @Override
        public int getDataLength() {
            throw new NullPointerException("You tried to use the NULL-Item!");
        }

        @Override
        public byte[] getData() {
            throw new NullPointerException("You tried to use the NULL-Item!");
        }
    };
	
	/**
	 * The item's id.
	 */
	private final int id;

	/**
	 * The number of items within the stack.
	 */
	private int count;

	/**
	 * The item's damage.
	 */
	private final int damage;
	
	/**
	 * The item's NBT data length. -1 to disable
	 */
	private final int dataLength;
	
	/**
	 * The item's NBT byte array storing data (enchantments, etc).
	 */
	private final byte[] data;

	/**
	 * Creates a single item with no damage.
	 * @param id The item id.
	 */
	public Item(int id) {
		this(id, 1);
	}

	/**
	 * Creates an item with no damage.
	 * @param id The id.
	 * @param count The number of items within the stack.
	 */
	public Item(int id, int count) {
		this(id, count, 0);
	}

	/**
	 * Creates an item with the specified count and damage. Generally items that
	 * can be damaged cannot be stacked so the count should be one.
	 * @param id The id.
	 * @param count The number of items within the stack.
	 * @param damage The damage.
	 */
	public Item(int id, int count, int damage) {
		this(id, count, damage, -1, null);
	}
	
	/**
	 * Creates an item with the specified count, damage, data length and data. Generally items that
	 * can be damaged cannot be stacked so the count should be one.
	 * @param id The id.
	 * @param count The number of items within the stack.
	 * @param damage The damage.
	 */
	public Item(int id, int count, int damage, int dataLength, byte[] data) {
		this.id = id;
		this.count = count;
		this.damage = damage;
		this.dataLength = dataLength;
		this.data = data;
	}

	/**
	 * Gets the id of this item.
	 * @return The id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the number of items on a stack, generally this is between 1 and 64.
	 * @return The count of this item.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Gets the damage of this item.
	 * @return The damage of this item.
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
	
	
	/**
	 * Sets the amount of the item.
	 */
	public void setCount(int count) {
		this.count = count;
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
		Item other = (Item) obj;
		if (count != other.count)
			return false;
		if (damage != other.damage)
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("Item [id=%s,count=%d,damage=%d])", id, count, damage);
	}

}

