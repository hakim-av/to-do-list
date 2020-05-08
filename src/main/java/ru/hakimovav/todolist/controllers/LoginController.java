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


/*
14. Реализуем отправку данных при нажатии на регистрацию в базу данных
21. После создания возможности регистрации пользователей, кооректируем шаблон добавляя действие-регистрацию
25. На страницах html прописываем ошибки, некорректный пароль
26. Подсоединяем базу данных, добавляя разрешения в application.properties. Затем создаем пакет Persist и внутри него
пакеты entity и repo. В пакете entity добавляем класс User в который прописываем генерацию данных для базы данных
28. Создаем Пакет Service и класс UserService в нем, он будет ответственен за преобразования
33. Создаем UserService на этой странице и создадим для него конструктор
34. Добавляем userService.create(userRepr); перед return "redirect://login";
 */

@Controller
public class LoginController { // 3. Регистрация

    // 19. Прописываем логирование
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() { // 4. Переход на страницу логирования
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) { // 5. Переход на страницу регистрации
        model.addAttribute("user", new UserRepr()); // 16. Добавление нового пользователя класса UserRepr.java
        return "register";
    }

    @PostMapping("/register") // 15. Метод регистрация нового пользователя
    public String registerNewUser(@ModelAttribute("user") @Valid UserRepr userRepr, // 17. Прописываем данные юзера
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
