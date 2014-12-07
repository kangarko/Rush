package net.rush.model;

import java.util.zip.Deflater;

import net.rush.api.world.Chunk;
import net.rush.protocol.Packet;
import net.rush.protocol.packets.ChunkBulk;

import org.apache.commons.lang3.Validate;


public final class RushChunk implements Chunk {

	public static final int WIDTH = 16, DEPTH = 256, HEIGHT = 16;
	public static final int SIZE = WIDTH * HEIGHT * DEPTH;

	private final int x, z;

	/**
	 * The data in this chunk representing all of the blocks and their state.
	 */
	public final byte[] types, metaData, skyLight, blockLight, biomes;

	/**
	 * Creates a new chunk with a specified X and Z coordinates.
	 */
	public RushChunk(int x, int z) {
		this.x = x;
		this.z = z;

		types = new byte[SIZE];
		metaData = new byte[SIZE];
		skyLight = new byte[SIZE];
		blockLight = new byte[SIZE];
		biomes = new byte[SIZE];
	}
	
	@Override
	public int getX() {
		return x;
	}
	
	@Override
	public int getZ() {		
		return z;
	}

	@Override
	public int getType(int x, int y, int z) {
		return types[coordToIndex(x, y, z)];
	}

	@Override
	public void setType(int x, int y, int z, int type) {
		Validate.isTrue(type >= 0 && type <= Byte.MAX_VALUE, "Block ID outside the range");

		types[coordToIndex(x, y, z)] = (byte) type;
	}

	@Override
	public void setTypeAndData(int x, int y, int z, int type, int data) {
		setType(x, y, z, type);
		setMetadata(x, y, z, data);
	}

	@Override
	public int getMetadata(int x, int y, int z) {
		return metaData[coordToIndex(x, y, z)];
	}

	@Override
	public void setMetadata(int x, int y, int z, int metadata) {
		Validate.isTrue(metadata >= 0 && metadata < 16, "Metadata must be between 0 and 15");

		metaData[coordToIndex(x, y, z)] = (byte) metadata;
	}

	public int getSkyLight(int x, int y, int z) {
		return skyLight[coordToIndex(x, y, z)];
	}

	public void setSkyLight(int x, int y, int z, int skylight) {
		Validate.isTrue(skylight >= 0 && skylight < 16, "Skylight must be between 0 and 15");

		skyLight[coordToIndex(x, y, z)] = (byte) skylight;
	}

	public int getBlockLight(int x, int y, int z) {
		return blockLight[coordToIndex(x, y, z)];
	}

	public void setBlockLight(int x, int y, int z, int blocklight) {
		Validate.isTrue(blocklight >= 0 && blocklight < 16, "Blocklight must be between 0 and 15");

		blockLight[coordToIndex(x, y, z)] = (byte) blocklight;
	}

	/**
	 * Creates a new {@link Packet} which can be sent to a client to stream this chunk to them.
	 */
	public Packet toPacket() {
		return new ChunkBulk(x, z, true, 0xFFFF, 0, serializeTileData());
		//return new MapChunkPacketImpl(x * Chunk.WIDTH, z * Chunk.HEIGHT, 0, WIDTH, HEIGHT, DEPTH, serializeTileData());
	}

	/**
	 * Converts a three-dimensional coordinate to an index within the
	 * one-dimensional arrays.
	 * @return The index within the arrays.
	 */
	private int coordToIndex(int x, int y, int z) {
		Validate.isTrue(x >= 0 && z >= 0 && y >= 0 && x <= WIDTH && y <= DEPTH && z <= HEIGHT, "Coords out of bound! x:" + x + ", z:" + z + ", y:" + y);

		return y << 8 | z << 4 | x;
	}

	public byte[] serializeTileData() {
		// (types + metaData + blocklight + skylight + add) * 16 vanilla-chunks + biome
		byte[] data;

		int pos = types.length;

		data = new byte[(4096 + 2048 + 2048 + 2048 + 0) * 16 + 256];
		// types
		System.arraycopy(types, 0, data, 0, types.length);

		if (pos != types.length)
			throw new IllegalStateException("Illegal pos: " + pos + " vs " + types.length);

		// metadata
		for (int i = 0; i < metaData.length; i += 2) {
			byte meta1 = metaData[i];
			byte meta2 = metaData[i + 1];
			data[pos++] = (byte) ((meta2 << 4) | meta1);
		}

		// skylight
		for (int i = 0; i < skyLight.length; i += 2) {
			byte light1 = skyLight[i];
			byte light2 = skyLight[i + 1];
			data[pos++] = (byte) ((light2 << 4) | light1);
		}

		// blocklight
		for (int i = 0; i < blockLight.length; i += 2) {
			byte light1 = blockLight[i];
			byte light2 = blockLight[i + 1];
			data[pos++] = (byte) ((light2 << 4) | light1);
		}

		// biome
		for (int i = 0; i < 256; i++)
			data[pos++] = biomes[i];

		if (pos != data.length)
			throw new IllegalStateException("Illegal Pos: " + pos + " vs " + data.length);

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
}

