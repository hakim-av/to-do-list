package ru.hakimovav.todolist.persist.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.hakimovav.todolist.persist.entity.ToDo;
import ru.hakimovav.todolist.persist.entity.User;
import ru.hakimovav.todolist.repr.ToDoRepr;

import java.util.List;

// Данный интерфейс связывает класс сущности БД To Do и POJO класс To Do
// Наследуемся от CrudRepository и передаем сущность Entity
@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long> {

    // Создаем методы и устанавливаем связь между данным интерфейсом и классом ToDoRepr
    List<ToDoRepr> findToDoByUser_Username(String username);
    List<ToDoRepr> findToDoByUser(User user); // Возможно ошибка и данный метод не нужен
}
