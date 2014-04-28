package net.rush.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.rush.packets.serialization.HashcodeAndEqualsStub;

public abstract class Packet extends HashcodeAndEqualsStub {

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("@").append(Integer.toHexString(hashCode()));
		sb.append(String.format(" (%1$d/0x0%1$X) ", getOpcode()));
		sb.append("[").append(getToStringDescription()).append("]");
		return sb.toString();
	}

	public Class<? extends Packet> getPacketType() {
		return super.getInterface();
	}

	public static int readVarInt(ByteBuf input) {
		int out = 0;
		int bytes = 0;
		byte in;
		while (true) {
			in = input.readByte();

			out |= (in & 0x7F) << (bytes++ * 7);

			if (bytes > 32)
				throw new RuntimeException("VarInt too big");

			if ((in & 0x80) != 0x80)
				break;
		}

		return out;
	}

	public static void writeVarInt(int value, ByteBuf output) {
		int part;
		while (true) {
			part = value & 0x7F;

			value >>>= 7;
			if (value != 0) {
				part |= 0x80;
			}

			output.writeByte(part);

			if (value == 0)
				break;
		}
	}

	public static int readVarInt(DataInput input) throws IOException {
		int out = 0;
		int bytes = 0;
		byte in;
		while (true) {
			in = input.readByte();

			out |= (in & 0x7F) << (bytes++ * 7);

			if (bytes > 32) {
				throw new RuntimeException("VarInt too big");
			}

			if ((in & 0x80) != 0x80) {
				break;
			}
		}

		return out;
	}

	public static void writeVarInt(int value, DataOutput output) throws IOException {
		int part;
		while (true) {
			part = value & 0x7F;

			value >>>= 7;
			if (value != 0) {
				part |= 0x80;
			}

			output.write(part);

			if (value == 0) {
				break;
			}
		}
	}

	public abstract String getToStringDescription();

	public abstract int getOpcode();

	// FIXME
	public void readData_MC_1_6(ByteBufInputStream input) {
	}
}
