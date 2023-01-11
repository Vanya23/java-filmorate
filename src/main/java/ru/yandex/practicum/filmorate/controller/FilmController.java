package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.FilmValidate;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
public class FilmController {

    public static FilmStorage filmStorage;
    private final String ROOT_PATH_FILM = "/films";
    private final String ROOT_PATH_GENRE = "/genres";
    private final String ROOT_PATH_MPA = "/mpa";

    public FilmController(@Qualifier("filmDbStorage") FilmDbStorage filmStorage) {
        FilmController.filmStorage = filmStorage;
    }

    @GetMapping(ROOT_PATH_FILM)
    public Collection<Film> findAll() {
        return filmStorage.getFilms().values();
    }

    @GetMapping(ROOT_PATH_FILM + "/{id}") // возможность получать каждый фильм по их уникальному
    public Film findUserById(@PathVariable int id) throws NotFoundException {
        checkUnknownFilm(id); // NotFoundException
        return filmStorage.getFilmsById(id);

    }

    @GetMapping(ROOT_PATH_FILM + "/popular") // Возвращает список из первых count фильмов по количеству лайков.
    // Если значение параметра count не задано, верните первые 10.
    public List<Film> getTopFilms(
            @RequestParam(defaultValue = "10", required = false) int count) {
        return FilmService.mostPopularFilms(filmStorage, count);
    }

    @PostMapping(value = ROOT_PATH_FILM)
    public Film create(@RequestBody Film film) throws ValidationException {
        fullValidFilm(film);
        filmStorage.addToStorageFilm(film);
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                "post", "/films", film);

        return film;
    }

    @PutMapping(value = ROOT_PATH_FILM)
    public Film update(@RequestBody Film film) throws NotFoundException, ValidationException {
        fullValidFilm(film); // если некорректный фильм то ValidationException
        checkUnknownFilm(film); // если фильм не найден  то NotFoundException
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                "put", "/films", film);
        filmStorage.updateToStorageFilm(film);
        return film;
    }

    @PutMapping(value = ROOT_PATH_FILM + "/{id}/like/{userId}") // пользователь ставит лайк фильму.
    public Film addLike(@PathVariable int id, @PathVariable int userId) throws NotFoundException {
        checkUnknownFilm(id);
        checkUnknownUser(userId);
        Film film = filmStorage.getFilms().get(id);
        User user = UserController.userStorage.getUsers().get(userId);
        filmStorage.addLike(film, user);
        return film;
    }

    @DeleteMapping(value = ROOT_PATH_FILM + "/{id}/like/{userId}") // пользователь удаляет лайк.
    public Film deleteLike(@PathVariable int id, @PathVariable int userId) throws NotFoundException {
        checkUnknownFilm(id);
        checkUnknownUser(userId);
        Film film = filmStorage.getFilms().get(id);
        User user = UserController.userStorage.getUsers().get(userId);
        filmStorage.deleteLike(film, user);
        return film;
    }

    // работы  с жанрами
    @GetMapping(ROOT_PATH_GENRE)
    public Collection<Genre> findAllGenres() {
        return filmStorage.getGenres().values();
    }

    @GetMapping(ROOT_PATH_GENRE + "/{id}") // возможность получать каждый жанр по их уникальному
    public Genre findGenreById(@PathVariable int id) throws NotFoundException {
        checkUnknownGenre(id);
        return filmStorage.getGenreById(id);
    }

    // работа с MPA
    @GetMapping(ROOT_PATH_MPA)
    public Collection<Mpa> findAllMPA() {
        return filmStorage.getMPA().values();
    }

    @GetMapping(ROOT_PATH_MPA + "/{id}") // возможность получать каждый mpa по их уникальному
    public Mpa findMPAById(@PathVariable int id) throws NotFoundException {
        checkUnknownMpa(id);
        return filmStorage.getMPAById(id);
    }

    // вспомогательные функции для проверки и валидации объектов
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

    private void checkUnknownMpa(int id) throws NotFoundException { // проверка по id
        if (!filmStorage.getMPA().containsKey(id)) {
            throw new NotFoundException();
        }
    }

    private void checkUnknownGenre(int id) throws NotFoundException { // проверка по id
        if (!filmStorage.getGenres().containsKey(id)) {
            throw new NotFoundException();
        }
    }


}
