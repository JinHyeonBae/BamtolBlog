CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nickname` varchar(40) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `permission_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_permission_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user_information` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `password` text,
  `name` varchar(40) DEFAULT NULL,
  `nickname` varchar(40) DEFAULT NULL,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_login` datetime DEFAULT CURRENT_TIMESTAMP,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `nickname_UNIQUE` (`nickname`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_information_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user_auth` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `token` longtext,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_auth_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `posts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `subscribe_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `subscriber` int,
  `publisher` int,
  `subscribed_date` datetime DEFAULT (current_timestamp),
  `expiration_date` datetime DEFAULT (current_timestamp),
  PRIMARY KEY (`id`),

  FOREIGN KEY(`subscriber`)
  REFERENCES users(`id`) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY(`publisher`)
  REFERENCES users(`id`) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE `subscribe_post` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `post_id` int DEFAULT NULL,
  `subscribed_date` datetime DEFAULT (curtime()),
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `subscribe_post_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `subscribe_post_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `permission` (
  `id` int NOT NULL,
  `role_comment` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `post_information` (
  `id` int NOT NULL AUTO_INCREMENT,
  `post_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `title` text,
  `contents` text,
  `created_at` datetime DEFAULT (curtime()),
  `updated_at` datetime DEFAULT (curtime()),
  `display_level` varchar(7) DEFAULT NULL,
  `price` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `post_information_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `post_information_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `post_permission` (
  `id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `post_id` int DEFAULT NULL,
  `permission_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `post_id` (`post_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `post_permission_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `post_permission_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `post_permission_ibfk_3` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `post_public` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` text,
  `title` text,
  `created_at` datetime DEFAULT (curtime()),
  `contents` longtext,
  `comments` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
