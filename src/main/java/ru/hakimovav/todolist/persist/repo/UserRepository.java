package ru.hakimovav.todolist.persist.repo;

// 28. Создаем интерфейс UserRepository наследуясь от CrudRepository

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.hakimovav.todolist.persist.entity.User;

@Repository
public interface UserRepository extends CrudRepository <User, Long> {
}
