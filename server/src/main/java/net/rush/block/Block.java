package net.rush.block;

import java.util.Objects;

import org.apache.commons.lang3.Validate;

import net.rush.entity.EntityPlayer;
import net.rush.world.World;

public abstract class Block {

	private static final Block[] byId = new Block[255];
	
	public static final Block AIR = new BlockTransparent(0);
	public static final Block STONE = new BlockDummy(1);
	public static final Block GRASS = new BlockGrass(2);
	public static final Block DIRT = new BlockDummy(3);
	public static final Block COBBLESTONE = new BlockDummy(4);
	public static final Block BEDROCK = new BlockDummy(7);
	public static final Block WATER = new BlockDummy(8);
	public static final Block STATIONARY_WATER = new BlockDummy(9);
	public static final Block LAVA = new BlockDummy(10);
	public static final Block STATIONARY_LAVA = new BlockDummy(11);
	public static final Block SAND = new BlockDummy(12);
	public static final Block GRAVEL = new BlockDummy(13);	
	public static final Block GOLD_ORE = new BlockDummy(14);
	public static final Block IRON_ORE = new BlockDummy(15);
	public static final Block COAL_ORE = new BlockDummy(16);	
	public static final Block LOG = new BlockDummy(17);
	public static final Block LEAVES = new BlockTransparent(18);
	public static final Block TALL_GRASS = new BlockFlower(31);
	public static final Block DEAD_BUSH = new BlockFlower(32);
	public static final Block YELLOW_FLOWER = new BlockFlower(37);
	public static final Block RED_ROSE = new BlockFlower(38);
	public static final Block BROWN_MUSHROOM = new BlockFlower(39);
	public static final Block RED_MUSHROOM = new BlockFlower(40);
	public static final Block MOSSY_COBBLESTONE = new BlockDummy(48);
	public static final Block DIAMOND_ORE = new BlockDummy(56);
	public static final Block REDSTONE_ORE = new BlockDummy(73);
	public static final Block CACTUS = new BlockCactus(81);
	public static final Block CLAY_BLOCK = new BlockDummy(82);
	
	public final int id;
	
	protected Block(int id) {
		this.id = id;
		
		Validate.isTrue(byId[id] == null, "Error assigning " + this + " to id " + id + "! Id occupied by " + byId[id]);
		byId[id] = this;
	}
	
	public static final Block byId(int id) {
		Validate.isTrue(id < byId.length, "Id out of range! (max. " + byId.length + ")");
		
		Block block = byId[id];
		Objects.requireNonNull(block, "Block id " + id + " not found!");
		
		return block;
	}
	
	public static final boolean exists(int id) {
		return id < byId.length ? byId[id] != null : false;
	}
	
	public void onBlockDestroy(World world, EntityPlayer player, int x, int y, int z, int metadata) {
	}
	
	public void onTick(World world, int x, int y, int z) {
	}
	
	public boolean isTickingRandomly() {
		return false;
	}
	
	public boolean canPlaceAt(World world, int x, int y, int z) {
		return true;
	}
	
	/**
	 * Is the block non-transparent?
	 */
	public boolean isOpaque() {
		return true;
	}
	
	@Override
	public final String toString() {
		return getClass().getSimpleName() + " {id=" + id + "}";
	}
}
