package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

@RequestMapping("/meals")

@Controller
public class JspMealController extends AbstractMealController {

    @GetMapping("create")
    public String create(Model model) {
        Meal meal = new Meal(LocalDateTime.now(), "", 1000);
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
        int id = getParsedId(request);
        if (id == 0) {
            super.create(m);
        } else {
            super.update(m, id);
        }
        return "redirect:/meals";
    }

    private int getParsedId(HttpServletRequest request) {
        String idStr = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(idStr);
    }
}
