package net.rush.packets.misc;

public interface EntityMetadata<T> {
    T getValue();
    MetadataType getType();
}
