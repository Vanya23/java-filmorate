package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class User {
    private int id; // целочисленный идентификатор
    @NonNull
    private String email; // электронная почта
    @NonNull
    private String login; // логин пользователя
    @NonNull
    private String name = ""; // имя для отображения
    @NonNull
    private LocalDate birthday; // дата рождения

}
