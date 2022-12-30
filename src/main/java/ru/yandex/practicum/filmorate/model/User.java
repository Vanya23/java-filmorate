package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    Set<Long> friends = new HashSet<>(); // список друзей
    @NotBlank
    int id; // целочисленный идентификатор
    @NonNull
    @Email(message = "Email should be valid")
    String email; // электронная почта
    @NonNull
    String login; // логин пользователя
    @NonNull
    String name = ""; // имя для отображения
    @NonNull
    LocalDate birthday; // дата рождения

}
