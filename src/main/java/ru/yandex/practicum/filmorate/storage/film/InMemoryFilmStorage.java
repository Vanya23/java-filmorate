package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private HashMap<Integer, Film> films = new HashMap<>();
    private int counterId = 1;


    public HashMap<Integer, Film> getFilms() {
        return films;
    }

    public void setFilms(HashMap<Integer, Film> films) {
        this.films = films;
    }

    public int getCounterId() {
        return counterId;
    }

    public void setCounterId(int counterId) {
        this.counterId = counterId;
    }

    public int getAndIncrementCounterId() {
        return counterId++;
    }

    @Override
    public Film addToStorageFilm(Film film) {
        // метод используется только в db
        return null;
    }

    @Override
    public Film updateToStorageFilm(Film film) {
        // метод используется только в db
        return null;
    }

    @Override
    public void addLike(Film film, User user) {
        // метод используется только в db

    }

    @Override
    public void deleteLike(Film film, User user) {
        // метод используется только в db

    }

    @Override
    public Film getFilmsById(int id) {
        return getFilms().get(id);
    }

    @Override
    public HashMap<Integer, Genre> getGenres() {
        // метод используется только в db
        return null;
    }

    @Override
    public Genre getGenreById(int id) {
        // метод используется только в db
        return null;
    }

    @Override
    public Mpa getMPAById(int id) {
        return null;
    }

    @Override
    public HashMap<Integer, Mpa> getMPA() {
        return null;
    }
}
