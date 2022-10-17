package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;

    public MealRestController(MealService mealService) {
        service = mealService;
    }

    public Meal create(Meal meal) {
        int authUserId = authUserId();
        log.info("save meal {} for user {}", meal, authUserId);
        checkNew(meal);
        return service.create(meal, authUserId);
    }

    public void update(Meal meal, int id) {
        int authUserId = authUserId();
        log.info("update {} with id={} for user {}", meal, id, authUserId);
        assureIdConsistent(meal, id);
        service.update(meal, authUserId);
    }

    public void delete(int id) {
        int authUserId = authUserId();
        log.info("delete meal {} for user {}", id, authUserId);
        service.delete(id, authUserId);
    }

    public Meal get(int id) {
        int authUserId = authUserId();
        log.info("get meal {} for user {}", id, authUserId);
        return service.get(id, authUserId);
    }

    public List<MealTo> getAll() {
        int authUserId = authUserId();
        log.info("get all meals without filter for user {}", authUserId);
        return MealsUtil.getTos(service.getAll(authUserId), authUserCaloriesPerDay());
    }

    public List<MealTo> getAllFiltered(String startDate, String startTime, String endDate, String endTime) {
        int authUserId = authUserId();
        log.info("get all with filter for user {}", authUserId);
        return MealsUtil.getFilteredTos(service.getAllFiltered(authUserId,
                        startDate.isEmpty() ? LocalDate.MIN : LocalDate.parse(startDate),
                        endDate.isEmpty() ? LocalDate.MAX : LocalDate.parse(endDate)
                ), authUserCaloriesPerDay(),
                startTime.isEmpty() ? LocalTime.MIN : LocalTime.parse(startTime),
                endTime.isEmpty() ? LocalTime.MAX : LocalTime.parse(endTime));
    }
}