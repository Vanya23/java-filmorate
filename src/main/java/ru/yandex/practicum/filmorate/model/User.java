package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private Set<Long> friends = new HashSet<>(); // список друзей
    @NotBlank
    private int id; // целочисленный идентификатор
    @NonNull
    @Email(message = "Email should be valid")
    private String email; // электронная почта
    @NonNull
    private String login; // логин пользователя
    @NonNull
    private String name = ""; // имя для отображения
    @NonNull
    private LocalDate birthday; // дата рождения

}
