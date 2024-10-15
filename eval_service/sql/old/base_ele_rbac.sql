/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 100148
 Source Host           : localhost:3306
 Source Schema         : base_ele_rbac

 Target Server Type    : MySQL
 Target Server Version : 100148
 File Encoding         : 65001

 Date: 07/09/2022 17:11:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `dict_sort` int(255) NULL DEFAULT NULL COMMENT '显示顺序',
  `dict_label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典标签',
  `dict_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典键值',
  `dict_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典类型',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'user_sex', '', NULL);
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'user_sex', '', NULL);
INSERT INTO `sys_dict_data` VALUES (3, 1, 'sdf', 'fgfd', 'check_status', '', NULL);
INSERT INTO `sys_dict_data` VALUES (4, 1, 'video/mp4', '0', 'sys_video', '', NULL);
INSERT INTO `sys_dict_data` VALUES (5, 2, 'm3u8', '1', 'sys_video', '', NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dict_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `dict_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典类型',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'user_sex', '2021-07-28 17:58:16', '');
INSERT INTO `sys_dict_type` VALUES (3, '开关状态', 'check_status', '2021-07-29 09:52:36', '');
INSERT INTO `sys_dict_type` VALUES (4, '视频类型', 'sys_video_type', '2022-09-07 16:36:55', '');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '访问地址',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父级菜单',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `perms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `sort` int(11) NULL DEFAULT NULL COMMENT '显示顺序',
  `iframe` int(255) NULL DEFAULT NULL COMMENT '是否为内置页面',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 70 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (3, 'dd', '系统管理', 0, 'Setting', '0', NULL, 1, 0);
INSERT INTO `sys_menu` VALUES (4, 'system/Menus', '菜单管理', 3, 'Menu', '1', NULL, 1, 0);
INSERT INTO `sys_menu` VALUES (5, 'system/Roles', '角色管理', 3, 'HelpFilled', '1', 'sys:role', 2, 0);
INSERT INTO `sys_menu` VALUES (33, 'system/Users', '用户管理', 3, 'UserFilled', '1', 'system:user:list', 3, 0);
INSERT INTO `sys_menu` VALUES (41, 'base', '基础管理', 0, 'Calendar', '0', '', 2, 0);
INSERT INTO `sys_menu` VALUES (42, 'blacklist', '黑名单记录', 41, 'ios-aperture', '0', 'base:black:list', 1, 0);
INSERT INTO `sys_menu` VALUES (45, 'system/DictType', '字典管理', 3, 'DataAnalysis', '1', 'sys:dict:list', 4, 0);
INSERT INTO `sys_menu` VALUES (46, 'blacklist', '白名单记录', 42, 'ios-aperture', '1', 'base:black:list', 1, 0);
INSERT INTO `sys_menu` VALUES (52, 'genCode', '代码生成', 3, 'Menu', '1', 'ss', 1, 0);
INSERT INTO `sys_menu` VALUES (54, 'personCenter', '个人中心', 0, 'Stamp', '1', 'sd', 3, 0);
INSERT INTO `sys_menu` VALUES (56, '', '新增', 5, 'Menu', '2', 'sys:role:add', 1, 0);
INSERT INTO `sys_menu` VALUES (61, '', '测试目录', 0, 'FullScreen', '0', '', 1, 0);
INSERT INTO `sys_menu` VALUES (63, '', '编辑', 5, 'Menu', '2', 'sys:role:edit', 1, 0);
INSERT INTO `sys_menu` VALUES (64, 'system/SysTest-ele', '测试管理', 61, 'TakeawayBox', '1', '', 1, 0);
INSERT INTO `sys_menu` VALUES (65, '', '新增', 64, 'Menu', '2', 'sysy', 1, 0);
INSERT INTO `sys_menu` VALUES (66, '', '删除不', 64, 'Menu', '2', '222', 1, 0);
INSERT INTO `sys_menu` VALUES (67, '', 'sdf', 46, 'Menu', '2', '岁的法国', 1, 0);
INSERT INTO `sys_menu` VALUES (68, '', '是德国', 46, 'Menu', '2', 'dfg', 1, 0);
INSERT INTO `sys_menu` VALUES (69, 'system/SysVideo', '视频管理', 61, 'View', '1', 'sss', 1, 0);

