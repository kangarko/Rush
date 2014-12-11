package net.rush.model.entity;

import net.rush.model.RushWorld;
import net.rush.protocol.Packet;
import net.rush.protocol.packets.SpawnPlayer;

public class RushFakePlayer extends RushTrackeableEntity {

	private final String name;
	
	public RushFakePlayer(RushWorld world, String name) {
		super(world);

		this.name = name;
	}

	@Override
	public Packet createSpawnMessage() {		
		return new SpawnPlayer(id, name, position, 0, metadata);
	}
}

