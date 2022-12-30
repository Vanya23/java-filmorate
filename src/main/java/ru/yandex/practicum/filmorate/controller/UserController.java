package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.service.UserValidate;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Slf4j
@RestController
public class UserController {
    public static UserStorage userStorage = new InMemoryUserStorage();

    @GetMapping("/users")
    public Collection<User> findAll() {
        return userStorage.getUsers().values();
    }

    @GetMapping("users/{id}") // возможность получать каждый данные о пользователях по их уникальному
    public User findUserById(@PathVariable int id) throws NotFoundException {
        checkUnknownUser(id); // NotFoundException
        return userStorage.getUsers().get(id);
    }

    @GetMapping("/users/{id}/friends") // возвращаем список пользователей, являющихся его друзьями
    public List<User> listFriends(@PathVariable int id) throws NotFoundException {
        checkUnknownUser(id); // NotFoundException
        List<User> friends = new ArrayList<>();
        for (Long idL :
                userStorage.getUsers().get(id).getFriends()) {
            int idk = Math.toIntExact(idL);
            friends.add(userStorage.getUsers().get(idk));
        }
        return friends;
    }

    @GetMapping("/users/{id}/friends/common/{otherId}") // список друзей, общих с другим пользователем.
    public List<User> listMutualFriends(@PathVariable int id, @PathVariable int otherId) throws NotFoundException {
        checkUnknownUser(id, otherId); // NotFoundException
        User user = userStorage.getUsers().get(id);
        User userOther = userStorage.getUsers().get(otherId);
        HashSet<Long> mutualSet = UserService.mutualFriends(user, userOther); // получили список общих друзей
        List<User> friends = new ArrayList<>();
        for (Long idL :
                mutualSet) {
            int idk = Math.toIntExact(idL);
            friends.add(userStorage.getUsers().get(idk));
        }
        return friends;
    }

    @PostMapping(value = "/users")
    public User create(@RequestBody User user) throws ValidationException {
        fullValidUser(user);
        user.setId(userStorage.getAndIncrementCounterId());
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                "post", "/user", user);
        userStorage.getUsers().put(user.getId(), user);
        return user;
    }

    @PutMapping(value = "/users")
    public User update(@RequestBody User user) throws ValidationException, NotFoundException {
        fullValidUser(user); // если данные некорретны то ValidationException
        checkUnknownUser(user);// если пользователь не найден то NotFoundException
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                "put", "/user", user);
        userStorage.getUsers().put(user.getId(), user);
        return user;
    }

    @PutMapping(value = "/users/{id}/friends/{friendId}") // добавление в друзья
    public void addFriends(@PathVariable int id, @PathVariable int friendId) throws NotFoundException {
        checkUnknownUser(id, friendId); // NotFoundException
        HashMap<Integer, User> users = userStorage.getUsers();
        UserService.addFriends(users.get(id), users.get(friendId));
    }

    @DeleteMapping(value = "/users/{id}/friends/{friendId}") // удаление друзей
    public void deleteFriends(@PathVariable int id, @PathVariable int friendId) throws NotFoundException {
        checkUnknownUser(id, friendId); // NotFoundException
        HashMap<Integer, User> users = userStorage.getUsers();
        UserService.deleteFriends(users.get(id), users.get(friendId));
    }

    private void fullValidUser(User user) throws ValidationException {
        if (!UserValidate.validUser(user)) {
            log.warn("Получен запрос к эндпоинту: '{} {}', Выброшено исключение: '{}',  Причина: '{}', Строка параметров запроса: '{}'",
                    "post", "/users", ValidationException.class.getName(), "Данные пользователя некорректные", user);
            throw new ValidationException();
        }
    }

    private void checkUnknownUser(User user) throws NotFoundException { // проверка по Users
        if (!userStorage.getUsers().containsKey(user.getId())) {
            log.warn("Получен запрос к эндпоинту: '{} {}', Выброшено исключение: '{}',  Причина: '{}', Строка параметров запроса: '{}'",
                    "put", "/users", ValidationException.class.getName(), "Неизвестный пользователь", user);
            throw new NotFoundException();
        }
    }

    private void checkUnknownUser(int id) throws NotFoundException { // проверка по id
        if (!userStorage.getUsers().containsKey(id)) {
            throw new NotFoundException();
        }
    }

    private void checkUnknownUser(int id1, int id2) throws NotFoundException {
        checkUnknownUser(id1);
        checkUnknownUser(id2);
    }


}
