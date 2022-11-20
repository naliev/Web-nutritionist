package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {
    private final Logger log = LoggerFactory.getLogger(RootController.class);
    private final UserService userService;
    private final MealService mealService;

    @Autowired
    public RootController(UserService userService, MealService mealService) {
        this.userService = userService;
        this.mealService = mealService;
    }

    @GetMapping("/")
    public String root() {
        log.info("root");
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        log.info("users");
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @PostMapping("/users")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        log.info("setUser {}", userId);
        SecurityUtil.setAuthUserId(userId);
        return "redirect:meals";
    }

    @GetMapping("/meals")
    public final String getAllMealForUser(Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("get meals for user {}", userId);
        model.addAttribute("meals", MealsUtil.getTos(mealService.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }
}
