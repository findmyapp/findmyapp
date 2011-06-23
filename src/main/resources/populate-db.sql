CREATE table program(id integer IDENTITY PRIMARY KEY, day date);
INSERT into program(day) values('2010-01-01');
INSERT into program(day) values('2010-02-05');
CREATE table ukeprogram_event(
	id integer IDENTITY PRIMARY KEY,
	title varchar(255),
	lead varchar(255),
	description varchar(255),
	picture varchar(100),
	thumbnail varchar(100),
	url varchar(255),
	event_type varchar(1),
	location varchar(2),
	ticket_price integer,
	event_date date
);
INSERT into ukeprogram_event values(
	1,
	'Konsert 1',
	'Lead 1',
	'Dette er et arrangement!',
	'bilde1.jpg',
	'thumb1.jpg',
	'url',
	'a',
	'sf',
	200,
	'2011-10-07'
);
INSERT into ukeprogram_event values(
	2,
	'Oktoberfest',
	'Lead 2',
	'Dette blir fest!',
	'bilde2.jpg',
	'thumb2.jpg',
	'url',
	'b',
	'te',
	200,
	'2011-10-10'
);
CREATE table event_type(
	id varchar(1) PRIMARY KEY,
	title varchar(255)
);

INSERT into event_type values('a', 'Konsert');
INSERT into event_type values('b', 'Fest');

CREATE table location(
	id varchar(2) PRIMARY KEY,
	name varchar(255)
);
INSERT into location values('sf', 'Samfundet');
INSERT into location values('te', 'Dødens dal');