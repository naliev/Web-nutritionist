package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import java.util.List;

public interface Storage {
    int size();

    List<Meal> getAll();

    Meal get(int uuid);

    void clear();

    void update(Meal m);

    void save(Meal m);

    void delete(int uuid);
}
