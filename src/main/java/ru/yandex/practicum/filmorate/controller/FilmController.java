package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
public class FilmController {

    private int counterId = 1;
    private final HashMap<Integer, Film> films = new HashMap<>();
    private static final int MAX_LENGTH_DESCR = 200;
    private static final LocalDate EARLY_DATE = LocalDate.of(1895, 12, 28);
    private static final long MIN_DURATION = 0;


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

    //Проверьте данные, которые приходят в запросе на добавление нового фильма или пользователя.
    // Эти данные должны соответствовать определённым критериям.
//    название не может быть пустым;
//    максимальная длина описания — 200 символов;
//    дата релиза — не раньше 28 декабря 1895 года;
//    продолжительность фильма должна быть положительной.
    private boolean validFilm(Film film) {
        if (film.getName() == null || film.getName().equals("")) return false;
        if (film.getDescription().length() > MAX_LENGTH_DESCR) return false;
        if (film.getReleaseDate().isBefore(EARLY_DATE)) return false;
        return film.getDuration() > MIN_DURATION;
    }

    private void fullValidFilm(Film film) throws ValidationException {
        if (!validFilm(film)) {
            log.warn("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                    "post", "/films", film);
            throw new ValidationException();
        }
    }

    private void checkUnknownFilm(Film film) throws ValidationException {
        if (!films.containsKey(film.getId())) {
            log.warn("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                    "post", "/films", film);
            throw new ValidationException();
        }
    }

}
