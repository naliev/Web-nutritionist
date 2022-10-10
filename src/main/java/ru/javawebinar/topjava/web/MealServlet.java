package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.ListStorage;
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
    private static final ListStorage storage = new ListStorage(MealsUtil.getTestData());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("meals", MealsUtil.filteredByStreams(storage.getAll(),
                    LocalTime.of(7, 0), LocalTime.of(23, 0), MealsUtil.CALORIES_PER_DAY));
            request.getRequestDispatcher("meals.jsp").forward(request, response);
            return;
        }
        Meal m;
        int id;
        switch (action) {
            case "add":
                m = Meal.EMPTY;
                break;
            case "delete":
                id = Integer.parseInt(request.getParameter("id"));
                storage.delete(id);
                request.setAttribute("meals", MealsUtil.filteredByStreams(storage.getAll(),
                        LocalTime.of(7, 0), LocalTime.of(23, 0), MealsUtil.CALORIES_PER_DAY));
                request.getRequestDispatcher("meals.jsp").forward(request, response);
                return;
            case "update":
                id = Integer.parseInt(request.getParameter("id"));
                m = storage.get(id);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("meal", m);
        request.getRequestDispatcher("meal.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.MIN;
        try {
            dateTime = TimeUtil.getDateFromString(request.getParameter("dateTime"));
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
        Meal m = new Meal(dateTime,
                request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        String id = request.getParameter("id");
        if (id == null || id.isEmpty() || id.equals("0")) {
            storage.save(m);
        } else {
            m.setId(Integer.parseInt(id));
            storage.update(m);
        }
        request.setAttribute("meals", MealsUtil.filteredByStreams(storage.getAll(),
                LocalTime.of(7, 0), LocalTime.of(23, 0), MealsUtil.CALORIES_PER_DAY));
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
