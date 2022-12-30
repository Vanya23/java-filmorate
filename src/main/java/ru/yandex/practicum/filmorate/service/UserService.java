package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;

@Service
public class UserService {
    public static void addFriends(User user1, User user2) { //добавление в друзья
        user1.getFriends().add(Long.valueOf(user2.getId()));
        user2.getFriends().add(Long.valueOf(user1.getId()));
    }

    public static void deleteFriends(User user1, User user2) { //удаление из друзей
        user1.getFriends().remove(Long.valueOf(user2.getId()));
        user2.getFriends().remove(Long.valueOf(user1.getId()));
    }

    public static HashSet<Long> mutualFriends(User user1, User user2) { //вывод списка общих друзей
        HashSet<Long> mutualF = new HashSet<>();

        for (Long friend1 :  // заполение set общих друзей
                user1.getFriends()) {
            if (user2.getFriends().contains(friend1)) mutualF.add(friend1);
        }
        return mutualF;
    }

}
