package ru.hakimovav.todolist.repr;

import org.springframework.format.annotation.DateTimeFormat;
import ru.hakimovav.todolist.persist.entity.ToDo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

// 11. POJO класс для представления дел во View To Do

public class ToDoRepr {

    private Long id;
    @NotEmpty
    private String description;
    private String username;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Формат даты
    private LocalDate targetDate;

    public ToDoRepr() { // Пустой конструктор, чтобы не было ошибки
    }

    public ToDoRepr(ToDo toDo) { // 72. Конструктор возвращающий имеющиеся значения
        this.id = toDo.getId();
        this.description = toDo.getDescription();
        this.targetDate = toDo.getTargetDate();
        this.username = toDo.getUser().getUsername();
    }

    // Конструктор передающий значения
    public ToDoRepr(Long id, @NotEmpty String description, String username, @NotNull LocalDate targetDate) {
        this.id = id;
        this.description = description;
        this.username = username;
        this.targetDate = targetDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }
}
