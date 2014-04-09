package net.rush.packets.packet.impl;

import net.rush.model.ItemStack;
import net.rush.packets.packet.PlayerBlockPlacementPacket;
import net.rush.packets.serialization.Serialize;
import net.rush.packets.serialization.Type;

public class PlayerBlockPlacementPacketImpl extends AbstractPacket implements PlayerBlockPlacementPacket {
    @Serialize(type = Type.INT, order = 0)
    private final int x;
    @Serialize(type = Type.BYTE, order = 1)
    private final byte y;
    @Serialize(type = Type.INT, order = 2)
    private final int z;
    @Serialize(type = Type.BYTE, order = 3)
    private final byte direction;
    @Serialize(type = Type.ITEM, order = 4)
    private final ItemStack heldItem;
    @Serialize(type = Type.BYTE, order = 5)
    private final byte cursorX;
    @Serialize(type = Type.BYTE, order = 6)
    private final byte cursorY;
    @Serialize(type = Type.BYTE, order = 7)
    private final byte cursorZ;

    public PlayerBlockPlacementPacketImpl(int x, byte y, int z, byte action, ItemStack heldItem, byte cursorX, byte cursorY, byte cursorZ) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
        this.direction = action;
        this.heldItem = heldItem;
        this.cursorX = cursorX;
        this.cursorY = cursorY;
        this.cursorZ = cursorZ;
    }

    @Override
    public int getOpcode() {
        return 0x0F;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public byte getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public byte getDirection() {
        return direction;
    }

    @Override
    public ItemStack getHeldItem() {
        return heldItem;
    }
    
    @Override
    public byte getCursorX() {
        return cursorX;
    }
    
    @Override
    public byte getCursorY() {
        return cursorY;
    }
    
    @Override
    public byte getCursorZ() {
        return cursorZ;
    }

    @Override
    public String getToStringDescription() {
        return String.format("x=\"%d\",y=\"%d\",z=\"%d\",direction=\"%d\",heldItem=\"%s\"",
                x, y, z, direction, heldItem);
    }
}
