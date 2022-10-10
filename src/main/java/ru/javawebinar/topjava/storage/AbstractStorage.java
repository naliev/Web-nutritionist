package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {
    public Meal get(int uuid) {
        SK searchKey = getExistedSearchKey(uuid);
        return doGet(searchKey);
    }

    public void update(Meal r) {
        SK searchKey = getExistedSearchKey(r.getId());
        doUpdate(r, searchKey);
    }

    public void save(Meal r) {
        SK searchKey = getNotExistedSearchKey(r.getId());
        doSave(r, searchKey);
    }

    public void delete(int uuid) {
        SK searchKey = getExistedSearchKey(uuid);
        doDelete(searchKey);
    }

    private SK getExistedSearchKey(int uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new IllegalArgumentException(String.valueOf(uuid));
        }
        return searchKey;
    }

    private SK getNotExistedSearchKey(int uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new IllegalArgumentException(String.valueOf(uuid));
        }
        return searchKey;
    }

    protected abstract List<Meal> doGetAll();

    protected abstract Meal doGet(SK searchKey);

    protected abstract void doUpdate(Meal value, SK searchKey);

    protected abstract void doSave(Meal value, SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract SK getSearchKey(int uuid);

    protected abstract boolean isExist(SK searchKey);
}
