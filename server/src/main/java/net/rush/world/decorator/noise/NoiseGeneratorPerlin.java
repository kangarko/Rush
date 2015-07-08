package net.rush.world.decorator.noise;

import java.util.Random;

public class NoiseGeneratorPerlin extends NoiseGenerator {

	private int[] d;
	public double a;
	public double b;
	public double c;

	public NoiseGeneratorPerlin() {
		this(new Random());
	}

	public NoiseGeneratorPerlin(Random random) {
		d = new int[512];
		a = random.nextDouble() * 256.0D;
		b = random.nextDouble() * 256.0D;
		c = random.nextDouble() * 256.0D;

		int i;

		for (i = 0; i < 256; d[i] = i++)
			;

		for (i = 0; i < 256; ++i) {
			int j = random.nextInt(256 - i) + i;
			int k = d[i];

			d[i] = d[j];
			d[j] = k;
			d[i + 256] = d[i];
		}
	}

	public double a(double d0, double d1, double d2) {
		double d3 = d0 + a;
		double d4 = d1 + b;
		double d5 = d2 + c;
		int i = (int) d3;
		int j = (int) d4;
		int k = (int) d5;

		if (d3 < i)
			--i;

		if (d4 < j)
			--j;

		if (d5 < k)
			--k;

		int l = i & 255;
		int i1 = j & 255;
		int j1 = k & 255;

		d3 -= i;
		d4 -= j;
		d5 -= k;
		double d6 = d3 * d3 * d3 * (d3 * (d3 * 6.0D - 15.0D) + 10.0D);
		double d7 = d4 * d4 * d4 * (d4 * (d4 * 6.0D - 15.0D) + 10.0D);
		double d8 = d5 * d5 * d5 * (d5 * (d5 * 6.0D - 15.0D) + 10.0D);
		int k1 = d[l] + i1;
		int l1 = d[k1] + j1;
		int i2 = d[k1 + 1] + j1;
		int j2 = d[l + 1] + i1;
		int k2 = d[j2] + j1;
		int l2 = d[j2 + 1] + j1;

		return b(d8, b(d7, b(d6, this.a(d[l1], d3, d4, d5), this.a(d[k2], d3 - 1.0D, d4, d5)), b(d6, this.a(d[i2], d3, d4 - 1.0D, d5), this.a(d[l2], d3 - 1.0D, d4 - 1.0D, d5))), b(d7, b(d6, this.a(d[l1 + 1], d3, d4, d5 - 1.0D), this.a(d[k2 + 1], d3 - 1.0D, d4, d5 - 1.0D)), b(d6, this.a(d[i2 + 1], d3, d4 - 1.0D, d5 - 1.0D), this.a(d[l2 + 1], d3 - 1.0D, d4 - 1.0D, d5 - 1.0D))));
	}

	public double b(double d0, double d1, double d2) {
		return d1 + d0 * (d2 - d1);
	}

	public double a(int i, double d0, double d1, double d2) {
		int j = i & 15;
		double d3 = j < 8 ? d0 : d1;
		double d4 = j < 4 ? d1 : j != 12 && j != 14 ? d2 : d0;

		return ((j & 1) == 0 ? d3 : -d3) + ((j & 2) == 0 ? d4 : -d4);
	}

	public double a(double d0, double d1) {
		return this.a(d0, d1, 0.0D);
	}

	public void generateNoise(double[] cache, double d0, double d1, double d2, int i, int j, int k, double d3, double d4, double d5, double d6) {
		int l = 0;
		double d7 = 1.0D / d6;
		int i1 = -1;
		double d8 = 0.0D;
		double d9 = 0.0D;
		double d10 = 0.0D;
		double d11 = 0.0D;

		for (int j1 = 0; j1 < i; ++j1) {
			double d12 = (d0 + j1) * d3 + a;
			int k1 = (int) d12;

			if (d12 < k1)
				--k1;

			int l1 = k1 & 255;

			d12 -= k1;
			double d13 = d12 * d12 * d12 * (d12 * (d12 * 6.0D - 15.0D) + 10.0D);

			for (int i2 = 0; i2 < k; ++i2) {
				double d14 = (d2 + i2) * d5 + c;
				int j2 = (int) d14;

				if (d14 < j2)
					--j2;

				int k2 = j2 & 255;

				d14 -= j2;
				double d15 = d14 * d14 * d14 * (d14 * (d14 * 6.0D - 15.0D) + 10.0D);

				for (int l2 = 0; l2 < j; ++l2) {
					double d16 = (d1 + l2) * d4 + b;
					int i3 = (int) d16;

					if (d16 < i3)
						--i3;

					int j3 = i3 & 255;

					d16 -= i3;
					double d17 = d16 * d16 * d16 * (d16 * (d16 * 6.0D - 15.0D) + 10.0D);

					if (l2 == 0 || j3 != i1) {
						i1 = j3;
						int k3 = d[l1] + j3;
						int l3 = d[k3] + k2;
						int i4 = d[k3 + 1] + k2;
						int j4 = d[l1 + 1] + j3;
						int k4 = d[j4] + k2;
						int l4 = d[j4 + 1] + k2;

						d8 = b(d13, this.a(d[l3], d12, d16, d14), this.a(d[k4], d12 - 1.0D, d16, d14));
						d9 = b(d13, this.a(d[i4], d12, d16 - 1.0D, d14), this.a(d[l4], d12 - 1.0D, d16 - 1.0D, d14));
						d10 = b(d13, this.a(d[l3 + 1], d12, d16, d14 - 1.0D), this.a(d[k4 + 1], d12 - 1.0D, d16, d14 - 1.0D));
						d11 = b(d13, this.a(d[i4 + 1], d12, d16 - 1.0D, d14 - 1.0D), this.a(d[l4 + 1], d12 - 1.0D, d16 - 1.0D, d14 - 1.0D));
					}

					double d18 = b(d17, d8, d9);
					double d19 = b(d17, d10, d11);
					double d20 = b(d15, d18, d19);
					int i5 = l++;

					cache[i5] += d20 * d7;
				}
			}
		}
	}
}
