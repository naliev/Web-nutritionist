package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import javax.xml.stream.events.EndDocument;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> list = new ArrayList<>();
        if (meals.size() == 0) {
            return list;
        }
        LocalDateTime previousDay = meals.get(0).getDateTime();
        int currentCalories = 0;
        int startPointer = -1;
        int currentPointer = 0;
        int endPointer = 0;
        while (currentPointer <meals.size()) {
            UserMeal meal = meals.get(currentPointer);
            LocalDateTime curDay = meal.getDateTime();
            if (startPointer == -1 && TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                startPointer = currentPointer;
                endPointer = currentPointer;
            }
            if (!curDay.getDayOfWeek().equals(previousDay.getDayOfWeek())) {
                boolean exceeded = currentCalories >= caloriesPerDay;
                for (int i = startPointer; i < endPointer; i++) {
                    UserMeal userMeal = meals.get(i);
                    list.add(new UserMealWithExcess(curDay, userMeal.getDescription(), userMeal.getCalories(), exceeded));
                }
                startPointer = -1;
                endPointer = -1;
                previousDay = curDay;
                currentCalories = 0;
            } else {
                if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                    endPointer++;
                }
                currentCalories += meal.getCalories();
                currentPointer++;
            }
        }
        boolean exceeded = currentCalories >= caloriesPerDay;
        for (int i = startPointer; i < endPointer; i++) {
            UserMeal userMeal = meals.get(i);
            list.add(new UserMealWithExcess(previousDay, userMeal.getDescription(), userMeal.getCalories(), exceeded));
        }
        return list;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
