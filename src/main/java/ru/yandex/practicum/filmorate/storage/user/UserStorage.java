package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;

public interface UserStorage {
    HashMap<Integer, User> getUsers();

    void setUsers(HashMap<Integer, User> users);

    int getCounterId();

    void setCounterId(int counterId);

    int getAndIncrementCounterId();


}
