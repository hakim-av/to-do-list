package ru.hakimovav.todolist.persist.repo;

// 28. Создаем интерфейс UserRepository наследуясь от CrudRepository

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.hakimovav.todolist.persist.entity.User;

import java.util.Optional;

// Данный интерфейс связывает класс сущности БД User и POJO класс User
// Наследуемся от CrudRepository и передаем сущность Entity
@Repository
public interface UserRepository extends CrudRepository <User, Long> {

    boolean existsByUsername(String username); // Возможно ошибка и данный метод не нужен
    Optional<User> getUserByUsername(String username); // Получение имени юзера
}
