package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private Set<Long> likes = new HashSet<>(); // список лайков
    @NotBlank
    private int id; // целочисленный идентификатор
    @NonNull
    private String name; // название
    @NonNull
    private String description; // описание
    @NonNull
    private LocalDate releaseDate; // дата релиза
    @NonNull
    @Positive
    private long duration; // продолжительность фильма

}
