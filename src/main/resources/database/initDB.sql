CREATE TABLE IF NOT EXISTS author
(
    author_id BIGSERIAL PRIMARY KEY ,
    full_name  VARCHAR(50) NOT NULL ,
    birth DATE NOT NULL ,
    biography VARCHAR  NOT NULL

);

CREATE TABLE IF NOT EXISTS book
(
    book_id BIGSERIAL PRIMARY KEY ,
    name  VARCHAR(50) NOT NULL ,
    publication DATE NOT NULL ,
    genre VARCHAR(50)  NOT NULL ,
    author_id INTEGER ,
    user_id INTEGER ,
    FOREIGN KEY (author_id) REFERENCES author (author_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)

);

CREATE TABLE IF NOT EXISTS users
(
    user_id BIGSERIAL PRIMARY KEY ,
    full_name  VARCHAR(50) NOT NULL ,
    birth DATE NOT NULL ,
    enabled VARCHAR(5) NOT NULL ,
    password VARCHAR(255) NOT NULL ,
    type VARCHAR(8)  NOT NULL ,
    username varchar(255) NOT NULL
);
