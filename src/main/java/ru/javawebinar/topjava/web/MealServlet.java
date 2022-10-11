package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.InMemoryMealStorage;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final InMemoryMealStorage storage = new InMemoryMealStorage();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meal servlet - GET");
        String action = request.getParameter("action");
        if (action == null) {
            forwardToMealsList(request, response);
            return;
        }
        Meal m;
        int id;
        switch (action) {
            case "add":
                log.debug("adding new meal");
                m = MealsUtil.EMPTY;
                break;
            case "delete":
                id = Integer.parseInt(request.getParameter("id"));
                log.debug(String.format("deleting meal with %d id", id));
                if (storage.delete(id)) {
                    log.debug(String.format("meal successfully deleted with %d id", id));
                } else {
                    log.debug(String.format("meal was not deleted with %d id", id));
                }
                response.sendRedirect("meals");
                return;
            case "update":
                id = Integer.parseInt(request.getParameter("id"));
                log.debug(String.format("updating meal with %d id", id));
                m = storage.get(id);
                break;
            default:
                forwardToMealsList(request, response);
                return;
        }
        request.setAttribute("meal", m);
        request.getRequestDispatcher("meal.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("forward to meal servlet - POST");
        LocalDateTime dateTime = LocalDateTime.now();
        try {
            dateTime = TimeUtil.getDateFromString(request.getParameter("dateTime"));
        } catch (DateTimeParseException e) {
            log.error(String.format("Cannot parse date from string %S", request.getParameter("dateTime")));
        }
        Meal m = new Meal(dateTime,
                request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        String id = request.getParameter("id");
        if (id.isEmpty()) {
            storage.create(m);
            log.debug(String.format("New meal successfully saved with %S id", m.getId()));
        } else {
            m.setId(Integer.parseInt(id));
            storage.update(m);
            log.debug(String.format("Meal successfully updated with %S id", m.getId()));
        }
        inputFilteredMealsListIntoRequestAttribute(request);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    private void forwardToMealsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("no action was sent - show all meals");
        inputFilteredMealsListIntoRequestAttribute(request);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    private void inputFilteredMealsListIntoRequestAttribute(HttpServletRequest request) {
        request.setAttribute("meals", MealsUtil.filteredByStreams(storage.getAll(),
                LocalTime.of(0, 0), LocalTime.of(23, 59), MealsUtil.CALORIES_PER_DAY));
    }
}
