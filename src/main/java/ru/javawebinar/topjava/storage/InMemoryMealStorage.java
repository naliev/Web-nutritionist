package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealStorage implements Storage {
    protected final Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private static final AtomicInteger COUNTER = new AtomicInteger(1);

    public InMemoryMealStorage() {
        for (Meal m : MealsUtil.getTestData()) {
            m.setId(COUNTER.getAndIncrement());
            storage.put(m.getId(), m);
        }
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Meal get(int id) {
        return storage.get(id);
    }

    @Override
    public Meal update(Meal m) {
        storage.replace(m.getId(), m);
        return m;
    }

    @Override
    public Meal create(Meal m) {
        m.setId(COUNTER.getAndIncrement());
        storage.put(m.getId(), m);
        return m;
    }

    @Override
    public boolean delete(int id) {
        if (storage.containsKey(id)) {
            storage.remove(id);
            return true;
        } else {
            return false;
        }
    }

}
