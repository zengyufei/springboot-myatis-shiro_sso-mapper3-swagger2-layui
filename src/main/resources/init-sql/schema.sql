SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_module`
-- ----------------------------
DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE `t_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11),
  `name` varchar(32) DEFAULT NULL,
  `res_icon` varchar(50) DEFAULT NULL,
  `url` varchar(50) DEFAULT NULL,
  `res_level` int(2) DEFAULT NULL COMMENT '1.URL, 2.功能',
  `permission` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_module
-- ----------------------------
INSERT INTO `t_resource` VALUES ('1', '0', '系统管理', '/static/images/0.jpg', '', '1', 'sys:*', '2016-06-01 23:41:39', '2016-06-01 23:41:39');
INSERT INTO `t_resource` VALUES ('2', '0', '订单管理', '/static/images/0.jpg', '', '1', 'order:*', '2016-06-01 23:41:39', '2016-06-01 23:41:39');
INSERT INTO `t_resource` VALUES ('10', '1', '系统用户管理', '/static/images/0.jpg', '/pages/html/sys/user.html', '2', 'sys:user:*', '2016-06-03 21:42:17', '2016-06-01 23:41:39');
INSERT INTO `t_resource` VALUES ('11', '1', '用户角色管理', '/static/images/0.jpg', '/pages/html/sys/role.html', '2', 'sys:role:*', '2016-06-03 21:42:17', '2016-06-01 23:41:39');
INSERT INTO `t_resource` VALUES ('12', '1', '资源管理', '/static/images/0.jpg', '/pages/html/sys/resource.html', '2', 'sys:resource:*', '2016-06-03 21:42:17', '2016-06-01 23:41:39');
INSERT INTO `t_resource` VALUES ('13', '10', '添加', '', '/user/index', '3', 'sys:user:add', '2016-06-03 21:42:17', '2016-06-01 23:41:39');
INSERT INTO `t_resource` VALUES ('14', '10', '修改', '', '/user/update', '3', 'sys:user:update', '2016-06-03 21:42:17', '2016-06-01 23:41:39');
INSERT INTO `t_resource` VALUES ('15', '10', '删除', '', '/user/delete', '3', 'sys:user:delete', '2016-06-03 21:42:17', '2016-06-01 23:41:39');
INSERT INTO `t_resource` VALUES ('16', '11', '添加', '', '/role/index', '3', 'sys:role:add', '2016-06-03 21:42:17', '2016-06-01 23:41:39');
INSERT INTO `t_resource` VALUES ('17', '11', '修改', '', '/role/update', '3', 'sys:role:update', '2016-06-03 21:42:17', '2016-06-01 23:41:39');
INSERT INTO `t_resource` VALUES ('18', '11', '删除', '', '/role/delete', '3', 'sys:role:delete', '2016-06-03 21:42:17', '2016-06-01 23:41:39');
INSERT INTO `t_resource` VALUES ('19', '12', '添加', '', '/resource/index', '3', 'sys:resource:add', '2016-06-03 21:42:17', '2016-06-01 23:41:39');
INSERT INTO `t_resource` VALUES ('20', '12', '修改', '', '/resource/update', '3', 'sys:resource:update', '2016-06-03 21:42:17', '2016-06-01 23:41:39');
INSERT INTO `t_resource` VALUES ('21', '12', '删除', '', '/resource/delete', '3', 'sys:resource:delete', '2016-06-03 21:42:17', '2016-06-01 23:41:39');
INSERT INTO `t_resource` VALUES ('22', '2', '订单管理', '/static/images/0.jpg', '/pages/html/sys/order.html', '2', 'order:asc:*', '2016-06-03 21:42:17', '2016-06-01 23:41:39');
INSERT INTO `t_resource` VALUES ('23', '22', '添加', '', '/order/index', '3', 'order:asc:add', '2016-06-03 21:42:17', '2016-06-01 23:41:39');
INSERT INTO `t_resource` VALUES ('24', '22', '修改', '', '/order/update', '3', 'order:asc:update', '2016-06-03 21:42:17', '2016-06-01 23:41:39');
INSERT INTO `t_resource` VALUES ('25', '22', '删除', '', '/order/delete', '3', 'order:asc:delete', '2016-06-03 21:42:17', '2016-06-01 23:41:39');

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `resource_id` varchar(1000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `pid` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '管理员', '系统管理员', 'all', '2016-06-01 23:41:11', '2016-06-01 23:41:11', 0);
INSERT INTO `t_role` VALUES ('2', '二级管理员1', '下属管理员', '1,4,5,6,7', '2016-06-01 23:41:11', '2016-06-01 23:41:11', 1);
INSERT INTO `t_role` VALUES ('3', '二级管理员2', '下属管理员', '1,4,5,6,7', '2016-06-01 23:41:11', '2016-06-01 23:41:11', 1);
INSERT INTO `t_role` VALUES ('4', '二级管理员3', '下属管理员', '1,4,5,6,7', '2016-06-01 23:41:11', '2016-06-01 23:41:11', 1);
INSERT INTO `t_role` VALUES ('5', '三级管理员1', '下属下属管理员', '1,4,5,6,7', '2016-06-01 23:41:11', '2016-06-01 23:41:11', 2);
INSERT INTO `t_role` VALUES ('6', '三级管理员2', '下属下属管理员', '1,4,5,6,7', '2016-06-01 23:41:11', '2016-06-01 23:41:11', 2);
INSERT INTO `t_role` VALUES ('7', '三级管理员3', '下属下属管理员', '1,4,5,6,7', '2016-06-01 23:41:11', '2016-06-01 23:41:11', 2);
INSERT INTO `t_role` VALUES ('8', '四级管理员1', '下属下属管理员', '1,4,5,6,7', '2016-06-01 23:41:11', '2016-06-01 23:41:11', 5);
INSERT INTO `t_role` VALUES ('9', '四级管理员2', '下属下属管理员', '1,4,5,6,7', '2016-06-01 23:41:11', '2016-06-01 23:41:11', 5);
INSERT INTO `t_role` VALUES ('10', '四级管理员1', '下属下属管理员', '1,4,5,6,7', '2016-06-01 23:41:11', '2016-06-01 23:41:11', 6);
INSERT INTO `t_role` VALUES ('11', '四级管理员2', '下属下属管理员', '1,4,5,6,7', '2016-06-01 23:41:11', '2016-06-01 23:41:11', 6);
INSERT INTO `t_role` VALUES ('12', '三级管理员4', '下属下属管理员', '1,4,5,6,7', '2016-06-01 23:41:11', '2016-06-01 23:41:11', 3);
INSERT INTO `t_role` VALUES ('13', '三级管理员5', '下属下属管理员', '1,4,5,6,7', '2016-06-01 23:41:11', '2016-06-01 23:41:11', 3);

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `role_id` varchar(4) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `user_type` TINYINT(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', 'f6fdffe48c908deb0f4c3bd36c032e72', '1', 'Admin', 1, '2016-06-01 23:35:17', '2016-06-01 23:35:17');
INSERT INTO `t_user` VALUES (2, 'admin1', 'f6fdffe48c908deb0f4c3bd36c032e72', '1', 'Admin1', 1, '2016-06-01 23:35:17', '2016-06-01 23:35:17');
INSERT INTO `t_user` VALUES (3, 'admin2', 'f6fdffe48c908deb0f4c3bd36c032e72', '1', 'Admin2', 1, '2016-06-01 23:35:17', '2016-06-01 23:35:17');


