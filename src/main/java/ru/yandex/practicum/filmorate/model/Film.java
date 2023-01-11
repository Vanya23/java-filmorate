package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film {
    Set<Long> likes = new HashSet<>(); // список лайков
    List<Genre> genres = new ArrayList<>(); // список жанров, т.к. для тестов надо сортированный список
    Mpa mpa; // ограничение
    int rate; // рейтинг
    int id; // целочисленный идентификатор
    @NonNull
    String name; // название
    @NonNull
    String description; // описание
    @NonNull
    LocalDate releaseDate; // дата релиза
    @NonNull
    @Positive
    long duration; // продолжительность фильма
}
