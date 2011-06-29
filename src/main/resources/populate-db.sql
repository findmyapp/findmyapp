CREATE table program(id integer IDENTITY PRIMARY KEY, day date);
INSERT into program(day) values('2010-01-01');
INSERT into program(day) values('2010-02-05');

CREATE table position(id integer IDENTITY PRIMARY KEY, name varchar(20), ssid varchar(20));
INSERT into position(name, ssid) values('Strossa', 'strossa');
INSERT into position(name, ssid) values('Storsalen', 'storsalen');

CREATE table room(id integer IDENTITY PRIMARY KEY, name varchar(20));

CREATE table sample(id integer IDENTITY PRIMARY KEY, room_id integer,
 FOREIGN KEY (room_id) REFERENCES room(id));

CREATE table signal(id integer IDENTITY PRIMARY KEY, bssid varchar(255), signalstrength integer, sample_id integer);
 
-- INSERT into room
INSERT into room(name) values('Strossa');
INSERT into room(name) values('Storsalen');
-- INSERT into sample
INSERT into sample(id, room_id) values(0, 0);
INSERT into sample(id, room_id) values(1, 1);
-- INSERT into signal
INSERT into signal(id, bssid, signalstrength, sample_id) values(0,'strossa', -30, 0);
INSERT into signal(id, bssid, signalstrength, sample_id) values(1,'storsalen', -75, 0);
INSERT into signal(id, bssid, signalstrength, sample_id) values(2,'strossa', -80, 1);
INSERT into signal(id, bssid, signalstrength, sample_id) values(3,'storsalen', -20, 1);

CREATE table event_showing_real(
	id integer IDENTITY PRIMARY KEY,
	showing_time timestamp,
	publish_time timestamp,
	place varchar(30),
	billig_id integer,
	event_id integer,
	netsale_from timestamp,
	netsale_to timestamp,
	free boolean,
	canceled boolean,
	entrance_id integer
);
INSERT into event_showing_real values(
	11,
	'2011-10-03 00:00:00',
	'2011-10-01 00:00:00',
	'Samfundet',
	0,
	1,
	'2011-10-01 00:00:00',
	'2011-10-21 00:00:00',
	false,
	false,
	2222
);
INSERT into event_showing_real values(
	21,
	'2011-10-01 00:00:00',
	'2011-10-07 00:00:00',
	'Dodens dal',
	0,
	2,
	'2011-10-01 00:00:00',
	'2011-10-07 00:00:00',
	false,
	false,
	2222
);
CREATE table events_event(
	id integer IDENTITY PRIMARY KEY,
	title varchar(255),
	lead varchar(255),
	text varchar(255),
	event_type varchar(30),
	image varchar(100),
	thumbnail varchar(100),
	hidden_from_listing boolean,
	slug varchar(50),
	age_limit smallint,
	detail_photo_id integer
);
INSERT into events_event values(
	1,
	'Konsert 1',
	'Lead 1',
	'Dette er et arrangement!',
	'Konsert',
	'bilde1.jpg',
	'thumb1.jpg',
	false,
	'slug',
	'23',
	2222
);
INSERT into events_event values(
	2,
	'Oktoberfest',
	'Lead 2',
	'Dette blir fest!',
	'fest!',
	'bilde1.jpg',
	'thumb1.jpg',
	false,
	'slug',
	'23',
	2222
);
