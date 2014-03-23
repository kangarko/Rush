package net.rush.chunk;

import java.io.Serializable;

public class ChunkCoords implements Serializable {
    private static final long serialVersionUID = 1L;

    public final int x;
    public final int z;

    public ChunkCoords(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getBlockX() {
        return x * 16;
    }

    public int getBlockZ() {
        return z * 16;
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
        if (!(obj instanceof ChunkCoords))
            return false;
        ChunkCoords other = (ChunkCoords) obj;
        if (x != other.x)
            return false;
        if (z != other.z)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ChunkCoords [x=" + x + ", z=" + z + "]";
    }
}
