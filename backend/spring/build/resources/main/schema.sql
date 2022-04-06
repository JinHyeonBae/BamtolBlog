CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` text,
  `password` text,
  `created_at` datetime DEFAULT (current_time),
  `last_login` datetime DEFAULT (current_time),
  PRIMARY KEY (`id`)
);

CREATE TABLE `user_auth` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int,
  `token` longtext,
  PRIMARY KEY (`id`)
);

CREATE TABLE `user_information` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int,
  `email` text,
  `name` text,
  `nickname` text,
  `updated_at` datetime DEFAULT (current_time),
  PRIMARY KEY (`id`)
);

CREATE TABLE `user_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int,
  `permission_id` int,
  PRIMARY KEY (`id`)
);

CREATE TABLE `permission` (
  `id` int NOT NULL,
  `role` int,
  `role_comment` text,
  PRIMARY KEY (`id`)
);

CREATE TABLE `user_price` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int,
  `price` int DEFAULT 0,
  PRIMARY KEY (`id`)
);

CREATE TABLE `post` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int,
  `pay` boolean,
  PRIMARY KEY (`id`)
);

CREATE TABLE `post_public` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` text,
  `title` text,
  `user_id` int,
  `created_at` datetime DEFAULT (current_time),
  `contents` longtext,
  `comments` text,
  PRIMARY KEY (`id`)
);

CREATE TABLE `post_information` (
  `id` int NOT NULL AUTO_INCREMENT,
  `post_id` int,
  `user_id` int,
  `title` text,
  `contents` text,
  `created_at` datetime DEFAULT (current_time),
  `updated_at` datetime DEFAULT (current_time),
  PRIMARY KEY (`id`)
);

CREATE TABLE `post_price` (
  `id` int NOT NULL AUTO_INCREMENT,
  `post_id` int,
  `price` int DEFAULT 0,
  PRIMARY KEY (`id`)
);

CREATE TABLE `subscribe_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `subscriber` int,
  `publisher` int,
  `subscribed_date` datetime DEFAULT (current_time),
  `expiration_date` datetime DEFAULT (current_time),
  PRIMARY KEY (`id`)
);

CREATE TABLE `subscribe_post` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int,
  `post_id` int,
  `subscribed_date` datetime DEFAULT (current_time),
  PRIMARY KEY (`id`)
);

ALTER TABLE `user_auth` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `user_information` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `user_permission` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `user_permission` ADD FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`);

ALTER TABLE `user_price` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `post` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `post_public` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `post_information` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `post_information` ADD FOREIGN KEY (`post_id`) REFERENCES `post` (`id`);

ALTER TABLE `post_price` ADD FOREIGN KEY (`post_id`) REFERENCES `post` (`id`);

ALTER TABLE `subscribe_post` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `subscribe_post` ADD FOREIGN KEY (`post_id`) REFERENCES `post` (`id`);

ALTER TABLE `subscribe_user` ADD FOREIGN KEY (`subscriber`) REFERENCES `users` (`id`);

ALTER TABLE `subscribe_user` ADD FOREIGN KEY (`publisher`) REFERENCES `users` (`id`);
