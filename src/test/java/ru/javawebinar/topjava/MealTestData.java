package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL1_ID = START_SEQ + 3;
    public static final int NOT_FOUND = 10;

    public static final Meal MEAL1 = new Meal(MEAL1_ID, LocalDateTime.parse("2022-10-20T10:00:00"), "USER1: Breakfast", 500);
    public static final Meal MEAL2 = new Meal(MEAL1_ID + 1, LocalDateTime.parse("2022-10-20T13:00:00"), "USER1: Lunch", 1000);
    public static final Meal MEAL3 = new Meal(MEAL1_ID + 2, LocalDateTime.parse("2022-10-20T20:00:00"), "USER1: Dinner", 500);
    public static final Meal MEAL4 = new Meal(MEAL1_ID + 3, LocalDateTime.parse("2022-10-21T10:00:00"), "USER1: Breakfast", 500);
    public static final Meal MEAL5 = new Meal(MEAL1_ID + 4, LocalDateTime.parse("2022-10-21T13:00:00"), "USER1: Lunch", 1000);
    public static final Meal MEAL6 = new Meal(MEAL1_ID + 5, LocalDateTime.parse("2022-10-21T20:00:00"), "USER1: Dinner", 510);
    public static final Meal MEAL7 = new Meal(MEAL1_ID + 6, LocalDateTime.parse("2022-10-20T14:00:00"), "USER2: Lunch", 510);
    public static final Meal MEAL8 = new Meal(MEAL1_ID + 7, LocalDateTime.parse("2022-10-20T21:00:00"), "USER2: Dinner", 1500);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.MAX, "New meal", 2525);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL1);
        updated.setId(MEAL1_ID);
        updated.setDateTime(LocalDateTime.parse("2077-10-20T10:00:00"));
        updated.setCalories(2525);
        updated.setDescription("Updated meal");
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
