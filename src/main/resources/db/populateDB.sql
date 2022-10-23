DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
values (100000, '2022-10-20 10:00:00', 'USER1: Breakfast', 500),
       (100000, '2022-10-20 13:00:00', 'USER1: Lunch', 1000),
       (100000, '2022-10-20 20:00:00', 'USER1: Dinner', 500),
       (100000, '2022-10-21 10:00:00', 'USER1: Breakfast', 500),
       (100000, '2022-10-21 13:00:00', 'USER1: Lunch', 1000),
       (100000, '2022-10-21 20:00:00', 'USER1: Dinner', 510),
       (100001, '2022-10-20 14:00:00', 'USER2: Lunch', 510),
       (100001, '2022-10-20 21:00:00', 'USER2: Dinner', 1500);