-- ----------------------------
-- Table structure for sys_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_role`;
CREATE TABLE `sys_menu_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL,
  `menu_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `select_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单-权限关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu_role
-- ----------------------------
INSERT INTO `sys_menu_role` VALUES (5, 5, '3,4,5', NULL);
INSERT INTO `sys_menu_role` VALUES (13, 4, '1,4', NULL);
INSERT INTO `sys_menu_role` VALUES (15, 2, '56,65,5,3,64,61', '56,65');
INSERT INTO `sys_menu_role` VALUES (22, 3, '7,8,10,4,9', NULL);
INSERT INTO `sys_menu_role` VALUES (23, 6, '5,3', '5');
INSERT INTO `sys_menu_role` VALUES (24, 11, '4,5,7,3,6', '4,5,7');
INSERT INTO `sys_menu_role` VALUES (25, 11, '4,5,33,3', '4,5,33');
INSERT INTO `sys_menu_role` VALUES (26, 12, '4,5,33,3', '4,5,33');
INSERT INTO `sys_menu_role` VALUES (27, 13, '5,33,45,3', '5,33,45');
INSERT INTO `sys_menu_role` VALUES (28, 14, '4,5,33,45,3', '4,5,33,45');
INSERT INTO `sys_menu_role` VALUES (29, 15, '33,3', '33');
INSERT INTO `sys_menu_role` VALUES (30, 16, '45,50,3', '45,50');
INSERT INTO `sys_menu_role` VALUES (31, 17, '33,45,3', '33,45');
INSERT INTO `sys_menu_role` VALUES (32, 18, '33,50,3', '33,50');
INSERT INTO `sys_menu_role` VALUES (40, 38, '61,62,0', '61,62');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色权限字符串',
  `role_sort` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ROLE_SUPER_ADMIN', 1, '2021-07-22 09:18:20', NULL);
INSERT INTO `sys_role` VALUES (2, '人事专员', 'ROLE_PERSON', 3, '2021-07-21 09:18:23', NULL);
INSERT INTO `sys_role` VALUES (3, '招聘主管', 'ROLE_recruiter', 4, '2021-07-07 09:18:28', NULL);
INSERT INTO `sys_role` VALUES (4, '培训主管', 'ROLE_train', 5, '2021-07-13 09:18:30', NULL);
INSERT INTO `sys_role` VALUES (5, '薪酬绩效主管', 'ROLE_performance', 6, '2021-07-11 09:18:35', NULL);
INSERT INTO `sys_role` VALUES (9, '管理员', 'ADMIN', 7, '2021-07-22 11:13:41', '');

-- ----------------------------
-- Table structure for sys_test
-- ----------------------------
DROP TABLE IF EXISTS `sys_test`;
CREATE TABLE `sys_test`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名字',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '测试生成表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_test
-- ----------------------------
INSERT INTO `sys_test` VALUES (1, 'asd', 1, '士大夫');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名（手机号）',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `role` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$hGHXhwZvHa3cPSBcsw5l2ON.o70rw7r6kT7pZqxZa1dieMnVEl2HC', '1,2', NULL, '2021-07-21 16:25:07');
INSERT INTO `sys_user` VALUES (6, 'user', '$2a$10$9js5P2t54NJtWieJ.3ZO1.8blkugIVqAsRVGFctIBeNvc45vx9EF.', '2', NULL, '2021-07-09 16:25:10');
INSERT INTO `sys_user` VALUES (25, 'user2', '$2a$10$tOa3NVUWoM1z5EFQl2i3vec4R.IDExsm7GTcVw3ikxc21QOpYfpsu', '3', '', '2022-05-24 08:20:07');

-- ----------------------------
-- Table structure for sys_video
-- ----------------------------
DROP TABLE IF EXISTS `sys_video`;
CREATE TABLE `sys_video`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '摄像头名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '播放地址',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '位置',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `type` int(255) NULL DEFAULT NULL COMMENT '视频类型',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_video
-- ----------------------------
INSERT INTO `sys_video` VALUES (1, '测试设备1', 'https://cdn.jsdelivr.net/gh/xdlumia/files/video-play/IronMan.mp4', '公司测试', '', NULL, '2022-09-07 00:00:00');

SET FOREIGN_KEY_CHECKS = 1;
