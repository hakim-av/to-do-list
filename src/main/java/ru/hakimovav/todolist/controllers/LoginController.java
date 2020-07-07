package ru.hakimovav.todolist.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.hakimovav.todolist.repr.UserRepr;
import ru.hakimovav.todolist.service.UserService;

import javax.validation.Valid;

@Controller
public class LoginController { // Реализуем контроллер для регистрации и входа в приложение

    // Прописываем логирование
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    // Внедряем бин в котором прописана логика регистрации юзера
    private final UserService userService;
    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login") // Ловим запрос на получение страницы по URI /login и возвращаем ее
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) { // Ловим запрос на получение страницы по URI /register и возвращаем ее
        model.addAttribute("user", new UserRepr()); // Добавление нового пользователя класса UserRepr.java
        return "register";
    }

    @PostMapping("/register") // Ловим запрос на отправку данных по URI /register
    public String registerNewUser(
            @ModelAttribute("user")
            @Valid UserRepr userRepr, // 17. Прописываем данные юзера
            BindingResult result) { // 22. Прописываем проверку на валидацию
                logger.info("New user {}", userRepr); // 20. Прописываем сам вывод информации о пользователе
                if (result.hasErrors()) { // 23. Прописываем возможные ошибки
                    return "register";
                }
                if (!userRepr.getPassword().equals(userRepr.getMatchingPassword())) { // 24. Прописываем некорректный пароль
                    result.rejectValue("password","", "Пароли не совпадают");
                    return "register";
                }
                userService.create(userRepr);
                return "redirect:/login";
            }
}
