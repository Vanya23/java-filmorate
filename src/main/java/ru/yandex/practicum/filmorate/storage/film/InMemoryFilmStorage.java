package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

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
}
