package net.rush.model;

import net.rush.model.blocks.BlockLeaves;
import net.rush.model.blocks.BlockLog;
import net.rush.model.blocks.BlockStone;

public class Block {

	public static final Block AIR = new Block(0, Material.AIR);
	public static final Block STONE = new BlockStone(1);
	public static final Block LOG = new BlockLog(17);
	public static final Block LEAVES = new BlockLeaves(18);

	public final Material material;
	public final int id;

	public static final Block[] byId = new Block[2048];

	protected Block(int id, Material material) {
		if (byId[id] != null)
			throw new IllegalArgumentException("Slot " + id + " is already occupied by " + byId[id] + " when adding " + this);

		byId[id] = this;
		this.material = material;
		this.id = id;
		// TODO lightBlock[id]


	}



}

