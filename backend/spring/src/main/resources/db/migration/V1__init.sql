CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `last_login` datetime DEFAULT (current_time),
  PRIMARY KEY (`id`)
);

CREATE TABLE `user_auth` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int,
  `token` longtext,
  PRIMARY KEY (`id`),
  FOREIGN KEY(`user_id`)
  REFERENCES users(`id`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `user_information` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int,
  `email` text,
  `password` text,
  `name` text,
  `nickname` text,
  `updated_at` datetime DEFAULT (current_time),
  `created_at` datetime DEFAULT (current_time),
  PRIMARY KEY (`id`),
  FOREIGN KEY(`user_id`)
  REFERENCES users(`id`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `user_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int,
  `permission_id` int,
  PRIMARY KEY (`id`),
  FOREIGN KEY(`user_id`)
  REFERENCES users(`id`) ON UPDATE CASCADE ON DELETE CASCADE
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
  PRIMARY KEY (`id`),
  FOREIGN KEY(`user_id`)
  REFERENCES users(`id`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `user_own_post`(
  `id` int NOT NULL AUTO_INCREMENT,
  `post_id` int,
  `user_id` int,
  `title` text,
  `contents` text,
  `created_at` datetime DEFAULT (current_time),
  `updated_at` datetime DEFAULT (current_time),
  `display_level` text,
  PRIMARY KEY(`id`)
);

CREATE TABLE `post` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int,
  PRIMARY KEY (`id`),
  FOREIGN KEY(`user_id`)
  REFERENCES users(`id`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `post_public` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` text,
  `title` text,
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
  `display_level` text,
  `is_charged` boolean,
  PRIMARY KEY (`id`),
  FOREIGN KEY(`user_id`)
  REFERENCES users(`id`) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY(`post_id`)
  REFERENCES post(`id`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `post_price` (
  `id` int NOT NULL AUTO_INCREMENT,
  `post_id` int,
  `price` int DEFAULT 0,
  PRIMARY KEY (`id`),
  FOREIGN KEY(`post_id`)
  REFERENCES post(`id`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `subscribe_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `subscriber` int,
  `publisher` int,
  `subscribed_date` datetime DEFAULT (current_time),
  `expiration_date` datetime DEFAULT (current_time),
  PRIMARY KEY (`id`),

  FOREIGN KEY(`subscriber`)
  REFERENCES users(`id`) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY(`publisher`)
  REFERENCES users(`id`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `subscribe_post` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int,
  `post_id` int,
  `subscribed_date` datetime DEFAULT (current_time),
  PRIMARY KEY (`id`),

  FOREIGN KEY(`user_id`)
  REFERENCES users(`id`) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY(`post_id`)
  REFERENCES post(`id`) ON UPDATE CASCADE ON DELETE CASCADE
);

