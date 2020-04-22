INSERT INTO `person`(`id`, `e_mail`, `first_name`, `is_blocked`, `last_name`, `password`, `reg_date`) VALUES ('10', 'test1@mail.ru', 'first1', 'N', 'last1', '$2a$10$EqHML.VBGRVxqylVOwETdO3.ueCbsCKF.UPO3AN16l1Fn2pYENPjW', '2020-04-06 06:10:51');
INSERT INTO `person`(`id`, `e_mail`, `first_name`, `is_blocked`, `last_name`, `password`, `reg_date`) VALUES ('12', 'test2@mail.ru', 'first2', 'N', 'last2', '$2a$10$EqHML.VBGRVxqylVOwETdO3.ueCbsCKF.UPO3AN16l1Fn2pYENPjW', '2020-04-06 06:10:51');
INSERT INTO `person`(`id`, `e_mail`, `first_name`, `is_blocked`, `last_name`, `password`, `reg_date`) VALUES ('13', 'test3@mail.ru', 'first3', 'N', 'last3', '$2a$10$EqHML.VBGRVxqylVOwETdO3.ueCbsCKF.UPO3AN16l1Fn2pYENPjW', '2020-04-06 06:10:51');
INSERT INTO `person`(`id`, `e_mail`, `first_name`, `is_blocked`, `last_name`, `password`, `reg_date`) VALUES ('14', 'test4@mail.ru', 'first4', 'N', 'last4', '$2a$10$EqHML.VBGRVxqylVOwETdO3.ueCbsCKF.UPO3AN16l1Fn2pYENPjW', '2020-04-06 06:10:51');

INSERT INTO `roles` (`id`, `name`) VALUES ('50', 'ROLE_USER');

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES ('10', '50');
INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES ('12', '50');
INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES ('13', '50');
INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES ('14', '50');

INSERT INTO `dialog`(`id`) VALUES ('60');
--INSERT INTO `dialog`(`id`) VALUES ('7');

INSERT INTO `dialog_user` VALUES('60', '10');
INSERT INTO `dialog_user` VALUES('60', '12');
--INSERT INTO `dialog_user` VALUES('7', '1');
--INSERT INTO `dialog_user` VALUES('7', '3');

INSERT INTO `message`(`id`, `author_id`, `message_text`, `read_status`, `recipient_id`, `time`, `dialog_id`) VALUES ('90', '10', 'hello world', 'SENT', '12', '2020-04-06 06:07:11', '60');
INSERT INTO `message`(`id`, `author_id`, `message_text`, `read_status`, `recipient_id`, `time`, `dialog_id`) VALUES ('900', '12', 'hello world', 'READ', '10', '2020-04-06 06:07:11', '60');


INSERT INTO `friendship_status` (`id`, `code`, `name`, `time`) VALUES ('30', 'FRIEND', 'Друзья', '2020-04-19 17:02:14');
INSERT INTO `friendship_status` (`id`, `code`, `name`, `time`) VALUES ('31', 'REQUEST', 'Запрос на добавление в друзья', '2020-04-19 18:31:35');

INSERT INTO `friendship` (`id`, `dst_person_id`, `src_person_id`, `status_id`) VALUES ('32', '10', '12', '30');
INSERT INTO `friendship` (`id`, `dst_person_id`, `src_person_id`, `status_id`) VALUES ('33', '10', '13', '30');
INSERT INTO `friendship` (`id`, `dst_person_id`, `src_person_id`, `status_id`) VALUES ('34', '12', '13', '31');


INSERT INTO `post` VALUES ('100', 'N', 'N', 'post text', '2020-04-19 17:02:14', 'Title1', '12');

INSERT INTO `tag` VALUES ('15', 'tag1');

INSERT INTO `post2tag` VALUES ('100', '15');


