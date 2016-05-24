package net.rush.world.chunk;

import java.util.Arrays;
import java.util.zip.Deflater;

import org.apache.commons.lang3.Validate;

import lombok.Getter;
import net.rush.protocol.Packet;
import net.rush.protocol.packets.ChunkBulk;
import net.rush.world.World;

public final class Chunk {

	public static final int WIDTH = 16, DEPTH = 256, HEIGHT = 16;
	public static final int SIZE = WIDTH * HEIGHT * DEPTH;

	/**
	 * The data in this chunk representing all of the blocks and their state.
	 */
	public byte[] types, metadata, skylight, blocklight, biomes;

	private World world;
	
	@Getter
	private final int x, z;
	@Getter
	private boolean populated = false;

	public Chunk(World world, int x, int z) {
		this(world, x, z, null);
	}
	
	public Chunk(World world, int x, int z, byte[] blocks) {
		this.world = world;
		this.x = x;
		this.z = z;

		if (blocks == null)
			blocks = new byte[SIZE];
		else
			Validate.isTrue(blocks.length == SIZE, "Types must be " + SIZE + " lenght!");
		
		types = blocks;
		metadata = new byte[SIZE];
		skylight = new byte[SIZE];
		blocklight = new byte[SIZE];
		biomes = new byte[SIZE];
		
		initLightning();
	}
	
	private void initLightning() {
		Arrays.fill(skylight, (byte) 15);
		Arrays.fill(blocklight, (byte) 15);
	}

	public int getType(int x, int y, int z) {
		return types[coordToIndex(x, y, z)];
	}

	public void setType(int x, int y, int z, int type) {
		Validate.isTrue(type >= 0 && type <= Byte.MAX_VALUE, "Block ID outside the range");

		types[coordToIndex(x, y, z)] = (byte) type;
	}

	public void setTypeAndData(int x, int y, int z, int type, int data) {
		setType(x, y, z, type);
		setData(x, y, z, data);
	}

	public int getData(int x, int y, int z) {		
		return metadata[coordToIndex(x, y, z)];
	}

	public void setData(int x, int y, int z, int data) {
		Validate.isTrue(data >= 0 && data < 16, "Metadata must be between 0 and 15");

		metadata[coordToIndex(x, y, z)] = (byte) data;
	}

	public int getSkylight(int x, int y, int z) {
		return skylight[coordToIndex(x, y, z)];
	}

	public void setSkylight(int x, int y, int z, int lightLevel) {
		Validate.isTrue(lightLevel >= 0 && lightLevel < 16, "Skylight must be between 0 and 15");

		skylight[coordToIndex(x, y, z)] = (byte) lightLevel;
	}

	public int getBlockLight(int x, int y, int z) {
		return blocklight[coordToIndex(x, y, z)];
	}

	public void setBlockLight(int x, int y, int z, int lightLevel) {
		Validate.isTrue(lightLevel >= 0 && lightLevel < 16, "Blocklight must be between 0 and 15");
		
		blocklight[coordToIndex(x, y, z)] = (byte) lightLevel;
	}

	/**
	 * Converts a three-dimensional coordinate to an index within the
	 * one-dimensional arrays.
	 */
	private int coordToIndex(int x, int y, int z) {
		Validate.isTrue(x >= 0 && z >= 0 && y >= 0 && x <= WIDTH && y <= DEPTH && z <= HEIGHT, "Coords out of bound! x:" + x + ", y:" + y + ", z:" + z);

		return y << 8 | z << 4 | x;
	}

	public void setPopulated() {
		Validate.isTrue(!populated, "Already populated!");

		this.populated = true;
	}
	
	public int getTerrainHeight(int x, int z) {
		for (int y = Chunk.DEPTH - 1; y > 0; y--)
			if (getType(x, y, z) != 0)
				return y + 1;

		return 0;
	}

	/**
	 * Creates a new {@link Packet} which can be sent to a client to stream this chunk to them.
	 */
	public Packet toPacket() {
		return new ChunkBulk(x, z, true, 0xFFFF, 0, serializeTileData());
	}

	private byte[] serializeTileData() {
		// (types + metaData + blocklight + skylight + add) * 16 sections in 1 chunks + biome // Using chunk sections.
		//final byte[] data = new byte[(4096 + 2048 + 2048 + 2048 + 0) * 16 + 256];

		// (types + metaData + blocklight + skylight + add) + biome // Chunk is not divided into sections.
		final byte[] data = new byte[65536 + 32768 + 32768 + 32768 + 0 + 256];

		int pos = types.length;

		// types
		System.arraycopy(types, 0, data, 0, types.length);

		Validate.isTrue(pos == types.length, "Illegal pos: " + pos + " vs " + types.length);

		byte firstBit, secondBit;

		// metadata
		for (int i = 0; i < metadata.length; i += 2) {
			firstBit = metadata[i];
			secondBit = metadata[i + 1];

			data[pos++] = (byte) ((secondBit << 4) | firstBit);
		}

		// skylight
		for (int i = 0; i < skylight.length; i += 2) {
			firstBit = skylight[i];
			secondBit = skylight[i + 1];

			data[pos++] = (byte) ((secondBit << 4) | firstBit);
		}

		// blocklight
		for (int i = 0; i < blocklight.length; i += 2) {
			firstBit = blocklight[i];
			secondBit = blocklight[i + 1];

			data[pos++] = (byte) ((secondBit << 4) | firstBit);
		}

		// biome
		for (int i = 0; i < 256; i++)
			data[pos++] = biomes[i];

		// Check if the position has reached the data length and filling up the whole array.
		Validate.isTrue(pos == data.length, "Illegal Pos: " + pos + " vs " + data.length);

		// compress
		Deflater deflater = new Deflater(Deflater.BEST_SPEED);
		deflater.setInput(data);
		deflater.finish();

		// TODO clean up
		byte[] compressed = new byte[data.length];
		int length = deflater.deflate(compressed);

		deflater.end();

		byte[] realCompressed = new byte[length];
		for (int i = 0; i < length; i++)
			realCompressed[i] = compressed[i];

		return realCompressed;
	}
	
	@Override
	public String toString() {
		return "Chunk{" + x + "," + z + "}";
	}
}

