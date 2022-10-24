package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(created, service.get(newId, USER_ID));
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, userMeal1.getDateTime(), "Duplicated", userMeal1.getCalories()), USER_ID));
        service.create(new Meal(null, userMeal1.getDateTime(), "Duplicated", userMeal1.getCalories()), ADMIN_ID);
    }

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL1_ID, USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(MealTestData.NOT_FOUND, ADMIN_ID));
        assertThrows(NotFoundException.class, () -> service.get(MealTestData.NOT_FOUND, USER_ID));
    }

    @Test
    public void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL1_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(MealTestData.NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_MEAL1_ID, USER_ID), getUpdated());
    }

    @Test
    public void updateNotOwn() {
        Meal userUpdated = MealTestData.getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(userUpdated, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(
                userMeal1.getDate(), userMeal3.getDate(), USER_ID), userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void getBetweenInclusiveFromNullToNull() {
        assertMatch(service.getBetweenInclusive(
                null, null, USER_ID), userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void getAllAdminMeals() {
        assertMatch(service.getAll(ADMIN_ID), adminMeal2, adminMeal1);
    }

}