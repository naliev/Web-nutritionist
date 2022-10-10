package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ListStorage extends AbstractStorage<Integer> {
    protected final List<Meal> storage = new ArrayList<>();
    private static final AtomicInteger COUNTER = new AtomicInteger(1);

    public ListStorage(List<Meal> list) {
        for (Meal m: list) {
            doSave(m, 0);
        }
    }

    public int size() {
        return storage.size();
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Meal> doGetAll() {
        return new ArrayList<>(storage);
    }

    @Override
    protected Meal doGet(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doUpdate(Meal value, Integer searchKey) {
        storage.set(searchKey, value);
    }

    @Override
    protected void doSave(Meal value, Integer searchKey) {
        value.setId(COUNTER.getAndIncrement());
        storage.add(value);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected Integer getSearchKey(int uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage.get(i).getId() == uuid) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return (searchKey != null);
    }
}
