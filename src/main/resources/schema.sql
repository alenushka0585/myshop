-- создание дополнительных таблиц (не @Entity)

CREATE TABLE IF NOT EXISTS COUNTRIES  (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS BOOK  (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);