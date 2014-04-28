package net.rush.packets.packet;

import net.rush.packets.Packet;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PacketLoginSuccess extends Packet {

	@Serialize(type = Type.STRING, order = 0)
    private String uuid;
	@Serialize(type = Type.STRING, order = 1)
    private String name;

	public PacketLoginSuccess() {}
	
    public PacketLoginSuccess(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

	@Override
	public String getToStringDescription() {
		return "uuid=" + uuid + ",name=" + name;
	}

	@Override
	public int getOpcode() {
		return 2;
	}

}
