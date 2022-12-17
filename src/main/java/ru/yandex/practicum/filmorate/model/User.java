package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class User {
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
