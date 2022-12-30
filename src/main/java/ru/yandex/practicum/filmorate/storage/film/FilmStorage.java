package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;

public interface FilmStorage {
    HashMap<Integer, Film> getFilms();

    void setFilms(HashMap<Integer, Film> films);

    int getCounterId();

    void setCounterId(int counterId);

    int getAndIncrementCounterId();


}
