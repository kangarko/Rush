package net.rush.entity;

import java.util.Iterator;

import org.apache.commons.lang3.Validate;

import net.rush.api.safety.SafeMapa;
import net.rush.api.safety.SafeUnorderedZoznam;

@SuppressWarnings("unchecked")
public class EntityManager implements Iterable<Entity> {

	private final SafeMapa<Integer, Entity> entities = new SafeMapa<>();
	private final SafeMapa<Class<Entity>, SafeUnorderedZoznam<? extends Entity>> entityGroups = new SafeMapa<>();

	private int nextId = 1;

	public void pulse() {
		for (Entity entity : entities.values())
			entity.pulse();

		for (Entity entity : entities.values())
			entity.reset();
	}
	
	void allocate(Entity entity) {
		for (int i = nextId; i < Integer.MAX_VALUE; i++) {
			if (entities.containsKey(entity.getId()))
				continue;

			entity.assignId(nextId++);
			entities.put(entity.getId(), entity);
			((SafeUnorderedZoznam<Entity>) getAll(entity.getClass())).add(entity);
			
			System.out.println("Allocated new entity " + entity);
			break;
		}
	}

	void deallocate(Entity entity) {		
		Validate.isTrue(entities.containsKey(entity.getId()), "Error deallocating " + entity + " (does not exist in the map)");

		entities.remove(entity.getId());		
		SafeUnorderedZoznam<Entity> list = (SafeUnorderedZoznam<Entity>) getAll(entity.getClass());
		list.remove(entity);
		
		System.out.println("Entity " + entity + " de-allocated");
	}

	public <T extends Entity> SafeUnorderedZoznam<T> getAll(Class<T> type) {
		if (!entityGroups.containsKey((Class<Entity>) type))
			entityGroups.put((Class<Entity>) type, new SafeUnorderedZoznam<>());
			
		return (SafeUnorderedZoznam<T>) entityGroups.get((Class<Entity>) type);
	}
	
	public Entity getEntity(int id) {
		return entities.get(id);
	}
	
	@Override
	public Iterator<Entity> iterator() {
		return entities.values().iterator();
	}
}

