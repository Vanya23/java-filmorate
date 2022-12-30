package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.FilmValidate;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
public class FilmController {

    public static FilmStorage filmStorage = new InMemoryFilmStorage();


    @GetMapping("/films")
    public Collection<Film> findAll() {
        return filmStorage.getFilms().values();
    }

    @GetMapping("films/{id}") // возможность получать каждый фильм по их уникальному
    public Film findUserById(@PathVariable int id) throws NotFoundException {
        checkUnknownFilm(id); // NotFoundException
        return filmStorage.getFilms().get(id);
    }

    @GetMapping("/films/popular") // возвращает список из первых count фильмов по количеству лайков.
    // Если значение параметра count не задано, верните первые 10.
    public List<Film> getTopFilms(
            @RequestParam(defaultValue = "10", required = false) int count) {
        return FilmService.mostPopularFilms(filmStorage, count);
    }

    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) throws ValidationException {
        fullValidFilm(film);
        film.setId(filmStorage.getAndIncrementCounterId());
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                "post", "/films", film);
        filmStorage.getFilms().put(film.getId(), film);
        return film;
    }

    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) throws NotFoundException, ValidationException {
        fullValidFilm(film); // если некорректный фильм то ValidationException
        checkUnknownFilm(film); // если фильм не найден  то NotFoundException
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                "put", "/films", film);
        filmStorage.getFilms().put(film.getId(), film);
        return film;
    }

    @PutMapping(value = "/films/{id}/like/{userId}") // пользователь ставит лайк фильму.
    public Film addLike(@PathVariable int id, @PathVariable int userId) throws NotFoundException {
        checkUnknownFilm(id);
        checkUnknownUser(userId);
        Film film = filmStorage.getFilms().get(id);
        User user = UserController.userStorage.getUsers().get(userId);
        FilmService.addLike(film, user);
        return film;
    }

    @DeleteMapping(value = "/films/{id}/like/{userId}") // пользователь удаляет лайк.
    public Film deleteLike(@PathVariable int id, @PathVariable int userId) throws NotFoundException {
        checkUnknownFilm(id);
        checkUnknownUser(userId);
        Film film = filmStorage.getFilms().get(id);
        User user = UserController.userStorage.getUsers().get(userId);
        FilmService.deleteLike(film, user);
        return film;
    }


    private void fullValidFilm(Film film) throws ValidationException {
        if (!FilmValidate.validFilm(film)) {
            log.warn("Получен запрос к эндпоинту: '{} {}', Выброшено исключение: '{}',  Причина: '{}', Строка параметров запроса: '{}'",
                    "post", "/films", ValidationException.class.getName(), "Данные фильма некорректные", film);
            throw new ValidationException();
        }
    }

    private void checkUnknownFilm(Film film) throws NotFoundException {
        if (!filmStorage.getFilms().containsKey(film.getId())) {
            log.warn("Получен запрос к эндпоинту: '{} {}', Выброшено исключение: '{}',  Причина: '{}', Строка параметров запроса: '{}'",
                    "put", "/films", ValidationException.class.getName(), "Неизвестный фильм", film);
            throw new NotFoundException();
        }
    }

    private void checkUnknownFilm(int id) throws NotFoundException { // проверка по id
        if (!filmStorage.getFilms().containsKey(id)) {
            throw new NotFoundException();
        }
    }

    private void checkUnknownUser(int id) throws NotFoundException { // проверка по id
        if (!UserController.userStorage.getUsers().containsKey(id)) {
            throw new NotFoundException();
        }
    }


}
