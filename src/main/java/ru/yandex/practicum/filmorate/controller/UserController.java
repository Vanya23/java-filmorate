package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.UserService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
public class UserController {
    private int counterId = 1;
    private final HashMap<Integer, User> users = new HashMap<Integer, User>();



    @GetMapping("/users")
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping(value = "/users")
    public User create(@RequestBody User user) throws ValidationException {
        fullValidUser(user);
        user.setId(counterId++);
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                "post", "/user", user);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping(value = "/users")
    public User update(@RequestBody User user) throws ValidationException {
        fullValidUser(user);
        checkUnknownUser(user);
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                "put", "/user", user);
        users.put(user.getId(), user);
        return user;
    }

    private void fullValidUser(User user) throws ValidationException {
        if (!UserService.validUser(user)) {
            log.warn("Получен запрос к эндпоинту: '{} {}', Выброшено исключение: '{}',  Причина: '{}', Строка параметров запроса: '{}'",
                    "post", "/users", ValidationException.class.getName(), "Данные пользователя некорректные",user);
            throw new ValidationException();
        }
    }

    private void checkUnknownUser(User user) throws ValidationException {
        if (!users.containsKey(user.getId())) {
            log.warn("Получен запрос к эндпоинту: '{} {}', Выброшено исключение: '{}',  Причина: '{}', Строка параметров запроса: '{}'",
                    "put", "/users", ValidationException.class.getName(), "Неизвестный пользователь",user);
            throw new ValidationException();
        }
    }


}
