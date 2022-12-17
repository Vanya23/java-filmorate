package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class SimpleController {

    @GetMapping("/home")
    public String homePage() {
        return "Рейтинг фильмов";
    }
}
