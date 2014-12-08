package net.rush.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import net.rush.model.entity.RushEntity;

import org.apache.commons.lang3.Validate;

@SuppressWarnings("unchecked")
public final class EntityManager implements Iterable<RushEntity> {

	private final HashMap<Integer, RushEntity> entities = new HashMap<>();
	private final HashMap<Class<RushEntity>, HashSet<RushEntity>> entityGroups = new HashMap<>();

	private int nextId = 1;

	public RushEntity getEntity(int id) {
		return entities.get(id);
	}

	public <T extends RushEntity> HashSet<T> getAll(Class<T> type) {
		if (!entityGroups.containsKey(type))
			entityGroups.put((Class<RushEntity>) type, new HashSet<>());
			
		return (HashSet<T>) entityGroups.get(type);
	}

	public void allocate(RushEntity entity) {
		for (int i = nextId; i < Integer.MAX_VALUE; i++) {
			if (entities.containsKey(entity.id))
				continue;
			
			System.out.println("Allocated new entity " + entity + " id " + entity.id);

			entity.id = nextId++;

			entities.put(entity.id, entity);
			((HashSet<RushEntity>) getAll(entity.getClass())).add(entity);			
			break;
		}
	}

	public void deallocate(RushEntity entity) {
		Validate.isTrue(entities.containsKey(entity.id), "Error deallocating entity id " + entity.id + " (does not exist in the map)");

		entities.remove(entity);
		getAll(entity.getClass()).remove(entity);
	}

	@Override
	public Iterator<RushEntity> iterator() {
		return entities.values().iterator();
	}
}

