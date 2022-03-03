create database if not exists calorie_count;

use calorie_count;

CREATE TABLE `users` (
  `user_id` varchar(12) NOT NULL,
  `name` varchar(100) NOT NULL,
  `type` varchar(25) NOT NULL,
  `email` varchar(254) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `password` varchar(256) NOT NULL,
  `reset_token` varchar(100) DEFAULT NULL,
  `reset_token_timestamp` datetime DEFAULT NULL,
  `created_by` varchar(12) DEFAULT NULL,
  `created_timestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`)
);

CREATE TABLE `entry` (
`entry_id` varchar(12) NOT NULL,
`eat_time` varchar(20) NOT NULL,
`food_name` varchar(250) NOT NULL,
`calories` float NOT NULL,
`user_id` varchar(12) NOT NULL,
`creation_timestamp` timestamp not null,
`last_updated_timestamp` timestamp default null,
PRIMARY KEY(`entry_id`),
CONSTRAINT `FK_Entry_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE `calorie_limit` (
	`user_id` varchar(12) NOT NULL PRIMARY KEY,
    `basic_limit` float DEFAULT 2100,
    `carbs` float default 800,
    `protein` float default 800,
    `fat` float default 500,
    `height` float default null,
    `weight` float default null,
    `last_updated_timestamp` timestamp default  null,
    CONSTRAINT `FK_limit_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
);