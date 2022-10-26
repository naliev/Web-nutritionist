package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL1_ID = START_SEQ + 3;
    public static final int NOT_FOUND = 10;

    public static final Meal userMeal1 = new Meal(USER_MEAL1_ID, LocalDateTime.of(2022, 10, 20, 10, 0), "USER1: Breakfast", 500);
    public static final Meal userMeal2 = new Meal(USER_MEAL1_ID + 1, LocalDateTime.of(2022, 10, 20, 13, 0), "USER1: Lunch", 1000);
    public static final Meal userMeal3 = new Meal(USER_MEAL1_ID + 2, LocalDateTime.of(2022, 10, 20, 20, 0), "USER1: Dinner", 500);
    public static final Meal userMeal4 = new Meal(USER_MEAL1_ID + 3, LocalDateTime.of(2022, 10, 21, 10, 0), "USER1: Breakfast", 500);
    public static final Meal userMeal5 = new Meal(USER_MEAL1_ID + 4, LocalDateTime.of(2022, 10, 21, 13, 0), "USER1: Lunch", 1000);
    public static final Meal userMeal6 = new Meal(USER_MEAL1_ID + 5, LocalDateTime.of(2022, 10, 21, 20, 0), "USER1: Dinner", 510);
    public static final Meal adminMeal1 = new Meal(USER_MEAL1_ID + 6, LocalDateTime.of(2022, 10, 20, 14, 0), "USER2: Lunch", 510);
    public static final Meal adminMeal2 = new Meal(USER_MEAL1_ID + 7, LocalDateTime.of(2022, 10, 20, 21, 0), "USER2: Dinner", 1500);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.MAX, "New meal", 2525);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMeal1);
        updated.setId(USER_MEAL1_ID);
        updated.setDateTime(LocalDateTime.of(2077, 10, 20, 10, 0));
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
