package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class Film {

    private int id; // целочисленный идентификатор
    @NonNull
    private String name; // название
    @NonNull
    private String description; // описание
    @NonNull
    private LocalDate releaseDate; // дата релиза
    @NonNull
    private long duration; // продолжительность фильма

}
