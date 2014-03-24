package net.rush.packets.misc;

public class GenericMetadata<T> implements EntityMetadata<T> {
    private final T val;
    private final MetadataType type;

    public GenericMetadata(T val, MetadataType type) {
        this.val = val;
        this.type = type;
    }

    @Override
    public T getValue() {
        return val;
    }

    @Override
    public MetadataType getType() {
        return type;
    }
}
