SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for exec_log
-- ----------------------------
DROP TABLE IF EXISTS `exec_log`;
CREATE TABLE `exec_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `monitor_id` bigint(20) NOT NULL,
  `exec_user` varchar(32) NOT NULL COMMENT '执行用户： system 或用户名',
  `exec_time` datetime NOT NULL COMMENT '执行时间',
  `result_count` bigint(20) DEFAULT NULL COMMENT '返回值或返回行数',
  `result` longtext COMMENT '返回值 JSON序列化格式',
  `status` varchar(20) NOT NULL COMMENT '状态 ENABLE,DISABLE',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `create_user` varchar(32) NOT NULL,
  `update_user` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Idx_Monitor_MonitorId` (`monitor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exec_log
-- ----------------------------

-- ----------------------------
-- Table structure for monitor
-- ----------------------------
DROP TABLE IF EXISTS `monitor`;
CREATE TABLE `monitor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `db_source` varchar(255) NOT NULL,
  `sql` text NOT NULL,
  `cron_expression` varchar(255) DEFAULT NULL,
  `script_type` varchar(255) DEFAULT NULL,
  `script_code` text,
  `emails` varchar(1024) DEFAULT NULL,
  `phones` varchar(1024) DEFAULT NULL,
  `email_threshold` bigint(20) DEFAULT NULL,
  `phone_threshold` bigint(20) DEFAULT NULL,
  `notify_title` varchar(255) DEFAULT NULL COMMENT '通知标题',
  `remark` varchar(1024) DEFAULT NULL,
  `run_status` varchar(32) NOT NULL COMMENT 'RUNNING,STOPPED',
  `last_fire_time` datetime DEFAULT NULL COMMENT '上次运行时间',
  `next_fire_time` datetime DEFAULT NULL COMMENT '下次运行时间',
  `status` varchar(20) NOT NULL COMMENT '状态 ENABLE,DISABLE',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `create_user` varchar(32) NOT NULL,
  `update_user` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Uk_ds_name` (`name`),
  KEY `Idx_ds_dbSourceId` (`db_source`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(32) NOT NULL COMMENT '用户名',
  `email` varchar(32) DEFAULT NULL COMMENT '用户邮箱',
  `password` varchar(64) DEFAULT NULL COMMENT '用户密码 SSO登录时为空',
  `role` int(11) NOT NULL COMMENT '用户角色',
  `status` varchar(20) NOT NULL COMMENT '状态 ENABLE,DISABLE',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `create_user` varchar(32) NOT NULL,
  `update_user` varchar(32) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `Idx_User_UserName` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', null, '21232f297a57a5a743894a0e4a801fc3', '0', '', '2016-08-03 11:21:44', '2016-08-03 11:21:44', '', '');
