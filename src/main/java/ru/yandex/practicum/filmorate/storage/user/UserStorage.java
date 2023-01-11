package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;

public interface UserStorage {
    HashMap<Integer, User> getUsers();

    void setUsers(HashMap<Integer, User> users);

    User getUsersById(int id);

    User addToStorageUser(User user);

    User updateToStorageUser(User user);

    void proposalFrendship(User user1, User user2);

    void deleteProposalFrendship(User user1, User user2);

    int getAndIncrementCounterId();


}
