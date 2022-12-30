package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FilmorateApplication {

    public static void main(String[] args) {

//        int localCounterId = 1;
//        User user1 = new User("mail@mail.ru", "dolore",
//                LocalDate.of(1990, 12, 12));
//        user1.setName("Nick Name");
//        user1.setId(localCounterId++); // назначение правильного id локальному объекту
//        User user2 = new User("Upmail@mail.ru", "Updolore",
//                LocalDate.of(1990, 12, 12));
//        user2.setName("UpNick Name");
//        user2.setId(localCounterId++); // назначение правильного id локальному объекту
//
//        UserService.addFriends(user1, user2);
//
//        System.out.println(user1.getFriends());

        SpringApplication.run(FilmorateApplication.class, args);
    }


}
