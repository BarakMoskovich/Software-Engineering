CREATE SCHEMA zoomDB;

USE zoomDB;

CREATE TABLE `country` (
  `country_id` int NOT NULL AUTO_INCREMENT,
  `country` varchar(45) NOT NULL,
  PRIMARY KEY (`country_id`)
) ENGINE=InnoDB;

CREATE TABLE `city` (
  `city_id` int NOT NULL,
  `country_id` int NOT NULL,
  `city` varchar(45) NOT NULL,
  PRIMARY KEY (`city_id`),
  KEY `city_country_idx` (`country_id`),
  CONSTRAINT `city_country` FOREIGN KEY (`country_id`) REFERENCES `country` (`country_id`)
) ENGINE=InnoDB;

CREATE TABLE `address` (
  `address_id` int NOT NULL,
  `city_id` int NOT NULL,
  `address` varchar(45) NOT NULL,
  `address2` varchar(45) DEFAULT NULL,
  `district` varchar(45) DEFAULT NULL,
  `postal_code` varchar(45) NOT NULL,
  `last_update` varchar(45) NOT NULL,
  PRIMARY KEY (`address_id`),
  KEY `address_city_idx` (`city_id`),
  CONSTRAINT `address_city_fk` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`)
) ENGINE=InnoDB;

CREATE TABLE `subscription` (
  `subscription_id` int NOT NULL,
  `subscription` varchar(45) NOT NULL DEFAULT 'free',
  `duration` tinyint NOT NULL,
  PRIMARY KEY (`subscription_id`)
) ENGINE=InnoDB;


CREATE TABLE `client` (
  `client_id` int NOT NULL,
  `address_id` int NOT NULL,
  `subscription_id` int NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  `password` varchar(64) NOT NULL,
  `last_update` datetime NOT NULL,
  PRIMARY KEY (`client_id`),
  KEY `client_address_fk_idx` (`address_id`),
  KEY `client_subscription_fk_idx` (`subscription_id`),
  CONSTRAINT `client_address_fk` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`),
  CONSTRAINT `client_subscription_fk` FOREIGN KEY (`subscription_id`) REFERENCES `subscription` (`subscription_id`)
) ENGINE=InnoDB;

CREATE TABLE `contacts` (
  `client1_id` int NOT NULL,
  `client2_id` int NOT NULL,
  `last_update` datetime NOT NULL,
  PRIMARY KEY (`client1_id`,`client2_id`),
  KEY `client2_fk_idx` (`client2_id`),
  CONSTRAINT `client1_fk` FOREIGN KEY (`client1_id`) REFERENCES `client` (`client_id`),
  CONSTRAINT `client2_fk` FOREIGN KEY (`client2_id`) REFERENCES `client` (`client_id`)
) ENGINE=InnoDB;

CREATE TABLE `exposure` (
  `exposure_id` int NOT NULL,
  `exposure` varchar(45) NOT NULL,
  PRIMARY KEY (`exposure_id`)
) ENGINE=InnoDB;

CREATE TABLE `channels` (
  `channel_id` int NOT NULL,
  `exposure_id` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `last_update` datetime NOT NULL,
  PRIMARY KEY (`channel_id`),
  KEY `channel_exposure_fk_idx` (`exposure_id`),
  CONSTRAINT `channel_exposure_fk` FOREIGN KEY (`exposure_id`) REFERENCES `exposure` (`exposure_id`)
) ENGINE=InnoDB;

CREATE TABLE `channel_members` (
  `channel_id` int NOT NULL,
  `client_id` int NOT NULL,
  `last_update` datetime NOT NULL,
  PRIMARY KEY (`channel_id`,`client_id`),
  KEY `members_client_fk_idx` (`client_id`),
  CONSTRAINT `members_channel_fk` FOREIGN KEY (`channel_id`) REFERENCES `channels` (`channel_id`),
  CONSTRAINT `members_client_fk` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`)
) ENGINE=InnoDB;

CREATE TABLE `meeting` (
  `meeting_id` int NOT NULL,
  `title` varchar(45) NOT NULL,
  `date` datetime NOT NULL,
  `max_capacity` int NOT NULL,
  `time_limit` int DEFAULT '40',
  `last_update` datetime NOT NULL,
  PRIMARY KEY (`meeting_id`)
) ENGINE=InnoDB;

CREATE TABLE `messages` (
  `message_id` int NOT NULL,
  `sender_client_id` int NOT NULL,
  `receiver_client_id` int DEFAULT NULL,
  `message` varchar(256) NOT NULL,
  `last_update` datetime NOT NULL,
  PRIMARY KEY (`message_id`),
  KEY `messages_sender_fk_idx` (`sender_client_id`),
  KEY `messages_receiver_fk_idx` (`receiver_client_id`),
  CONSTRAINT `messages_receiver_fk` FOREIGN KEY (`receiver_client_id`) REFERENCES `client` (`client_id`),
  CONSTRAINT `messages_sender_fk` FOREIGN KEY (`sender_client_id`) REFERENCES `client` (`client_id`)
) ENGINE=InnoDB;

CREATE TABLE `permissions` (
  `permission_id` int NOT NULL,
  `permission` varchar(20) NOT NULL DEFAULT 'Viewer',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB;

CREATE TABLE `participants` (
  `client_id` int NOT NULL,
  `meeting_id` int NOT NULL,
  `permission_id` int NOT NULL,
  `last_update` datetime NOT NULL,
  PRIMARY KEY (`client_id`,`meeting_id`),
  KEY `participants_meeting_fk_idx` (`meeting_id`),
  KEY `participants_permissions_fk_idx` (`permission_id`),
  CONSTRAINT `participants_client_fk` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`),
  CONSTRAINT `participants_meeting_fk` FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`meeting_id`),
  CONSTRAINT `participants_permissions_fk` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`permission_id`)
) ENGINE=InnoDB;
