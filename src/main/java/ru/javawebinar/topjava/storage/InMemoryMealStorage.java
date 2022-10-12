package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealStorage implements Storage {
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(1);

    public InMemoryMealStorage() {
        for (Meal m : MealsUtil.getTestData()) {
            create(m);
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
        return storage.replace(m.getId(), m) != null ? m : null;
    }

    @Override
    public Meal create(Meal m) {
        m.setId(counter.getAndIncrement());
        storage.put(m.getId(), m);
        return m;
    }

    @Override
    public boolean delete(int id) {
        return storage.remove(id) != null;
    }
}
