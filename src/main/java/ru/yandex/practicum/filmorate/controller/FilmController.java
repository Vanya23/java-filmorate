package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
public class FilmController {
    private int counterId = 1;
    private final HashMap<Integer, Film> films = new HashMap<>();

    @GetMapping("/films")
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) throws ValidationException {
        fullValidFilm(film);
        film.setId(counterId++);
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                "post", "/films", film);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) throws ValidationException {
        fullValidFilm(film);
        checkUnknownFilm(film);
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                "put", "/films", film);
        films.put(film.getId(), film);
        return film;
    }


    private void fullValidFilm(Film film) throws ValidationException {
        if (!FilmService.validFilm(film)) {
            log.warn("Получен запрос к эндпоинту: '{} {}', Выброшено исключение: '{}',  Причина: '{}', Строка параметров запроса: '{}'",
                    "post", "/films", ValidationException.class.getName(), "Данные фильма некорректные",film);
            throw new ValidationException();
        }
    }

    private void checkUnknownFilm(Film film) throws ValidationException {
        if (!films.containsKey(film.getId())) {
            log.warn("Получен запрос к эндпоинту: '{} {}', Выброшено исключение: '{}',  Причина: '{}', Строка параметров запроса: '{}'",
                    "put", "/films", ValidationException.class.getName(), "Неизвестный фильм",film);
            throw new ValidationException();
        }
    }

}
