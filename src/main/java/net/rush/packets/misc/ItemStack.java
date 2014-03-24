package net.rush.packets.misc;

public class ItemStack extends org.bukkit.inventory.ItemStack {
    public static final ItemStack NULL_ITEMSTACK = new ItemStack((short) -1, (byte) -1, (short) -1, null) {
        @Override
        public short getId() {
            throw new NullPointerException("You tried to use the NULL-ItemStack!");
        }

        @Override
        public byte getStackSize() {
            throw new NullPointerException("You tried to use the NULL-ItemStack!");
        }

        @Override
        public short getDataValue() {
            throw new NullPointerException("You tried to use the NULL-ItemStack!");
        }

        @Override
        public byte[] getMetadata() {
            throw new NullPointerException("You tried to use the NULL-ItemStack!");
        }
    };

    private final short id;
    private final byte stackSize;
    private final short dataValue;
    private final byte[] metadata;

    public ItemStack(int id, int stackSize, int dataValue) {
        this((short)id, (byte)stackSize, (short)dataValue, new byte[-1]);
    }
    
    public ItemStack(short id, byte stackSize, short dataValue) {
        this(id, stackSize, dataValue, new byte[-1]);
    }

    public ItemStack(short id, byte stackSize, short dataValue, byte[] metadata) {
        this.id = id;
        this.stackSize = stackSize;
        this.dataValue = dataValue;
        this.metadata = metadata;
    }

    public short getId() {
        return id;
    }

    public byte getStackSize() {
        return stackSize;
    }

    public short getDataValue() {
        return dataValue;
    }

    public byte[] getMetadata() {
        return metadata;
    }

    @Override
    public String toString() {
        return String.format("ItemStack [id=%s, stackSize=%s, dataValue=%s, metadata=byte[%d]]", id,
                stackSize, dataValue, ((metadata != null) ? metadata.length : -1));
    }
}
