;
--delete from genre_film;
--delete from friends;
--delete from  film_user;
--delete from  films;
--delete from  users;
--
--delete from  MPA;
--delete from  GENRE;

--delete from  STATUS_FRIENDS;

--INSERT INTO users (EMAIL, LOGIN, NAME, BIRTHDAY) VALUES('mail1@mail.ru', 'myLogin1', 'Ivan', '1987-05-05');
--INSERT INTO users (EMAIL, LOGIN, NAME, BIRTHDAY) VALUES('mail2@mail.ru', 'myLogin2', 'Masha', '1990-01-15');

insert into mpa (NAME) values('G');
insert into mpa (NAME) values('PG');
insert into mpa (NAME) values('PG-13');
insert into mpa (NAME) values('R');
insert into mpa (NAME) values('NC-17');

insert into genre (NAME) values('Комедия');
insert into genre (NAME) values('Драма');
insert into genre (NAME) values('Мультфильм');
insert into genre (NAME) values('Триллер');
insert into genre (NAME) values('Документальный');
insert into genre (NAME) values('Боевик');

