package net.rush.api;

/**
 * Represents a stack of items
 */
public class ItemStack {
   
	public int id, count, data;

    public ItemStack(int type) {
        this(type, 1);
    }

    public ItemStack(int type, int amount) {
        this(type, amount, 0);
    }

    public ItemStack(int type, int amount, int data) {
        this.id = type;
        this.count = amount;
        this.data = data;
    }

    @Override
    public String toString() {
       return "ItemStack{id=" + id + ",count=" + count + ",data=" + data + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        
        if (!(obj instanceof ItemStack))
            return false;

        ItemStack stack = (ItemStack) obj;
        return count == stack.count && isSimilar(stack);
    }

    /**
     * @returns if the ID and data matches
     */
	public boolean isSimilar(ItemStack is) {
		return this.id == is.id && this.data == is.data;
	}
}
