package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

public class FilmService {
    private static final int MAX_LENGTH_DESCRIPTION = 200;
    private static final LocalDate EARLY_DATE = LocalDate.of(1895, 12, 28);
    private static final long MIN_DURATION = 0;

    //Проверьте данные, которые приходят в запросе на добавление нового фильма или пользователя.
    // Эти данные должны соответствовать определённым критериям.
//    название не может быть пустым;
//    максимальная длина описания — 200 символов;
//    дата релиза — не раньше 28 декабря 1895 года;
//    продолжительность фильма должна быть положительной.
    public static boolean validFilm(Film film) {
        if (validFilmName(film)) return false;
        if (validFilmDescription(film)) return false;
        if (validFilmReleaseDate(film)) return false;
        return !validFilmDuration(film);
    }

    private static boolean validFilmName(Film film) {
        return film.getName().equals("");
    }

    private static boolean validFilmDescription(Film film) {
        return film.getDescription().length() > MAX_LENGTH_DESCRIPTION;
    }

    private static boolean validFilmReleaseDate(Film film) {
        return film.getReleaseDate().isBefore(EARLY_DATE);
    }

    private static boolean validFilmDuration(Film film) {
        return film.getDuration() <= MIN_DURATION;
    }

}
