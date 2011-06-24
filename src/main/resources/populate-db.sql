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
	'2011-10-01 00:00:00',
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
	1,
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