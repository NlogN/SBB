-- fill passenger
INSERT INTO passenger (name,surname,birthday) VALUES ('Sergey', 'Vasilyev', '1993-02-20');
INSERT INTO passenger (name,surname,birthday) VALUES ('Ivan', 'Petrov', '1993-02-20');
INSERT INTO passenger (name,surname,birthday) VALUES ('Petr', 'Popov', '1993-02-20');
INSERT INTO passenger (name,surname,birthday) VALUES ('Kirill', 'Simonov', '1993-02-20');
INSERT INTO passenger (name,surname,birthday) VALUES ('Maxim', 'Nikovaev', '1993-02-20');

-- fill station
INSERT INTO station (name) VALUES ('Moskow');
INSERT INTO station (name) VALUES ('Saint-Peterburg');
INSERT INTO station (name) VALUES ('Novosibirsk');

-- fill train
INSERT INTO train (number,capacity) VALUES (123, 100);
INSERT INTO train (number,capacity) VALUES (239, 200);

-- fill ticket

INSERT INTO ticket (passenger_id, train_id, date) VALUES (1, 1, '2013/4/4');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (2, 1, '2013/4/4');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (3, 2, '2013/4/4');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (4, 2, '2013/4/4');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (1, 2, '2013/5/3');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (2, 2, '2013/5/3');
INSERT INTO ticket (passenger_id, train_id, date) VALUES (2, 2, '2014/08/17');

-- fill schedule
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 1, 1, '2014-08-17 12:05:11', 1);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 2, 1, '2014-08-17 15:10:11', 3);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 1, 2, '2014-08-17 12:05:11', 2);
INSERT INTO schedule (`station_id`, `train_id`, `time`,  `offset`) VALUES ( 3, 2, '2014-08-17 13:05:11', 4);


