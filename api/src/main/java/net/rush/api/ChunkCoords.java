package net.rush.api;


public class ChunkCoords {

	public final int x, z;

	public ChunkCoords(int x, int z) {
		this.x = x;
		this.z = z;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;

		ChunkCoords other = (ChunkCoords) obj;
		return x == other.x && z == other.z;
	}

	@Override
	public String toString() {
		return "ChunkCoords [x=" + x + ", z=" + z + "]";
	}
}
