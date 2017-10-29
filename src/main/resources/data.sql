INSERT INTO `user` (`id`, `email`, `username`, `password`, `role`, `enabled`) VALUES
(NULL, 'tomek@ignaszak.net', 'tomek', '$2a$10$NN/mlB8gJ9.ionWjxSZZ2Ob5iNuR7O7ktYb3/DSIN2pkn1JLf9RX6', 'ADMIN', 1), -- pass: tomek123
(NULL, 'test@ignaszak.net', 'test', '$2a$10$2ca8QFZHceCboBhXSSnsuuOJh7.8l9E8VnRlr1btSIoXl3qMGqmvq', 'USER', 1), -- pass: test1234
(NULL, 'zygmunt@ignaszak.net', 'zygmunt', '$2a$10$2ca8QFZHceCboBhXSSnsuuOJh7.8l9E8VnRlr1btSIoXl3qMGqmvq', 'USER', 1); -- pass: test1234

INSERT INTO `relation` (`id`, `key`, `sender_id`, `receiver_id`, `accepted`, `invitation_date`, `accepted_date`) VALUES
(NULL, '1-2', 1, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO `post` (`id`, `author_id`, `receiver_id`, `text`, `created_date`) VALUES
(NULL, 1, 1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus bibendum quam a ligula suscipit, nec iaculis lacus cursus.', CURRENT_TIMESTAMP),
(NULL, 2, 2, 'Mauris sed turpis diam. Sed elit eros, aliquam id imperdiet fringilla, ornare eu est.', CURRENT_TIMESTAMP),
(NULL, 2, 3, 'Mauris sed turpis diam. Sed elit eros, aliquam id imperdiet fringilla, ornare eu est.', CURRENT_TIMESTAMP);

INSERT INTO `comment` (`id`, `author_id`, `post_id`, `text`, `created_date`) VALUES
(NULL, 1, 1, 'Lorem ipsum dolor sit amet', CURRENT_TIMESTAMP),
(NULL, 1, 1, 'consectetur adipiscing elit', CURRENT_TIMESTAMP),
(NULL, 2, 1, 'Vivamus bibendum quam a ligula suscipit', CURRENT_TIMESTAMP),
(NULL, 1, 2, 'Mauris sed turpis diam', CURRENT_TIMESTAMP),
(NULL, 3, 2, 'Aliquam id imperdiet fringilla', CURRENT_TIMESTAMP),
(NULL, 2, 2, 'Sed elit eros', CURRENT_TIMESTAMP);