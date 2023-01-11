package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.HashMap;

@Component
public class InMemoryUserStorage implements UserStorage {

    private HashMap<Integer, User> users = new HashMap<Integer, User>();

    private int counterId = 1;


    public HashMap<Integer, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<Integer, User> users) {
        this.users = users;
    }

    @Override
    public User getUsersById(int id) {
        return getUsers().get(id);
    }

    @Override
    public User addToStorageUser(User user) {
        // метод используется только в db
        return user;
    }

    @Override
    public User updateToStorageUser(User user) {
        // метод используется только в db

        return user;
    }

    @Override
    public void proposalFrendship(User user1, User user2) {
        UserService.addFriends(user1, user2);
    }

    @Override
    public void deleteProposalFrendship(User user1, User user2) {
        UserService.deleteFriends(user1, user2);
    }


    public void setCounterId(int counterId) {
        this.counterId = counterId;
    }

    public int getAndIncrementCounterId() {
        return counterId++;
    }
}
