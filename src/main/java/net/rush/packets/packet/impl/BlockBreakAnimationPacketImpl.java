package net.rush.packets.packet.impl;

import net.rush.model.Position;
import net.rush.packets.packet.BlockBreakAnimationPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class BlockBreakAnimationPacketImpl extends AbstractPacket implements BlockBreakAnimationPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int entityId;
    @Serialize(type = Type.INT, order = 1)
    private final int x;
    @Serialize(type = Type.INT, order = 2)
    private final int y;
    @Serialize(type = Type.INT, order = 3)
    private final int z;
    @Serialize(type = Type.BYTE, order = 4)
    private final byte stage;

    public BlockBreakAnimationPacketImpl(int entityId, Position pos, byte stage) {
        super();
        this.entityId = entityId;
        this.x = (int)pos.getX();
        this.y = (int)pos.getY();
        this.z = (int)pos.getZ();
        this.stage = stage;
    }

    @Override
    public int getOpcode() {
        return 0x37;
    }

    @Override
    public int getEntityId() {
    	return entityId;
    }
    
    @Override
    public int getX() {
        return x;
    }
    
    @Override
    public int getY() {
        return y;
    }
    
    @Override
    public int getZ() {
        return z;
    }
    
    @Override
    public byte getStage() {
        return stage;
    }
    
    @Override
    public String getToStringDescription() {
        return String.format("entityId=\"%d\",x=\"%d\",y=%d,z=%d,stage=%d", entityId, x, y, z, stage);
    }
}
