package net.rush.packets.serialization;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface Serializor<T> {
	T read(DataInput in) throws IOException;

	void write(DataOutput out, T val) throws IOException;
}
