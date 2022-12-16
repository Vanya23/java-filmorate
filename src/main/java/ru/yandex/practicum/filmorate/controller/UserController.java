package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
public class UserController {
    private int counterId = 1;
    private final HashMap<Integer, User> users = new HashMap<Integer, User>();

    private static final String EMAIL_STRING = "@";

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

    //Проверьте данные, которые приходят в запросе на добавление нового фильма или пользователя.
    // Эти данные должны соответствовать определённым критериям.
//    электронная почта не может быть пустой и должна содержать символ @;
//    логин не может быть пустым и содержать пробелы;
//    имя для отображения может быть пустым — в таком случае будет использован логин;
//    дата рождения не может быть в будущем.
    private boolean validUser(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) return false;
        if (user.getEmail() == null || user.getEmail().equals("") ||
                !user.getEmail().contains(EMAIL_STRING)) return false;
        if (user.getLogin() == null || user.getLogin().equals("") ||
                user.getLogin().contains(" ")) return false;
        if (user.getName().equals("")) user.setName(user.getLogin());
        return true;
    }

    private void fullValidUser(User user) throws ValidationException {
        if (!validUser(user)) {
            log.warn("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                    "post", "/user", user);
            throw new ValidationException();
        }
    }

    private void checkUnknownUser(User user) throws ValidationException {
        if (!users.containsKey(user.getId())) {
            log.warn("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                    "post", "/user", user);
            throw new ValidationException();
        }
    }


}
