DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS menus;
DROP TABLE IF EXISTS cafes;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE users
(
  id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name       VARCHAR(255) NOT NULL,
  password   VARCHAR(255) NOT NULL
);

CREATE UNIQUE INDEX users_unique_name_idx ON users (name);

CREATE UNIQUE INDEX users_unique_name_idx ON users (name);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE cafes
(
  id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name       VARCHAR(255)
);

CREATE UNIQUE INDEX cafes_unique_name_idx ON cafes (name);

CREATE TABLE menus
(
  id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  cafe_id     INTEGER NOT NULL,
  date_time   TIMESTAMP    NOT NULL,
  dish        VARCHAR(255) NOT NULL,
  price       INT          NOT NULL,
  FOREIGN KEY (cafe_id) REFERENCES cafes (id) ON DELETE CASCADE
);

CREATE TABLE votes
(
  user_id     INTEGER NOT NULL,
  cafe_id     INTEGER NOT NULL,
  date_time   TIMESTAMP NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (cafe_id) REFERENCES cafes (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX votes_unique_ucd_idx ON votes (user_id, cafe_id, date_time)
