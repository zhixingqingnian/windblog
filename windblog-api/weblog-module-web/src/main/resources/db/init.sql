/*
 Navicat Premium Data Transfer

 Source Server         : zhixing
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : localhost:3306
 Source Schema         : weblog

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 04/08/2025 15:14:05
*/

SET NAMES  utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `title` varchar(120) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '文章标题',
  `cover` varchar(120) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '文章封面',
  `summary` varchar(160) CHARACTER SET  utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '文章摘要',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标志位：0：未删除 1：已删除',
  `read_num` int UNSIGNED NOT NULL DEFAULT 1 COMMENT '被阅读次数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET =  utf8 COLLATE = utf8_general_ci COMMENT = '文章表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for article_category_rel
-- ----------------------------
DROP TABLE IF EXISTS `article_category_rel`;
CREATE TABLE `article_category_rel`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint UNSIGNED NOT NULL COMMENT '文章id',
  `category_id` bigint UNSIGNED NOT NULL COMMENT '分类id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_article_id`(`article_id` ASC) USING BTREE,
  INDEX `idx_category_id`(`category_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET =  utf8 COLLATE = utf8_general_ci COMMENT = '文章所属分类关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for article_content
-- ----------------------------
DROP TABLE IF EXISTS `article_content`;
CREATE TABLE `article_content`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章内容id',
  `article_id` bigint NOT NULL COMMENT '文章id',
  `content` text CHARACTER SET  utf8 COLLATE utf8_general_ci NULL COMMENT '教程正文',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_article_id`(`article_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET =  utf8 COLLATE = utf8_general_ci COMMENT = '文章内容表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for article_tag_rel
-- ----------------------------
DROP TABLE IF EXISTS `article_tag_rel`;
CREATE TABLE `article_tag_rel`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint UNSIGNED NOT NULL COMMENT '文章id',
  `tag_id` bigint UNSIGNED NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_article_id`(`article_id` ASC) USING BTREE,
  INDEX `idx_tag_id`(`tag_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET =  utf8 COLLATE = utf8_general_ci COMMENT = '文章对应标签关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for blog_settings
-- ----------------------------
DROP TABLE IF EXISTS `blog_settings`;
CREATE TABLE `blog_settings`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `logo` varchar(120) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '博客Logo',
  `name` varchar(60) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '博客名称',
  `author` varchar(20) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '作者名',
  `introduction` varchar(120) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '简介',
  `avatar` varchar(120) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '作者头像',
  `github_homepage` varchar(60) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'GitHub 主页访问地址',
  `csdn_homepage` varchar(60) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'CSDN 主页访问地址',
  `gitee_homepage` varchar(60) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'Gitee 主页访问地址',
  `juejin_homepage` varchar(60) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '掘金主页访问地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET =  utf8 COLLATE = utf8_general_ci COMMENT = '博客设置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `name` varchar(60) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '分类名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标志位：0：未删除 1：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET =  utf8 COLLATE = utf8_general_ci COMMENT = '文章分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for statistics_article_pv
-- ----------------------------
DROP TABLE IF EXISTS `statistics_article_pv`;
CREATE TABLE `statistics_article_pv`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pv_date` date NOT NULL COMMENT '被统计的日期',
  `pv_count` bigint UNSIGNED NOT NULL COMMENT 'pv访问量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_pv_date`(`pv_date` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET =  utf8 COLLATE = utf8_general_ci COMMENT = '统计表 - 文章 PV (访问量)' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `name` varchar(60) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '标签名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标志位：0：未删除 1：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET =  utf8 COLLATE = utf8_general_ci COMMENT = '文章标签表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tool
-- ----------------------------
DROP TABLE IF EXISTS `tool`;
CREATE TABLE `tool`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '工具id',
  `name` varchar(60) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '工具名称',
  `icon` varchar(120) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '工具图标',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标志位：0：未删除 1：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET =  utf8 COLLATE = utf8_general_ci COMMENT = '工具表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tool_content
-- ----------------------------
DROP TABLE IF EXISTS `tool_content`;
CREATE TABLE `tool_content`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '工具id',
  `tool_id` bigint NOT NULL COMMENT '所属菜单id',
  `name` varchar(60) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '工具名称',
  `icon` varchar(120) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '工具图标',
  `link` varchar(60) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '工具链接',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标志位：0：未删除 1：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_tool_id`(`tool_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET =  utf8 COLLATE = utf8_general_ci COMMENT = '工具内容' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(60) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(60) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET =  utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(60) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `role` varchar(60) CHARACTER SET  utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET =  utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;

-- zxblog 所需的初始化数据，如登录用户、用户角色、博客设置信息
INSERT INTO `zxblog`.`user` (`id`, `username`, `password`, `create_time`, `update_time`) VALUES (1, 'admin', '$2a$10$uscQxxJ3/oqUECeRed1XXeRNJ.U994gmO5zOpoJVuJxxTPN2BP6YG', '2023-07-03 11:57:18', '2023-09-24 16:23:29');
INSERT INTO `zxblog`.`user` (`id`, `username`, `password`, `create_time`, `update_time`) VALUES (2, 'test', '$2a$10$nV91/34FkbqVStiErUMMAer5MimZXNZws2eYEDNRERQja2ysjZdI2', '2023-07-07 01:22:05', '2023-07-07 01:22:05');
INSERT INTO `zxblog`.`user_role` (`id`, `username`, `role`, `create_time`) VALUES (1, 'admin', 'ROLE_ADMIN', '2025-07-07 01:21:15');
INSERT INTO `zxblog`.`user_role` (`id`, `username`, `role`, `create_time`) VALUES (2, 'test', 'ROLE_VISITOR', '2025-07-07 01:23:33');
INSERT INTO `zxblog`.`blog_settings` (`id`, `logo`, `name`, `author`, `introduction`, `avatar`, `github_homepage`, `csdn_homepage`, `gitee_homepage`, `juejin_homepage`) VALUES (1, 'https://img.windblog.website/zxblog/ui.cn.png', '散客笔记', '知性', '做大做强！再创辉煌！', 'https://img.windblog.website/zxblog/ui.cn.png', '', '', '', '');

