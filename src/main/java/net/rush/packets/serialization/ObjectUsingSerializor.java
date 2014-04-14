package net.rush.packets.serialization;

import java.io.DataInput;
import java.io.IOException;

abstract class ObjectUsingSerializor<T> implements Serializor<T> {
	
	@Override
	@Deprecated
	public final T read(DataInput in) throws IOException {
		throw new UnsupportedOperationException();
	}

	public abstract T read(DataInput in, Object moreInfo) throws IOException;
}
