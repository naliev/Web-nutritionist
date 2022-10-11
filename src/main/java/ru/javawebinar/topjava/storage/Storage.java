package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {

    List<Meal> getAll();

    Meal get(int id);

    Meal update(Meal m);

    Meal create(Meal m);

    boolean delete(int id);
}
