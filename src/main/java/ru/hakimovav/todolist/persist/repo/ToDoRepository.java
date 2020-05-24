package ru.hakimovav.todolist.persist.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.hakimovav.todolist.persist.entity.ToDo;
import ru.hakimovav.todolist.persist.entity.User;
import ru.hakimovav.todolist.repr.ToDoRepr;

import java.util.List;

// 69. Создаем интерфейс работы с репозиторием To Do

@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long> {

    List<ToDoRepr> findToDoByUser_Username(String username); // 74. Извлекаем информацию из списка дел по имени

    List<ToDoRepr> findToDoByUser(User user);
}
