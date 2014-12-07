package net.rush.protocol.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rush.api.Difficulty;
import net.rush.api.Environment;
import net.rush.api.GameMode;
import net.rush.protocol.Packet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class JoinGame extends Packet {

	private int entityId;
	private String worldType;
	private GameMode gameMode;
	private Environment environment;
	private Difficulty difficulty;
	private int worldHeight;
	private int maxPlayers;
	private boolean hardcore = false;

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeInt(entityId);
		int gamemode = gameMode.getValue();

		if (hardcore)
			gamemode |= 8;

		out.writeByte(gamemode);

		out.writeByte(environment.getId());
		out.writeByte(difficulty.getValue());
		out.writeByte(maxPlayers);
		writeString(worldType == null ? "" : worldType, out);
	}
}
