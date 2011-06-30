CREATE table program(id int AUTO_INCREMENT, day date, PRIMARY KEY(id));
INSERT into program(dday) values('2010-01-01');
INSERT into program(dday) values('2010-02-05');

CREATE  TABLE `POSITION_ROOM` (
  `position_room_id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(20) NULL ,
  PRIMARY KEY (`position_room_id`) );

CREATE  TABLE `POSITION_ACCESSPOINT` (
  `bssid` VARCHAR(255) NULL ,
  PRIMARY KEY (`bssid`) );
  
CREATE  TABLE `POSITION_SAMPLE` (
  `position_sample_id` INT NOT NULL AUTO_INCREMENT ,
  `position_room_id` INT NULL ,
  PRIMARY KEY (`position_sample_id`) ,
  INDEX `position_room_id_fk` (`position_room_id` ASC) ,
  CONSTRAINT `position_room_id_fk`
    FOREIGN KEY (`position_room_id` )
    REFERENCES `POSITION_ROOM` (`position_room_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE  TABLE `POSITION_SIGNAL` (
  `position_signal_id` INT NOT NULL AUTO_INCREMENT,
  `position_accesspoint_bssid` VARCHAR(255) NULL ,
  `signal_strength` INT NULL ,
  `position_sample_id` INT NULL ,
  PRIMARY KEY (`position_signal_id`) ,
  INDEX `position_sample_fk` (`position_sample_id` ASC) ,
  CONSTRAINT `position_sample_fk`
    FOREIGN KEY (`position_sample_id` )
    REFERENCES `findmydb`.`POSITION_SAMPLE` (`position_sample_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `position_accesspoint_fk`
  	FOREIGN KEY (`position_accesspoint_bssid`)
    REFERENCES `POSITION_ACCESSPOINT` (`bssid` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- INSERT into room
INSERT into POSITION_ROOM(name) values('Strossa');
INSERT into POSITION_ROOM(name) values('Storsalen');
-- INSERT into accesspoint
INSERT into POSITION_ACCESSPOINT(bssid) values('strossa');
INSERT into POSITION_ACCESSPOINT(bssid) values('storsalen');
-- INSERT into sample
INSERT into POSITION_SAMPLE(position_room_id) values(1);
INSERT into POSITION_SAMPLE(position_room_id) values(2);
-- INSERT into signal
INSERT into POSITION_SIGNAL(position_accesspoint_bssid, signal_strength, position_sample_id) values('strossa', -30, 1);
INSERT into POSITION_SIGNAL(position_accesspoint_bssid, signal_strength, position_sample_id) values('storsalen', -75, 1);
INSERT into POSITION_SIGNAL(position_accesspoint_bssid, signal_strength, position_sample_id) values('strossa', -80, 2);
INSERT into POSITION_SIGNAL(position_accesspoint_bssid, signal_strength, position_sample_id) values('storsalen', -20, 2);

CREATE table event_showing_real(
	id int,
	showing_time timestamp,
	publish_time timestamp,
	place varchar(30),
	billig_id int,
	event_id int,
	netsale_from timestamp,
	netsale_to timestamp,
	free ENUM('true', 'false'),
	canceled ENUM('true', 'false'),
	entrance_id int,
	PRIMARY KEY(id)
);
INSERT into event_showing_real values(
	11,
	'2011-10-03 13:37:00',
	'2011-10-01 11:37:00',
	'Samfundet',
	0,
	1,
	'2011-10-01 11:37:00',
	'2011-10-21 11:37:00',
	'false',
	'false',
	2222
);
INSERT into event_showing_real values(
	12,
	'2011-10-03 21:00:00',
	'2011-10-01 11:37:00',
	'Lykke',
	0,
	4,
	'2011-10-01 11:37:00',
	'2011-10-21 11:37:00',
	'true',
	'false',
	2222
);
INSERT into event_showing_real values(
	21,
	'2011-10-10 11:00:00',
	'2011-10-07 00:00:00',
	'Dødens dal',
	0,
	2,
	'2011-10-01 00:00:00',
	'2011-10-07 00:00:00',
	'false',
	'false',
	2222
);
INSERT into event_showing_real values(
	22,
	'2011-10-10 18:00:00',
	'2011-10-07 00:00:00',
	'Rundt Dødens dal',
	0,
	5,
	'2011-10-01 00:00:00',
	'2011-10-10 00:00:00',
	'true',
	'false',
	2222
);
INSERT into event_showing_real values(
	31,
	'2011-10-15 20:00:00',
	'2011-10-01 11:37:00',
	'Bodegaen',
	0,
	3,
	'2011-10-01 11:37:00',
	'2011-10-21 11:37:00',
	'false',
	'false',
	2222
);
INSERT into event_showing_real values(
	32,
	'2011-10-15 17:00:00',
	'2011-10-01 11:37:00',
	'Dødens Dal',
	0,
	6,
	'2011-10-01 11:37:00',
	'2011-10-21 11:37:00',
	'false',
	'false',
	2222
);
CREATE table events_event(
	id int,
	title varchar(255),
	lead varchar(255),
	text varchar(255),
	event_type varchar(30),
	image varchar(100),
	thumbnail varchar(100),
	hidden_from_listing ENUM('true', 'false'),
	slug varchar(50),
	age_limit smallint,
	detail_photo_id int, 
  PRIMARY KEY(id)
);
INSERT into events_event values(
	1,
	'Åpning UKA 2011',
	'Offisiell åpning og avduking av UKEnavn',
	'Dette er et arrangement! Som du absolutt ikke trenger å være med på.. relativt kjedelig og navnet får du vite av andre dagen etter..',
	'Konsert',
	'bilde1.jpg',
	'http://localhost/thumb2.jpg',
	'false',
	'slug',
	'23',
	2222
);
INSERT into events_event values(
	2,
	'Oktoberfest',
	'Festen har flyttet fra Munchen til Trondheim og det her det skjer!',
	'Dette blir fest! Ta på lederhosen og kos deg med Das Boot!',
	'fest!',
	'bilde1.jpg',
	'http://localhost/thumb3.jpg',
	'false',
	'slug',
	'23',
	2222
);
INSERT into events_event values(
	3,
	'90-tallsfest',
	'På tide å ta frem klærne fra gotiden!',
	'Dette blir fest! Trondheim fyller opp det meste de finner av rusk og rask og flytter dem ned i Bodegaen slik at de mange studentene fra Gløshaugen skal få prøve seg på dansegulvet :) Garantert masse dyrt øl og vill fest! Fellesnach på Sesam...',
	'fest!',
	'bilde1.jpg',
	'http://localhost/thumb1.jpg',
	'false',
	'slug',
	'23',
	2222
);
INSERT into events_event values(
	4,
	'Velkomstkonsert',
	'UKA 2011 smeller i gang med gratis velkomstkonsert!',
	'Ser frem til en koselig kveld med venner og kjente på Samfundet. UKA er igang og alle er glade!',
	'Konsert',
	'bilde1.jpg',
	'http://localhost/thumb2.jpg',
	'false',
	'slug',
	'23',
	2222
);
INSERT into events_event values(
	5,
	'Opplukk av fulle folk rundt teltet',
	'Oktoberfest er ikke kjent for å være et arrangement for de edrue, og også i år trengs opprydding.',
	'Enten du rydder opp dge selv eller venner.. Eller kanskje du plukker opp en alt for full jente og hjeper henne hjem i trygghet. Still opp. De kommer til å bli mange som trenger hjelp :P',
	'Konsert',
	'bilde1.jpg',
	'http://localhost/thumb2.jpg',
	'false',
	'slug',
	'23',
	2222
);
INSERT into events_event values(
	6,
	'Aqua',
	'Noen skjønner det er på tide å gi seg, mens andre skjønner det ikke...',
	'Aqua fikk tydeligvis ikke nok av Trondheim for 2 år siden og i år er de tilbake igjen. Hopp og sprett og tjo og hei! Dette blir morro!',
	'Konsert',
	'bilde1.jpg',
	'http://localhost/thumb1.jpg',
	'false',
	'slug',
	'23',
	2222
);
