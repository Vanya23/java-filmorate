package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film {
    Set<Long> likes = new HashSet<>(); // список лайков
    @NotBlank
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
