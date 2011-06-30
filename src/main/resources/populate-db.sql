CREATE table program(id int AUTO_INCREMENT, day date, PRIMARY KEY(id));
INSERT into program(dday) values('2010-01-01');
INSERT into program(dday) values('2010-02-05');

CREATE  TABLE `POSITION_ROOM` (
  `position_room_id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(20) NULL ,
  PRIMARY KEY (`position_room_id`) );

CREATE  TABLE `POSITION_ACCESSPOINT` (
  `position_accesspoint_id` INT NOT NULL AUTO_INCREMENT ,
  `bssid` VARCHAR(255) NULL ,
  PRIMARY KEY (`position_accesspoint_id`) );
  
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
  `position_accesspoint_id` INT(11) NULL ,
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
  	FOREIGN KEY (`position_accesspoint_id`)
    REFERENCES `POSITION_ACCESSPOINT` (`position_accesspoint_id` )
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
INSERT into POSITION_SIGNAL(position_accesspoint_id, signal_strength, position_sample_id) values(1, -30, 1);
INSERT into POSITION_SIGNAL(position_accesspoint_id, signal_strength, position_sample_id) values(2, -75, 1);
INSERT into POSITION_SIGNAL(position_accesspoint_id, signal_strength, position_sample_id) values(1, -80, 2);
INSERT into POSITION_SIGNAL(position_accesspoint_id, signal_strength, position_sample_id) values(2, -20, 2);

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
	21,
	'2011-10-01 00:00:00',
	'2011-10-07 00:00:00',
	'Dodens dal',
	0,
	2,
	'2011-10-01 00:00:00',
	'2011-10-07 00:00:00',
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
	'Konsert 1',
	'Lead 1',
	'Dette er et arrangement!',
	'Konsert',
	'bilde1.jpg',
	'thumb1.jpg',
	'false',
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
	'false',
	'slug',
	'23',
	2222
);
