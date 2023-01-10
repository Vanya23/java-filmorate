package ru.yandex.practicum.filmorate.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.*;

@Component
public class FilmDbStorage implements FilmStorage {

    private final Logger log = LoggerFactory.getLogger(UserDbStorage.class);

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public HashMap<Integer, Film> getFilms() {
        return privategetFilms(-1);
    }

    @Override
    public void setFilms(HashMap<Integer, Film> films) {
        // в реализации db метод не используется, оставлен для совместимости
    }

    @Override
    public Film getFilmsById(int id) {
        return privategetFilms(id).get(id);
    }

    public HashMap<Integer, Film> privategetFilms(int id) {
        //если -1, то все фильмы. Если число то id фильма
        // выполняем запрос к базе данных.
        SqlRowSet filmRows;
        if (id == -1) filmRows = jdbcTemplate.queryForRowSet("select * from films");
        else filmRows = jdbcTemplate.queryForRowSet("select * from films where id =?", id);

        HashMap<Integer, Film> films = new HashMap<>();
        while (filmRows.next()) {
            // получили объект
            // подобный конструтор используется для совместимости с предыдущими спринтами
            Film film = new Film(filmRows.getString("name"), filmRows.getString("description"),
                    filmRows.getDate("releasedate").toLocalDate(), filmRows.getInt("duration"));
            film.setRate(filmRows.getInt("rate"));
            film.setMpa(getMPAById(filmRows.getInt("mpa_id")));
            film.setId(filmRows.getInt("id"));
            film.setLikes(findLikes(film.getId())); // лайки
            film.setGenres(findGenres(film.getId())); // жанры
            films.put(film.getId(), film);
        }
        return films;
    }

    // работа с жанрами
    @Override
    public HashMap<Integer, Genre> getGenres() {
        return privategetGenres(-1);
    }

    @Override
    public Genre getGenreById(int id) {
        return privategetGenres(id).get(id);
    }

    public HashMap<Integer, Genre> privategetGenres(int id) {
        //если -1, то все фильмы. Если число то id фильма
        // выполняем запрос к базе данных.
        SqlRowSet genreRows;
        if (id == -1) genreRows = jdbcTemplate.queryForRowSet("select * from genre");
        else genreRows = jdbcTemplate.queryForRowSet("select * from genre where id =?", id);

        HashMap<Integer, Genre> genres = new HashMap<>();
        while (genreRows.next()) {
            // получили объект
            // подобный конструктор используется для совместимости с предыдущими спринтами
            Genre genre = new Genre();
            genre.setName(genreRows.getString("name"));
            genre.setId(genreRows.getInt("id"));
            genres.put(genre.getId(), genre);
        }
        return genres;
    }

    // работа с mpa
    @Override
    public HashMap<Integer, Mpa> getMPA() {
        return privategetMPAs(-1);
    }

    @Override
    public Mpa getMPAById(int id) {
        return privategetMPAs(id).get(id);
    }

    public HashMap<Integer, Mpa> privategetMPAs(int id) {
        //если -1, то все фильмы. Если число то id фильма
        // выполняем запрос к базе данных.
        SqlRowSet MPARows;
        if (id == -1) MPARows = jdbcTemplate.queryForRowSet("select * from MPA");
        else MPARows = jdbcTemplate.queryForRowSet("select * from MPA where id =?", id);

        HashMap<Integer, Mpa> MPAs = new HashMap<>();
        while (MPARows.next()) {
            // получили объект
            // подобный конструтор используется для совместимости с предыдущими спринтами
            Mpa mpa = new Mpa();
            mpa.setName(MPARows.getString("name"));
            mpa.setId(MPARows.getInt("id"));
            MPAs.put(mpa.getId(), mpa);
        }
        return MPAs;
    }

    @Override
    public int getCounterId() {
        // в реализации db метод не используется, оставлен для совместимости
        return 0;
    }

    @Override
    public void setCounterId(int counterId) {
        // в реализации db метод не используется, оставлен для совместимости
    }

    @Override
    public int getAndIncrementCounterId() {
        // в реализации db метод не используется, оставлен для совместимости
        return 0;
    }

