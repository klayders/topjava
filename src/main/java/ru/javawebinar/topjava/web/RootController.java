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
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private MealService mealService;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @PostMapping("/users")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        SecurityUtil.setAuthUserId(userId);
        return "redirect:meals";
    }

    @GetMapping("/meals")
    public String meals(Model model) {
        log.info("getAll for user {} ROOTCONTROLLER priveeet");
        model.addAttribute("meals",
                MealsUtil.getWithExceeded(mealService.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }
}
