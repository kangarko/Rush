package net.rush.world.populator;

import java.util.Random;

import imported.MathHelper;
import net.rush.world.World;

public class WorldGenBigTree extends WorldPopulator {

	private static final byte[] otherCoordPairs = new byte[] { 2, 0, 0, 1, 2, 1 };

	private World world;
	private Random rand = new Random();

	private int[] defaultPos = new int[] { 0, 0, 0 };
	private int heightLimit = 0;
	private int height;
	private double heightAttenuation = 0.618D;
	//private double branchDensity = 1.0D;
	private double branchSlope = 0.381D;
	private double scaleWidth = 1.0D;
	private double leafDensity = 1.0D;

	int trunkSize = 1;
	int heightLimitLimit = 12;
	int leafDistanceLimit = 4;
	int[][] leafNodes;

	void generateLeafNodeList() {
		height = (int) (heightLimit * heightAttenuation);
		if (height >= heightLimit)
			height = heightLimit - 1;

		int i = (int) (1.382D + Math.pow(leafDensity * heightLimit / 13.0D, 2.0D));

		if (i < 1)
			i = 1;

		int[][] leafNodeList = new int[i * heightLimit][4];
		int j = defaultPos[1] + heightLimit - leafDistanceLimit;
		int index = 1;
		int l = defaultPos[1] + height;
		int i1 = j - defaultPos[1];

		leafNodeList[0][0] = defaultPos[0];
		leafNodeList[0][1] = j;
		leafNodeList[0][2] = defaultPos[2];
		leafNodeList[0][3] = l;
		--j;

		while (i1 >= 0) {
			int j1 = 0;
			float f = this.layerSize(i1);

			if (f < 0.0F) {
				--j;
				--i1;
			} else {
				for (double d0 = 0.5D; j1 < i; ++j1) {
					double d1 = this.scaleWidth * f * (rand.nextFloat() + 0.328D);
					double d2 = rand.nextFloat() * 2.0D * 3.14159D;
					int k1 = (int) (d1 * Math.sin(d2) + defaultPos[0] + d0);
					int l1 = (int) (d1 * Math.cos(d2) + defaultPos[2] + d0);
					int[] aint1 = new int[] { k1, j, l1 };
					int[] aint2 = new int[] { k1, j + leafDistanceLimit, l1 };

					if (this.checkBlockLine(aint1, aint2) == -1) {
						int[] basePos = new int[] { defaultPos[0], defaultPos[1], defaultPos[2] };
						double d3 = Math.sqrt(Math.pow(Math.abs(defaultPos[0] - aint1[0]), 2.0D) + Math.pow(Math.abs(defaultPos[2] - aint1[2]), 2.0D));
						double d4 = d3 * this.branchSlope;

						if (aint1[1] - d4 > l)
							basePos[1] = l;
						else
							basePos[1] = (int) (aint1[1] - d4);

						if (this.checkBlockLine(basePos, aint1) == -1) {
							leafNodeList[index][0] = k1;
							leafNodeList[index][1] = j;
							leafNodeList[index][2] = l1;
							leafNodeList[index][3] = basePos[1];
							++index;
						}
					}
				}

				--j;
				--i1;
			}
		}

		leafNodes = new int[index][4];
		System.arraycopy(leafNodeList, 0, leafNodes, 0, index);
	}

