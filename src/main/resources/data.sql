-- данные для таблиц которые предварительно создались через файл schema.sql

MERGE INTO book (ID, NAME) KEY(ID) VALUES (0, 'admin'), (1, 'The Tartar Steppe'), (2, 'Poem Strip'), (3, 'Restless Nights: Selected Stories of Dino Buzzati');

--insert IGNORE into book (id, name) values(1, 'The Tartar Steppe');
--insert IGNORE into book (id, name) values(2, 'Poem Strip');
--insert IGNORE into book (id, name) values(3, 'Restless Nights: Selected Stories of Dino Buzzati');

MERGE INTO countries (ID, NAME) KEY(ID) VALUES  (1, 'USA'), (2, 'France'),  (3, 'Brazil'), (4, 'Italy'), (5, 'Canada');
--INSERT IGNORE INTO countries (id, name) VALUES (1, 'USA');
--INSERT IGNORE INTO countries (id, name) VALUES (2, 'France');
--INSERT IGNORE INTO countries (id, name) VALUES (3, 'Brazil');
--INSERT IGNORE INTO countries (id, name) VALUES (4, 'Italy');
--INSERT IGNORE INTO countries (id, name) VALUES (5, 'Canada');