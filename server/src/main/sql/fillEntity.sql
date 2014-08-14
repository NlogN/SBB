-- fill passenger
INSERT INTO passenger VALUES (1, 'Sergey', 'Vasilyev', '1993-02-20');
INSERT INTO passenger VALUES (2, 'Ivan', 'Petrov', '1993-02-20');
INSERT INTO passenger VALUES (3, 'Petr', 'Popov', '1993-02-20');
INSERT INTO passenger VALUES (4, 'Kirill', 'Simonov', '1993-02-20');
INSERT INTO passenger VALUES (5, 'Maxim', 'Nikovaev', '1993-02-20');

-- fill station
INSERT INTO station VALUES (1, 'Moskow');
INSERT INTO station VALUES (2, 'Saint-Peterburg');
INSERT INTO station VALUES (3, 'Novosibirsk');

-- fill train
INSERT INTO train VALUES (1, 123, 100);
INSERT INTO train VALUES (2, 239, 200);

-- fill ticket
INSERT INTO ticket VALUES (1, 1, 1, '1993-02-20');
INSERT INTO ticket VALUES (2, 2, 1, '1993-02-20');
INSERT INTO ticket VALUES (3, 3, 2, '1993-02-20');
INSERT INTO ticket VALUES (4, 4, 2, '1993-02-20');

-- fill schedule
INSERT INTO schedule VALUES (1, 1, 1, '00:00:01',1);
INSERT INTO schedule VALUES (2, 1, 2, '00:00:02',2);
INSERT INTO schedule VALUES (3, 2, 1, '00:00:03',3);
INSERT INTO schedule VALUES (4, 2, 2, '00:00:04',4);