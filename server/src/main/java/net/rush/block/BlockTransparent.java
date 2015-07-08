package net.rush.block;

public class BlockTransparent extends Block {

	protected BlockTransparent(int id) {
		super(id);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

}
