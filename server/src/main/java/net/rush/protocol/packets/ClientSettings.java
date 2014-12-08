package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.protocol.Packet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ClientSettings extends Packet {

	private String locale;
	private byte viewDistance;
	
	private byte chatFlags;	
	private byte difficulty;
	
	private boolean showCape;
	public boolean chatColours;

	@Override
	public void read(ByteBuf in) throws IOException {
		locale = readString(in);
		viewDistance = in.readByte();
		
		chatFlags = in.readByte();
		chatColours = in.readBoolean();
		
		difficulty = in.readByte();
		showCape = in.readBoolean();
	}
}
