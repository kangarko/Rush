package net.rush.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.rush.model.entity.RushEntity;

public final class EntityManager implements Iterable<RushEntity> {

	private final HashMap<Integer, RushEntity> entities = new HashMap<>();
	private final HashMap<Class<? extends RushEntity>, Set<? extends RushEntity>> groupedEntities = new HashMap<>();

	private int nextId = 1;

	/**
	 * Gets all entities with the specified type.
	 * @param type The {@link Class} for the type.
	 * @param <T> The type of entity.
	 * @return A collection of entities with the specified type.
	 */
	@SuppressWarnings("unchecked")
	public <T extends RushEntity> Set<T> getAll(Class<T> type) {
		Set<T> set = (Set<T>) groupedEntities.get(type);

		if (set == null) {
			set = new HashSet<T>();
			groupedEntities.put(type, set);
		}
		return set;
	}

	public RushEntity getEntity(int id) {
		return entities.get(id);
	}

	@SuppressWarnings("unchecked")
	public int allocate(RushEntity entity) {
		for (int id = nextId; id < Integer.MAX_VALUE; id++) {
			if (entities.containsKey(id))
				continue;

			entities.put(id, entity);
			entity.id =	 id;

			((Collection<RushEntity>) getAll(entity.getClass())).add(entity);

			nextId = id + 1;

			return id;
		}

		for (int id = Integer.MIN_VALUE; id < -1; id++) {
			if (entities.containsKey(id))
				continue;
			
			entities.put(id, entity);

			((Collection<RushEntity>) getAll(entity.getClass())).add(entity);

			nextId = id + 1;

			return id;
		}

		throw new IllegalStateException("No free entity ids");
	}

	public void deallocate(RushEntity entity) {
		entities.remove(entity.id);
		getAll(entity.getClass()).remove(entity);
	}

	@Override
	public Iterator<RushEntity> iterator() {
		return entities.values().iterator();
	}

}

