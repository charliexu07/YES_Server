--drop database if exists YES_Database;
--create database YES_Database;
CREARE DATABASE IF NOT EXIST YES_database;
USE YES_database;

CREATE TABLE user {
    id INTEGER NOT NULL,
    active BOOLEAN,
    password VARCHAR(255),
    roles VARCHAR(255),
    user_name VARCHAR(255),
    PRIMARY KEY (id)
};

INSERT INTO USER VALUES (1, TRUE, '$2a$10$iXp2FjskWIo/rxYzgISsleP.iLRYJ0G5PZl.68cZDXDqENnrs7Cj2', 'ROLE_USER', 'Marina_Wang_01');