package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@RequestMapping("/meals")

@Controller
public class JspMealController extends AbstractMealController {

    @GetMapping("create")
    public String create(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        int id = getParsedId(request);
        Meal meal = super.get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        super.delete(getParsedId(request));
        return "redirect:/meals";
    }

    @PostMapping
    public String save(HttpServletRequest request) {
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        Meal m = new Meal(LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        String id = request.getParameter("id");
        if (id.isEmpty()) {
            super.create(m);
        } else {
            super.update(m, Integer.parseInt(id));
        }
        return "redirect:/meals";
    }

    @GetMapping("/filter")
    public final String getAllMealForUserBetweenHalfOpen(Model model, HttpServletRequest request) {
        int userId = SecurityUtil.authUserId();
        LocalDate fromDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate toDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime fromTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime toTime = parseLocalTime(request.getParameter("endTime"));
        log.info("get meals for user {} between {} {} and {} {}", userId, fromDate, toDate, fromTime, toTime);
        model.addAttribute("meals", MealsUtil.getFilteredTos(super.service.getBetweenInclusive(fromDate, toDate, userId), SecurityUtil.authUserCaloriesPerDay(), fromTime, toTime));
        return "meals";
    }

    private int getParsedId(HttpServletRequest request) {
        String idStr = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(idStr);
    }
}
