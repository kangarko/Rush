package net.rush.model.item;


public class ItemBlockWithMetadata extends ItemBlock {
	
	public ItemBlockWithMetadata(int id) {
		super(id);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	/**
	 * Returns the metadata of the block which this Item (ItemBlock) can place
	 */
	@Override
	public int getMetadata(int value) {
		return value;
	}
}
