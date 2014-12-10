package net.rush.model.entity;

import net.rush.model.RushWorld;
import net.rush.protocol.Packet;
import net.rush.protocol.packets.SpawnPlayer;

public class RushDebugEntity extends RushTrackeableEntity {

	private final String name;
	
	public RushDebugEntity(RushWorld world, String name) {
		super(world);

		this.name = name;
	}

	@Override
	public Packet createSpawnMessage() {		
		return new SpawnPlayer(id, name, position, 0, metadata);
	}
}

