package ru.hakimovav.todolist.repr;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

// 11. Прописываем все заголовки таблицы, добавляем пустой конструктор и геттеры/сеттеры для всех

public class ToDoRepr {

    private Long id;

    @NotEmpty // Чтобы не было пустой
    private String description;

    private String username;

    @NotNull // Чтобы не было нуля
    @DateTimeFormat(pattern = "dd-MM-yyyy") // Формат даты
    private LocalDate targetDate;

    public ToDoRepr() {
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