	void genTreeLayer(int x, int y, int z, float leafSize, byte b0, int l) {
		int leafDistance = (int) (leafSize + heightAttenuation);
		byte b1 = otherCoordPairs[b0];
		byte b2 = otherCoordPairs[b0 + 3];
		int[] posFromArguments = new int[] { x, y, z };
		int[] pos = new int[] { 0, 0, 0 };
		int j1 = -leafDistance;
		int k1 = -leafDistance;

		for (pos[b0] = posFromArguments[b0]; j1 <= leafDistance; ++j1) {
			pos[b1] = posFromArguments[b1] + j1;
			k1 = -leafDistance;

			while (k1 <= leafDistance) {
				double d0 = Math.sqrt(Math.pow(Math.abs(j1) + 0.5D, 2.0D) + Math.pow(Math.abs(k1) + 0.5D, 2.0D));

				if (d0 > leafSize)
					++k1;
				else {
					pos[b2] = posFromArguments[b2] + k1;
					int l1 = world.getType(pos[0], pos[1], pos[2]);

					if (l1 != 0 && l1 != 18)
						++k1;
					else {
						world.setType(pos[0], pos[1], pos[2], l);
						++k1;
					}
				}
			}
		}
	}

	float layerSize(int i) {
		if (i < heightLimit * 0.3D)
			return -1.618F;
		else {
			float f = heightLimit / 2.0F;
			float f1 = heightLimit / 2.0F - i;
			float size;

			if (f1 == 0.0F)
				size = f;
			else if (Math.abs(f1) >= f)
				size = 0.0F;
			else
				size = (float) Math.sqrt(Math.pow(Math.abs(f), 2.0D) - Math.pow(Math.abs(f1), 2.0D));

			size *= 0.5F;
			return size;
		}
	}

	float leafSize(int distance) {
		return distance >= 0 && distance < leafDistanceLimit ? distance != 0 && distance != leafDistanceLimit - 1 ? 3.0F : 2.0F : -1.0F;
	}

	void generateLeafNode(int x, int y, int z) {
		int leafY = y;

		for (int leafDistance = y + leafDistanceLimit; leafY < leafDistance; ++leafY) {
			float leafSize = this.leafSize(leafY - y);

			this.genTreeLayer(x, leafY, z, leafSize, (byte) 1, 18);
		}
	}

	void placeBlockLine(int[] aint, int[] aint1, int i) {
		int[] aint2 = new int[] { 0, 0, 0 };
		byte b0 = 0;

		byte b1;

		for (b1 = 0; b0 < 3; ++b0) {
			aint2[b0] = aint1[b0] - aint[b0];
			if (Math.abs(aint2[b0]) > Math.abs(aint2[b1]))
				b1 = b0;
		}

		if (aint2[b1] != 0) {
			byte b2 = otherCoordPairs[b1];
			byte b3 = otherCoordPairs[b1 + 3];
			byte b4;

			if (aint2[b1] > 0)
				b4 = 1;
			else
				b4 = -1;

			double d0 = (double) aint2[b2] / (double) aint2[b1];
			double d1 = (double) aint2[b3] / (double) aint2[b1];
			int[] positions = new int[] { 0, 0, 0 };
			int j = 0;

			for (int k = aint2[b1] + b4; j != k; j += b4) {
				positions[b1] = MathHelper.ceiling_double_int(aint[b1] + j + 0.5D);
				positions[b2] = MathHelper.ceiling_double_int(aint[b2] + j * d0 + 0.5D);
				positions[b3] = MathHelper.ceiling_double_int(aint[b3] + j * d1 + 0.5D);
				world.setType(positions[0], positions[1], positions[2], i);
			}
		}
	}

	void generateLeaves() {
		int i = 0;

		for (int leafLength = leafNodes.length; i < leafLength; ++i) {
			int x = leafNodes[i][0];
			int y = leafNodes[i][1];
			int z = leafNodes[i][2];

			this.generateLeafNode(x, y, z);
		}
	}

	boolean leafNodeNeedsBase(int i) {
		return i >= heightLimit * 0.2D;
	}

