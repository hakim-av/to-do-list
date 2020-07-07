package ru.hakimovav.todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.hakimovav.todolist.repr.ToDoRepr;
import ru.hakimovav.todolist.service.ToDoService;
import ru.hakimovav.todolist.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static ru.hakimovav.todolist.security.Utils.getCurrentUser;

@Controller
public class TodoController { // Реализуем контроллер для списка дел

    // Внедряем бины, через которых будем работать с юзерами и их делами
    private ToDoService toDoService;
    private UserService userService;
    @Autowired
    public TodoController(ToDoService toDoService, UserService userService) {
        this.toDoService = toDoService;
        this.userService = userService;
    }
    @GetMapping("") // Ловим запрос на получение главной страницы по URI:""
    public String mainPage() {
        return "redirect:/todo/all"; // Переадресовываем запрос на URI:"/to do/all"
    }

    @GetMapping("/todo/all") // Ловим запрос на URI:"/to do/all"
    public String allTodosPage(Model model) {
        List<ToDoRepr> todos = getCurrentUser() // Проверяем на авторизацию
                .map(toDoService::findToDoByUser_Username) // Вытаскиваем дела конкретного юзера
                .orElseThrow(IllegalStateException::new);
        model.addAttribute("todos", todos);
        return "todoList"; // Страница со всем списком дел для юзера
    }

    @GetMapping("/todo/{id}") // Ловим запрос на страницу одного конкретного todoPage
    public String todoPage(
            @PathVariable("id") Long id,
            Model model) {
        ToDoRepr toDoRepr = toDoService.findById(id) // Передаем на страницу конкретно одно дело
                .orElseThrow(ResourceNotFoundException::new);
        model.addAttribute("todo", toDoRepr); // Добавляем на HTML страницу данные
        return "todo"; // Страница самого совпадает со страницей создания дела, просто атрибут "to do" в HTML меняется
    }

    @GetMapping("/todo/create") // Ловим запрос на получение страницы создания нового дела по URI:"/to do/create"
    public String createTodoPage(Model model) {
        model.addAttribute("todo", new ToDoRepr());
        return "todo"; // Страница создания совпадает со страницей самого дела, просто атрибут "to do" в HTML меняется
    }

    @PostMapping("/todo/create")  // Ловим запрос на само создание нового дела по URI:"/to do/create"
    public String createTodoPost(
            @ModelAttribute("todo")
            @Valid ToDoRepr toDoRepr,
            BindingResult result) { // BindingResult result проверка на валидность
                if (result.hasErrors()) { // Если возникла ошибка возвращаем страницу создания
                    return "todo";
                }
                toDoService.save(toDoRepr); // Сохраняем это дело в репозитории и обновляем страницу
                return "redirect:/";
            }

    @GetMapping("/todo/delete/{id}") // Ловим запрос на удаление дела
    public String deleteTodo(@PathVariable Long id) {
        toDoService.delete(id);
        return "redirect:/";
    }
}