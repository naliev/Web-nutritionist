package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.InMemoryMealStorage;
import ru.javawebinar.topjava.storage.Storage;
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
    private final Storage storage = new InMemoryMealStorage();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meal servlet - GET");
        String action = request.getParameter("action");
        switch (action != null ? action : "show all") {
            case "add":
                log.debug("adding new meal");
                request.setAttribute("meal", MealsUtil.EMPTY);
                break;
            case "delete":
                int id = getIdFromRequest(request);
                log.debug("deleting meal with {} id", id);
                if (storage.delete(id)) {
                    log.debug("meal successfully deleted with {} id", id);
                } else {
                    log.debug("meal was not deleted with {} id", id);
                }
                response.sendRedirect("meals");
                return;
            case "update":
                id = getIdFromRequest(request);
                log.debug("updating meal with {} id", id);
                request.setAttribute("meal", storage.get(id));
                break;
            default:
                forwardToMealsList(request, response);
                return;
        }
        request.getRequestDispatcher("meal.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("forward to meal servlet - POST");
        LocalDateTime dateTime;
        try {
            dateTime = TimeUtil.getDateFromString(request.getParameter("dateTime"));
        } catch (DateTimeParseException e) {
            log.error("Cannot parse date from string {}", request.getParameter("dateTime"));
            throw e;
        }
        Meal m = new Meal(dateTime,
                request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        String id = request.getParameter("id");
        if (id.isEmpty()) {
            storage.create(m);
            log.debug("New meal successfully saved with {} id", m.getId());
        } else {
            m.setId(Integer.parseInt(id));
            if (storage.update(m) != null) {
                log.debug("Meal successfully updated with {} id", m.getId());
            } else {
                log.error("Meal cannot be updated with {} id, no such meal in the storage", m.getId());
            }

        }
        response.sendRedirect("meals");
    }

    private int getIdFromRequest(HttpServletRequest r) {
        return Integer.parseInt(r.getParameter("id"));
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
