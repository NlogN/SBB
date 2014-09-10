-- fill passenger
INSERT INTO passenger (name,surname,birthday) VALUES ('Sergey', 'Vasilyev', '1981-02-10');
INSERT INTO passenger (name,surname,birthday) VALUES ('Ivan', 'Petrov', '1982-02-20');
INSERT INTO passenger (name,surname,birthday) VALUES ('Petr', 'Ivanov', '1983-02-27');
INSERT INTO passenger (name,surname,birthday) VALUES ('Kirill', 'Simonov', '1984-02-28');
INSERT INTO passenger (name,surname,birthday) VALUES ('Sergey', 'Kirillov', '1985-02-20');

INSERT INTO passenger (name,surname,birthday) VALUES ('Anton', 'Vasilyev', '1995-01-21');
INSERT INTO passenger (name,surname,birthday) VALUES ('Mark', 'Petrov', '1994-02-22');
INSERT INTO passenger (name,surname,birthday) VALUES ('Petr', 'Markov', '1993-03-23');
INSERT INTO passenger (name,surname,birthday) VALUES ('Kirill', 'Markoni', '1992-04-24');
INSERT INTO passenger (name,surname,birthday) VALUES ('Maxim', 'Kirillov', '1991-05-25');

INSERT INTO passenger (name,surname,birthday) VALUES ('Sergio', 'Rossi', '1986-01-25');
INSERT INTO passenger (name,surname,birthday) VALUES ('Andrea', 'Pelligrini', '1985-01-24');
INSERT INTO passenger (name,surname,birthday) VALUES ('Petr', 'Boykiy', '1984-07-23');
INSERT INTO passenger (name,surname,birthday) VALUES ('Vyacheslav', 'Simonov', '1983-08-22');
INSERT INTO passenger (name,surname,birthday) VALUES ('Pavel', 'Nikovaev', '1981-09-21');

-- fill station
INSERT INTO station (name) VALUES ('Moskow');
INSERT INTO station (name) VALUES ('Saint-Petersburg');
INSERT INTO station (name) VALUES ('Novosibirsk');
INSERT INTO station (name) VALUES ('Rostov');
INSERT INTO station (name) VALUES ('Kazan');
INSERT INTO station (name) VALUES ('Ekaterinburg');
INSERT INTO station (name) VALUES ('Omsk');
INSERT INTO station (name) VALUES ('Samara');
INSERT INTO station (name) VALUES ('Krasnoyarsk');
INSERT INTO station (name) VALUES ('Belgorod');

-- fill train
INSERT INTO train (number,capacity) VALUES (123, 100);
INSERT INTO train (number,capacity) VALUES (239, 200);
INSERT INTO train (number,capacity) VALUES (300, 80);
INSERT INTO train (number,capacity) VALUES (321, 500);

-- fill ticket

INSERT INTO ticket (passenger_id, train_id, date) VALUES (1, 1, '2014/09/17');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (2, 1, '2014/09/17');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (3, 1, '2014/09/18');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (4, 1, '2014/09/18');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (5, 1, '2014/09/18');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (6, 2, '2014/09/17');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (7, 2, '2014/09/17');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (8, 2, '2014/09/19');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (9, 2, '2014/09/19');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (10, 2, '2014/09/19');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (11, 3, '2014/09/11');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (12, 3, '2014/09/11');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (13, 3, '2014/09/14');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (14, 4, '2014/09/21');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (15, 4, '2014/09/22');

-- fill schedule
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 1, 1, '2014-09-17 12:05:11', 10);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 2, 1, '2014-09-18 15:10:11', 20);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 3, 1, '2014-09-19 12:05:11', 30);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 4, 1, '2014-09-21 21:25:11', 40);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 5, 1, '2014-09-22 22:25:11', 50);

INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 2, 2, '2014-09-17 12:05:11', 10);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 1, 2, '2014-09-17 15:10:11', 20);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 6, 2, '2014-09-18 12:05:11', 30);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 8, 2, '2014-09-19 21:25:11', 40);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 10, 2,'2014-09-20 22:25:11', 50);

INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 10, 3, '2014-09-11 12:05:11', 10);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 9, 3, '2014-09-12 15:10:11', 20);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 1, 3, '2014-09-13 12:05:11', 30);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 7, 3, '2014-09-14 21:25:11', 40);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 5, 3, '2014-09-15 22:25:11', 50);

INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 5, 4, '2014-09-21 12:05:11', 10);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 6, 4, '2014-09-22 15:10:11', 20);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 1, 4, '2014-09-23 12:05:11', 30);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 7, 4, '2014-09-24 21:25:11', 40);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 3, 4, '2014-09-25 22:25:11', 50);

