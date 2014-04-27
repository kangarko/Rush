package net.rush.packets.serialization;

import static net.rush.packets.serialization.SerializationHelper.getSerializationInfos;
import static net.rush.packets.serialization.SerializationHelper.toMethodName;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ListIterator;

import net.rush.packets.Packet;

import org.jboss.netty.buffer.ChannelBufferOutputStream;

public class SerializationPacketSender<T extends Packet> {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void send(ChannelBufferOutputStream stream, T packet) {
		try {
			Class<? extends Packet> iFace = packet.getPacketType();
			List<SerializationInfo> serInfos = getSerializationInfos(iFace);
			/*
			 * dirty: we have to have "getProperty()" to get "property"
			 */
			ListIterator<SerializationInfo> iterator = serInfos.listIterator();
			while (iterator.hasNext()) {
				SerializationInfo now = iterator.next();
				Method getter = iFace.getMethod(toMethodName(now.getName()));
				Serializor serializor = now.getSerialize().type().getSerializor();
				serializor.write(stream, getter.invoke(packet));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
