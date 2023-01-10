package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;

public interface FilmStorage {
    HashMap<Integer, Film> getFilms();

    void setFilms(HashMap<Integer, Film> films);

    int getCounterId();

    void setCounterId(int counterId);

    int getAndIncrementCounterId();


    Film addToStorageFilm(Film film);

    Film updateToStorageFilm(Film film);

    void addLike(Film film, User user);

    void deleteLike(Film film, User user);

    Film getFilmsById(int id);

    HashMap<Integer, Genre> getGenres();

    Genre getGenreById(int id);

    Mpa getMPAById(int id);

    HashMap<Integer, Mpa> getMPA();
}
