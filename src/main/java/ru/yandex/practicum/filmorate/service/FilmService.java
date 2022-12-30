package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;

@Service
public class FilmService {

    public static void addLike(Film film, User user) { //добавление лайка
        film.getLikes().add(Long.valueOf(user.getId()));
    }

    public static void deleteLike(Film film, User user) { //удаление лайка
        film.getLikes().remove(Long.valueOf(user.getId()));
    }

    public static List<Film> mostPopularFilms(FilmStorage filmStorage, int top) { //вывод списка лучших фильмов
        HashMap<Integer, Film> films = filmStorage.getFilms();
        ArrayList<Film> arFilms = new ArrayList<>(films.values()); // получили arrayList фильмов
        Collections.sort(arFilms, new LikesFilmReverseComparator()); // отсортировали в обратном порядке по лайкам
        ArrayList<Film> topFilms; // arrayList топ фильмов
        if (arFilms.size() <= top) topFilms = arFilms; // если фильмов меньше ничего не создаем
        else {
            topFilms = new ArrayList<>();
            int minSize = arFilms.size() < top ? arFilms.size() : top; // минимальный размер массива из двух условий
            for (int i = 0; i < minSize; i++) {
                topFilms.add(arFilms.get(i));
            }
        }
        return topFilms;
    }

}

class LikesFilmComparator implements Comparator<Film> { // сортировка по лайкам
    @Override
    public int compare(Film film1, Film film2) {
        return Integer.valueOf(film1.getLikes().size()).compareTo((film2.getLikes().size()));
    }
}

class LikesFilmReverseComparator implements Comparator<Film> {// сортировка по лайкам в обратном порядке

    @Override
    public int compare(Film film1, Film film2) {
        return -1 * Integer.valueOf(film1.getLikes().size()).compareTo((film2.getLikes().size()));
    }
}
