package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserDbStorage implements UserStorage {


    private final Logger log = LoggerFactory.getLogger(UserDbStorage.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public HashMap<Integer, User> getUsers() {
        return privateGetUsers(-1);
    }

    @Override
    public void setUsers(HashMap<Integer, User> users) {
        // в реализации db метод не используется, оставлен для совместимости

    }

    @Override
    public User getUsersById(int id) {
        return privateGetUsers(id).get(id);
    }

    private HashMap<Integer, User> privateGetUsers(int id) {
        //если -1, то все пользователи. Если число то id пользователя
        // выполняем запрос к базе данных.
        SqlRowSet userRows;
        if (id == -1) userRows = jdbcTemplate.queryForRowSet("select * from users");
        else userRows = jdbcTemplate.queryForRowSet("select * from users where id =?", id);

        HashMap<Integer, User> users = new HashMap<>();
        while (userRows.next()) {
            // получили объект
            // подобный конструтор используется для совместимости с предыдущими спринтами
            User user = new User(userRows.getString("email"), userRows.getString("login"),
                    userRows.getDate("BIRTHDAY").toLocalDate());
            user.setId(userRows.getInt("id"));
            user.setName(userRows.getString("name"));

            //
            user.setFriends(findProposalFriendship(user.getId())); // поиск заявок в друзья
            users.put(user.getId(), user);
        }
        return users;
    }

    @Override
    public User addToStorageUser(User user) {
        jdbcTemplate.update(
                "INSERT INTO users (EMAIL, LOGIN, NAME, BIRTHDAY) VALUES(?, ?, ?, ?)",
                user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        for (Long l :
                user.getFriends()) {
            proposalFrendship(user, getUsersById(Math.toIntExact(l)));
        }
        // получаем id user из db
        SqlRowSet findIdRows = jdbcTemplate.queryForRowSet("select id from users where" +
                        " EMAIL = ? and LOGIN = ? and NAME = ? and BIRTHDAY = ?",
                user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        findIdRows.next();
        user.setId(findIdRows.getInt("id"));
        return user;
    }

    @Override
    public User updateToStorageUser(User user) {
        jdbcTemplate.update(
                "UPDATE users SET EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? WHERE ID = ?",
                user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        // удаляем все заявки userа
        jdbcTemplate.update("DELETE FROM FRIENDS WHERE ID_USER1 = ?", user.getId());
        //отправляем новые заявки
        for (Long l :
                user.getFriends()) {
            proposalFrendship(user, getUsersById(Math.toIntExact(l)));
        }
        return user;
    }

    private void makeFullFriends(User user1, User user2) {
        proposalFrendship(user1, user2);
        proposalFrendship(user2, user1);
    }

    @Override
    public void proposalFrendship(User user1, User user2) { // user1 добавляет в друзья user2
        // находим отправлял user1 ли заявку user2
        SqlRowSet whomSendRows = jdbcTemplate.queryForRowSet("select * from FRIENDS where ID_USER1 =? and ID_USER2 = ?", user1.getId(), user2.getId());
        Set<Long> listSend = new HashSet<>();
        while (whomSendRows.next()) {
            listSend.add(whomSendRows.getLong("ID_USER2"));
        }
        // если уже  ранее была заявка ничего не делается
        if (listSend.contains(user2.getId())) return;
        // если у юзера 1 и 2 не было никаких общих записей, то добавляем заявку
        jdbcTemplate.update(
                "INSERT INTO FRIENDS (ID_USER1, ID_USER2) VALUES(?, ?);",
                user1.getId(), user2.getId());
    }

    @Override
    public void deleteProposalFrendship(User user1, User user2) { // удаление заявки
        try {
            jdbcTemplate.update( // если статус not friend делаем update
                    "DELETE FROM FRIENDS WHERE ID_USER1 = ? AND ID_USER2 = ?",
                    user1.getId(), user2.getId());
        } catch (DataAccessException e) {
        }
    }


    @Override
    public int getAndIncrementCounterId() {
        // в реализации db метод не используется, оставлен для совместимости
        return 0;
    }

    private Set<Long> findProposalFriendship(int id) {
        // внутренняя вспомогательная функция для поиска предложений дружбы юзера id
        SqlRowSet friendRows1 = jdbcTemplate.queryForRowSet( // кому пользователь отправил заявку
                "select id_user2 from friends  where id_user1 = ?", id);
        Set<Long> friends1 = new HashSet<>();
        while (friendRows1.next()) {
            friends1.add((long) friendRows1.getInt("id_user2"));
        }
        return friends1;
    }


    private Set<Long> findFullFriends(int id) {
        // внутренняя вспомогательная функция для поиска друзей юзера id
        // делается два запроса потому что возможно в user1 и в user2
        SqlRowSet friendRows1 = jdbcTemplate.queryForRowSet( // кто пользователю отправил заявку
                "select id_user1 from friends where id_user2 = ?", id);
        SqlRowSet friendRows2 = jdbcTemplate.queryForRowSet( // кому пользователь отправил заявку
                "select id_user2 from friends where id_user1 = ?", id);
        Set<Long> friends1 = new HashSet<>();
        Set<Long> friends2 = new HashSet<>();
        Set<Long> result = new HashSet<>();
        while (friendRows1.next()) {
            friends1.add((long) friendRows1.getInt("id_user1"));
        }
        while (friendRows2.next()) {
            friends2.add((long) friendRows2.getInt("id_user2"));
        }
        // находим где заявки совпали
        for (Long l :
                friends1) {
            if (friends2.contains(l)) result.add(l);
        }
        return result;
    }

}
