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

@Service
@Transactional
public class ToDoService { // 70. Создаем сервис работы с To Do

    private ToDoRepository toDoRepository;

    private UserRepository userRepository;

    @Autowired
    public ToDoService(ToDoRepository toDoRepository, UserRepository userRepository) {
        this.toDoRepository = toDoRepository;
        this.userRepository = userRepository;
    }

    public Optional<ToDoRepr> findById(Long id) { // 71. Метод поиска дел по его ID
        return toDoRepository.findById(id)
                .map(ToDoRepr::new);
    }

    public List<ToDoRepr> findToDoByUser_Username(String username) { // 73. Находим по юзернейму дела
        return toDoRepository.findToDoByUser_Username(username);
    }

    public void save(ToDoRepr toDoRepr) { // 75. Создаем метод сохранения дел
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

    public void delete(Long id) { // 76. Создаем метод удаления дел
        toDoRepository.findById(id)
                .ifPresent(toDo -> toDoRepository.delete(toDo));
    }
}
