package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Service
public class UserValidate {
    private static final String EMAIL_STRING = "@";

    //Проверьте данные, которые приходят в запросе на добавление нового фильма или пользователя.
    // Эти данные должны соответствовать определённым критериям.
//    электронная почта не может быть пустой и должна содержать символ @;
//    логин не может быть пустым и содержать пробелы;
//    имя для отображения может быть пустым — в таком случае будет использован логин;
//    дата рождения не может быть в будущем.
    public static boolean validUser(User user) {
        if (validUserBirthday(user)) return false;
        if (validUserEmail(user)) return false;
        if (validUserLogin(user)) return false;
        if (validUserName(user)) user.setName(user.getLogin());
        return true;
    }

    private static boolean validUserBirthday(User user) {
        return user.getBirthday().isAfter(LocalDate.now());
    }

    private static boolean validUserEmail(User user) {
        return user.getEmail().equals("") || !user.getEmail().contains(EMAIL_STRING);
    }

    private static boolean validUserLogin(User user) {
        return user.getLogin().equals("") || user.getLogin().contains(" ");
    }

    private static boolean validUserName(User user) {
        return user.getName().equals("");
    }
}
