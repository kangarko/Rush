package net.rush.model;


public class BlockWood extends Block {

	public static final String[] woodType = new String[] { "oak", "spruce", "birch", "jungle" };

	public BlockWood(int id) {
		super(id, Material.WOOD);
	}

	@Override
	public int damageDropped(int damage) {
		return damage;
	}
}
