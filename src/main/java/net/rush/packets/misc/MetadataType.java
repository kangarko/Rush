package net.rush.packets.misc;

public enum MetadataType {
    BYTE(0),
    SHORT(1),
    INT(2),
    FLOAT(3),
    STRING(4),
    ITEM(5),
    POSITION(6);

    private static final MetadataType[] by_id;

    private final int id;

    MetadataType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static MetadataType fromId(int id) {
        return by_id[id];
    }

    static {
        int highest = 0;
        for (MetadataType t : MetadataType.values()) {
            if (t.getId() > highest)
                highest = t.getId();
        }
        by_id = new MetadataType[highest + 1];
        for (MetadataType t : MetadataType.values())
            by_id[t.getId()] = t;
    }
}