    @Override
    public Film addToStorageFilm(Film film) {
        // заполнили таблицу films
        jdbcTemplate.update(
                "INSERT INTO films (name, description, releasedate, duration, mpa_id, rate) VALUES(?, ?, ?, ?, ?, ?)",
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId(), film.getRate());
        // получаем id user из db
        SqlRowSet findIdRows = jdbcTemplate.queryForRowSet("select * from films where" +
                        " name =? and description = ? and releasedate = ? and duration = ? and mpa_id = ?",
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId());
        findIdRows.next();
        film.setId(findIdRows.getInt("id"));
        // заполнили таблицу film_user лайков
        for (Long l :
                film.getLikes()) {
            jdbcTemplate.update("INSERT INTO film_user (id_film, id_user) VALUES(?, ?)", film.getId(), l);
        }
        // заполнили таблицу genre_film жанров
        for (Genre genre :
                film.getGenres()) {
            jdbcTemplate.update("INSERT INTO genre_film (id_film, id_genre) VALUES(?, ?)", film.getId(), genre.getId());
        }
        film.setMpa(getMPAById(film.getMpa().getId())); // назначили mpa из db, потому что в тестах приходят только id без имени
        film.setGenres(findGenres(film.getId())); // жанры из db, потому что в тестах приходят только id без имени
        return film;
    }

    @Override
    public Film updateToStorageFilm(Film film) {
        jdbcTemplate.update(
                "UPDATE films SET name = ?, description = ?, releasedate = ?," +
                        " duration = ?, mpa_id = ?, rate = ? WHERE id = ?",
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId(), film.getRate(), film.getId());
        // обновили таблицу film_user лайков
        // удаляем жанры и лайки
        jdbcTemplate.update("DELETE FROM film_user WHERE id_film = ?", film.getId());
        jdbcTemplate.update("DELETE FROM genre_film WHERE id_film = ?", film.getId());
        for (Long l :
                film.getLikes()) {
            jdbcTemplate.update("INSERT INTO film_user (id_film, id_user) VALUES(?, ?)", film.getId(), l);
        }
        // заполнили таблицу genre_film жанров
        for (Genre genre :
                film.getGenres()) {
            try {
                jdbcTemplate.update("INSERT INTO genre_film (id_film, id_genre) VALUES(?, ?)", film.getId(), genre.getId());
            } catch (DataAccessException e) {
                // погашение ошибки Uniq SQL при дубликатном добавлении в тестировании
            }

        }
        film.setMpa(getMPAById(film.getMpa().getId())); // назначили mpa из db, потому что в тестах приходят только id без имени
        film.setGenres(findGenres(film.getId()));  // жанры из db, потому что в тестах приходят только id без имени
        return film;
    }

    @Override
    public void addLike(Film film, User user) {
        jdbcTemplate.update("INSERT INTO film_user (id_film, id_user) VALUES(?, ?)", film.getId(), user.getId());
    }

    @Override
    public void deleteLike(Film film, User user) {
        jdbcTemplate.update("DELETE FROM film_user WHERE id_film = ? and id_user = ?",
                film.getId(), user.getId());
    }

    private Set<Long> findLikes(int id) {
        // внутренняя вспомогательная функция для поиска лайков фильма id
        SqlRowSet likeRows = jdbcTemplate.queryForRowSet("select id_user from film_user  " +
                "where id_film = ?", id);
        Set<Long> likes = new HashSet<>();
        while (likeRows.next()) {
            likes.add((long) likeRows.getInt("id_user"));
        }
        return likes;
    }

    private List<Genre> findGenres(int id) {
        // внутренняя вспомогательная функция для поиска жанров фильма id
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("select * from genre_film  gf left join genre ge on gf.id_genre = ge.id " +
                "where id_film = ? order by ge.id", id);
        List<Genre> genres = new ArrayList<>();
        while (genreRows.next()) {
            Genre genre = new Genre();
            genre.setId(genreRows.getInt("id_genre"));
            genre.setName(genreRows.getString("name"));
            genres.add(genre);
        }
        return genres;
    }


}
