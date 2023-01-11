Схема проверена в ПИР-РЕВЬЮ (есть данные в таблице проверки)
ссылка на файл диаграммы https://dbdiagram.io/d/63b554dd7d39e42284e8d904

Описание: 
1) основые таблицы film, user
2) лайки фильма в таблице film_user
3) друзья в таблице friends
4) жанры фильмов в таблице genre_film, название жанра в таблице genre
5) в таблице MPA типы оценок фильма

Запросы:

--- друзья user id = 1
select u.id, u."name"  
from "user" u 
left join friends f on u.id = f.id_user1 
where sf."name" = 'friend';

--- все фильмы с жанром id = 1
select f."name", f.releasedate 
from genre_film gf 
left join film f on gf.id_film = f.id
left join genre g on gf.id_genre = g.id
where gf.id_genre = 1;

--- имена людей поставиших лайки фильму id = 1
select u."name", u.login 
from film_user fu 
left join "user" u on fu.id_user = u.id
left join film f on fu.id_film = f.id
where fu.id_film = 1;
