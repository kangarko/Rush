package net.rush.chunk;

import net.rush.model.Block;
import net.rush.model.Position;

public class BlockPosition {

	private final Block block;
	private final Position position;
	
	public BlockPosition(Block block, Position position) {
		this.block = block;
		this.position = position;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public Position getPositon() {
		return position;
	}	
}
