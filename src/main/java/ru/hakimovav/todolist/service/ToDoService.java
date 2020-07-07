package ru.hakimovav.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hakimovav.todolist.persist.entity.ToDo;
import ru.hakimovav.todolist.persist.repo.ToDoRepository;
import ru.hakimovav.todolist.persist.repo.UserRepository;
import ru.hakimovav.todolist.repr.ToDoRepr;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static ru.hakimovav.todolist.security.Utils.getCurrentUser;

// Здесь реализована вся логика работы с Ту Ду объектами передаваемая через запросы в Controller
@Service
@Transactional
public class ToDoService {

    // Внедряем бины из интерфейсов
    private ToDoRepository toDoRepository;
    private UserRepository userRepository;
    @Autowired
    public ToDoService(ToDoRepository toDoRepository, UserRepository userRepository) {
        this.toDoRepository = toDoRepository;
        this.userRepository = userRepository;
    }

    // Реализуем логику передачи дела по id дела (метод findById берется из CrudRepository)
    public Optional<ToDoRepr> findById(Long id) {
        return toDoRepository.findById(id)
                .map(ToDoRepr::new);
    }

    // Реализуем логику передачи дела по Username (метод findToDoByUser_Username берется из toDoRepository)
    public List<ToDoRepr> findToDoByUser_Username(String username) {
        return toDoRepository.findToDoByUser_Username(username);
    }

    // Реализуем логику создания и сохранения дел (метод getCurrentUser берется из Utils)
    // Метод createTodoPost из контроллера переходит в метод save
    public void save(ToDoRepr toDoRepr) {
        getCurrentUser()
                .flatMap(userRepository::getUserByUsername)
                .ifPresent(user -> {
                    ToDo toDo = new ToDo();
                    toDo.setId(toDoRepr.getId());
                    toDo.setDescription(toDoRepr.getDescription());
                    toDo.setTargetDate(toDoRepr.getTargetDate());
                    toDo.setUser(user);
                    toDoRepository.save(toDo);
                });
    }

    // Реализуем логику удаления дел (метод findById берется из CrudRepository)
    public void delete(Long id) {
        toDoRepository.findById(id)
                .ifPresent(toDo -> toDoRepository.delete(toDo));
    }
}
