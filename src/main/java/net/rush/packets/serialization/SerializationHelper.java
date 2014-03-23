package net.rush.packets.serialization;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import net.rush.packets.Packet;
import net.rush.packets.packet.HandshakePacket;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

final class SerializationHelper {
    private SerializationHelper() {
    }

    private static final Cache<Class<? extends Packet>, List<SerializationInfo>> serializationInfoCache =
            CacheBuilder.newBuilder().weakKeys().softValues().expireAfterAccess(1, TimeUnit.MINUTES)
            .build(new CacheLoader<Class<? extends Packet>, List<SerializationInfo>>() {
                @Override
                public List<SerializationInfo> load(Class<? extends Packet> clazz) throws Exception {
                    // key has to be the interface-class
                    if (!clazz.isInterface())
                        throw new IllegalArgumentException("Key has to be interface!");
                    if (!clazz.getPackage().equals(HandshakePacket.class.getPackage()))
                        throw new IllegalArgumentException("Not in our package!");

                    clazz = getImplClass(clazz);
                    // *sigh* now let's get the attributes
                    List<SerializationInfo> serInfos = new LinkedList<SerializationInfo>();
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field f : fields) {
                        f.setAccessible(true);
                        if (f.isAnnotationPresent(Serialize.class))
                            serInfos.add(new SerializationInfo(f.getAnnotation(Serialize.class), f
                                    .getName()));
                        f.setAccessible(false);
                    }
                    Collections.sort(serInfos, new Comparator<SerializationInfo>() {
                        @Override
                        public int compare(SerializationInfo s1, SerializationInfo s2) {
                            return Integer.valueOf(s1.getSerialize().order()).compareTo(
                                    Integer.valueOf(s2.getSerialize().order()));
                        }
                    });
                    return Collections.unmodifiableList(serInfos);
                }
            });

    static <T> Class<? extends T> getImplClass(Class<T> iFace) throws ClassNotFoundException {
    	return Class.forName(iFace.getPackage().getName() + ".impl." + iFace.getSimpleName() + "Impl").asSubclass(iFace);
    }

    static Class<? extends Packet> getPacketInterface(Class<? extends Packet> clazz) {
        // we just go up the inheritance tree and see if anything matches
        Stack<Class<?>> clazzStack = new Stack<Class<?>>();
        clazzStack.push(clazz);
        while (!clazzStack.isEmpty()) {
            Class<?> thisClazz = clazzStack.pop();
            if (thisClazz.getPackage().equals(HandshakePacket.class.getPackage()))
                // MATCHES!
                return thisClazz.asSubclass(Packet.class);
            for (Class<?> iFace : thisClazz.getInterfaces())
                clazzStack.push(iFace);
        }
        throw new RuntimeException("WTF!? I got a " + clazz);
    }

    static List<SerializationInfo> getSerializationInfos(Class<? extends Packet> clazz) {
        return serializationInfoCache.getUnchecked(clazz);
    }

    static String toMethodName(String fieldName) {
        return "get" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
    }

    static Class<?>[] fixupClasses(Class<?>... classes) {
        for (int i = 0; i < classes.length; i++) {
            try {
                // fix anonymous classes
                if (classes[i].isAnonymousClass())
                    classes[i] = classes[i].getSuperclass();
                // primitives to wrappers
                else // try...
                    classes[i] = (Class<?>) classes[i].getField("TYPE").get(null);
            } catch (Exception e) {
            }
        }

        return classes;
    }
}