	void generateTrunk() {
		int x = defaultPos[0];
		int y = defaultPos[1];
		int yHeight = defaultPos[1] + height;
		int z = defaultPos[2];
		int[] baseLine = new int[] { x, y, z };
		int[] topLine = new int[] { x, yHeight, z };

		this.placeBlockLine(baseLine, topLine, 17);
		if (this.trunkSize == 2) {
			++baseLine[0];
			++topLine[0];
			this.placeBlockLine(baseLine, topLine, 17);
			++baseLine[2];
			++topLine[2];
			this.placeBlockLine(baseLine, topLine, 17);
			baseLine[0] += -1;
			topLine[0] += -1;
			this.placeBlockLine(baseLine, topLine, 17);
		}
	}

	void generateLeafNodeBases() {
		int i = 0;
		int j = leafNodes.length;

		for (int[] aint = new int[] { defaultPos[0], defaultPos[1], defaultPos[2] }; i < j; ++i) {
			int[] aint1 = leafNodes[i];
			int[] aint2 = new int[] { aint1[0], aint1[1], aint1[2] };

			aint[1] = aint1[3];
			int k = aint[1] - defaultPos[1];

			if (this.leafNodeNeedsBase(k))
				this.placeBlockLine(aint, aint2, 17);
		}
	}

	int checkBlockLine(int[] firstTriplet, int[] secondTriplet) {
		int[] aint2 = new int[] { 0, 0, 0 };
		byte b0 = 0;

		byte b1;

		for (b1 = 0; b0 < 3; ++b0) {
			aint2[b0] = secondTriplet[b0] - firstTriplet[b0];
			if (Math.abs(aint2[b0]) > Math.abs(aint2[b1]))
				b1 = b0;
		}

		if (aint2[b1] == 0)
			return -1;
		else {
			byte b2 = otherCoordPairs[b1];
			byte b3 = otherCoordPairs[b1 + 3];
			byte b4;

			if (aint2[b1] > 0)
				b4 = 1;
			else
				b4 = -1;

			double d0 = (double) aint2[b2] / (double) aint2[b1];
			double d1 = (double) aint2[b3] / (double) aint2[b1];
			int[] aint3 = new int[] { 0, 0, 0 };
			int i = 0;

			int j;

			for (j = aint2[b1] + b4; i != j; i += b4) {
				aint3[b1] = firstTriplet[b1] + i;
				aint3[b2] = (int) (firstTriplet[b2] + i * d0);
				aint3[b3] = (int) (firstTriplet[b3] + i * d1);
				int blockId = world.getType(aint3[0], aint3[1], aint3[2]);

				if (blockId != 0 && blockId != 18)
					break;
			}

			return i == j ? -1 : Math.abs(i);
		}
	}

	boolean treeLocationValid() {
		int[] aint = new int[] { defaultPos[0], defaultPos[1], defaultPos[2] };
		int[] aint1 = new int[] { defaultPos[0], defaultPos[1] + heightLimit - 1, defaultPos[2] };
		int blockId = world.getType(defaultPos[0], defaultPos[1] - 1, defaultPos[2]);

		if (blockId != 2 && blockId != 3)
			return false;

		int blockLine = this.checkBlockLine(aint, aint1);

		if (blockLine == -1)
			return true;
		else if (blockLine < 6)
			return false;
		else {
			heightLimit = blockLine;
			return true;
		}
	}

	@Override
	public void setTreeGeneratorScale(double d0, double scaleWidth, double leafDensity) {
		heightLimitLimit = (int) (d0 * 12.0D);
		if (d0 > 0.5D)
			leafDistanceLimit = 5;

		this.scaleWidth = scaleWidth;
		this.leafDensity = leafDensity;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		this.world = world;
		long l = random.nextLong();

		rand.setSeed(l);
		defaultPos[0] = i;
		defaultPos[1] = j;
		defaultPos[2] = k;
		if (heightLimit == 0)
			heightLimit = 5 + rand.nextInt(heightLimitLimit);

		if (!treeLocationValid())
			return false;
		else {
			defaultPos[1]--;
			generateLeafNodeList();
			generateLeaves();
			generateTrunk();
			generateLeafNodeBases();
			return true;
		}
	}
}
