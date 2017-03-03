DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM menus;
DELETE FROM cafes;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, password) VALUES
  ('Admin', 'admin'),
 ('Natallia', 'password'),
 ('Andrey', 'password'),
 ('Nadja', 'password'),
 ('Vera', 'password');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_ADMIN', 100000),
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_USER', 100002),
  ('ROLE_USER', 100003),
  ('ROLE_USER', 100004);

INSERT INTO cafes (name) VALUES
  ('Papa Johns'), -- 100005
  ('Sbarro'),     -- 100006
  ('Dominos');    -- 100007

INSERT INTO menus (cafe_id, date_time, dish, price) VALUES
  (100005, '2017-02-08 10:00:00', 'Margarita', 7),
  (100005, '2017-02-08 10:00:00', 'Hawaii', 10),
  (100006, '2017-02-08 10:00:00', 'Diabolo', 9),
  (100006, '2017-02-08 10:00:00', 'Mexicana', 11),
  (100007, '2017-02-08 10:00:00', 'Cheese', 8),
  (100007, '2017-02-08 10:00:00', 'Pepperoni', 9);

INSERT INTO votes (user_id, cafe_id, date_time) VALUES
  (100000, 100005, '2017-02-08 10:00:00'),
  (100001, 100005, '2017-02-08 10:00:00'),
  (100002, 100006, '2017-02-08 10:00:00'),
  (100003, 100007, '2017-02-08 10:00:00'),
  (100004, 100007, '2017-02-08 10:00:00');



