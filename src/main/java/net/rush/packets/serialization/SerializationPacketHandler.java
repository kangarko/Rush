package net.rush.packets.serialization;

import static net.rush.packets.serialization.SerializationHelper.fixupClasses;
import static net.rush.packets.serialization.SerializationHelper.getSerializationInfos;
import io.netty.buffer.ByteBufInputStream;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.rush.packets.Packet;

public class SerializationPacketHandler<T extends Packet> {

	public T handle(ByteBufInputStream in, Class<T> type) {
		try {
			List<SerializationInfo> serInfos = getSerializationInfos(type);
			Object[] params = new Object[serInfos.size()];
			Class<?>[] paramTypes = new Class<?>[serInfos.size()];
			for (int i = 0; i < serInfos.size(); i++) {
				Serialize serialize = serInfos.get(i).getSerialize();
				Serializor<?> serializor = serialize.type().getSerializor();
				if (serializor instanceof ObjectUsingSerializor) {
					ObjectUsingSerializor<?> ous = (ObjectUsingSerializor<?>) serializor;
					params[i] = ous.read(in, params[serialize.moreInfo()]);
				} else {
					params[i] = serializor.read(in);
				}
				Class<?> paramClazz = params[i].getClass();
				if (Map.class.isAssignableFrom(paramClazz))
					paramClazz = Map.class;
				if (Set.class.isAssignableFrom(paramClazz))
					paramClazz = Set.class;
				paramTypes[i] = paramClazz;
			}
			Constructor<? extends T> ctor = type.getConstructor(fixupClasses(paramTypes));
			return ctor.newInstance(params);
		} catch (Exception ex) {
			throw new RuntimeException("Error while reading packet " + type.getSimpleName(), ex);
		}
	}
}
