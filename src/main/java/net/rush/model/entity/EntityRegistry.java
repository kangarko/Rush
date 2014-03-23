package net.rush.model.entity;

import java.util.HashMap;
import java.util.Map;

import net.rush.model.LivingEntity;

import org.bukkit.entity.EntityType;

public class EntityRegistry {
	
	private static final Map<EntityType, Class<? extends LivingEntity>> entities = new HashMap<EntityType, Class<? extends LivingEntity>>();
	
	static {
		registerEntity(EntityType.PIG, Pig.class);
	}
	
	public static Class<? extends LivingEntity> entityLookup(EntityType type) {
        Class<? extends LivingEntity> clazz = entities.get(type);
        if (clazz == null)
            throw new NullPointerException("Unknown entity " + type.toString() + "! Is is implemented?");
        return clazz;
    }

    public static void registerEntity(EntityType type, Class<? extends LivingEntity> clazz) {
        entities.put(type, clazz);
    }
}
