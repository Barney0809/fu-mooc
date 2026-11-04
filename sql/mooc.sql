-- =====================================================================
--  fu-mooc 数据库初始化脚本
-- =====================================================================

SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS `mooc`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE `mooc`;

-- 无外键约束：业务代码自己维护级联删除（删课程时手动删章节/小节），
-- 加外键反而会让现有删除逻辑报错。

DROP TABLE IF EXISTS `test`;
DROP TABLE IF EXISTS `sms`;
DROP TABLE IF EXISTS `member_course`;
DROP TABLE IF EXISTS `member`;
DROP TABLE IF EXISTS `role_resource`;
DROP TABLE IF EXISTS `role_user`;
DROP TABLE IF EXISTS `resource`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `file`;
DROP TABLE IF EXISTS `course_content_file`;
DROP TABLE IF EXISTS `course_content`;
DROP TABLE IF EXISTS `course_category`;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `section`;
DROP TABLE IF EXISTS `chapter`;
DROP TABLE IF EXISTS `course`;
DROP TABLE IF EXISTS `teacher`;


-- ---------------------------------------------------------------------
--  课程域
-- ---------------------------------------------------------------------

-- 讲师
CREATE TABLE `teacher` (
  `id`       char(8)      NOT NULL COMMENT 'id',
  `name`     varchar(50)  DEFAULT NULL COMMENT '姓名',
  `nickname` varchar(50)  DEFAULT NULL COMMENT '昵称',
  `image`    varchar(200) DEFAULT NULL COMMENT '头像',
  `position` varchar(50)  DEFAULT NULL COMMENT '职位',
  `motto`    varchar(50)  DEFAULT NULL COMMENT '座右铭',
  `intro`    varchar(500) DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='讲师';

-- 课程
CREATE TABLE `course` (
  `id`         char(8)       NOT NULL COMMENT 'id',
  `name`       varchar(50)   NOT NULL COMMENT '名称',
  `summary`    varchar(200)  DEFAULT NULL COMMENT '概述',
  `time`       int           DEFAULT '0' COMMENT '时长，秒',
  `price`      decimal(10,2) DEFAULT '0.00' COMMENT '价格，元',
  `image`      varchar(200)  DEFAULT NULL COMMENT '封面',
  `level`      char(1)       DEFAULT '1' COMMENT '级别：1初级 2中级 3高级',
  `charge`     char(1)       DEFAULT 'F' COMMENT '收费：C收费 F免费',
  `status`     char(1)       DEFAULT 'D' COMMENT '状态：P发布 D草稿',
  `enroll`     int           DEFAULT '0' COMMENT '报名数',
  `sort`       int           DEFAULT '0' COMMENT '顺序',
  `created_at` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `teacher_id` char(8)       DEFAULT NULL COMMENT '讲师id',
  PRIMARY KEY (`id`),
  KEY `idx_course_teacher` (`teacher_id`),
  KEY `idx_course_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程';

-- 大章
CREATE TABLE `chapter` (
  `id`        char(8)     NOT NULL COMMENT 'id',
  `course_id` char(8)     DEFAULT NULL COMMENT '课程id',
  `name`      varchar(50) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`),
  KEY `idx_chapter_course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='大章';

-- 小节
CREATE TABLE `section` (
  `id`         char(8)      NOT NULL COMMENT 'id',
  `title`      varchar(50)  DEFAULT NULL COMMENT '标题',
  `course_id`  char(8)      DEFAULT NULL COMMENT '课程id',
  `chapter_id` char(8)      DEFAULT NULL COMMENT '大章id',
  `video`      varchar(200) DEFAULT NULL COMMENT '视频地址',
  `time`       int          DEFAULT '0' COMMENT '时长，秒',
  `charge`     char(1)      DEFAULT 'F' COMMENT '收费：C收费 F免费',
  `sort`       int          DEFAULT '0' COMMENT '顺序',
  `created_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `vod`        char(32)     DEFAULT NULL COMMENT '阿里云VOD视频id',
  PRIMARY KEY (`id`),
  KEY `idx_section_course` (`course_id`),
  KEY `idx_section_chapter` (`chapter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小节';

-- 分类
CREATE TABLE `category` (
  `id`     char(8)     NOT NULL COMMENT 'id',
  `parent` char(8)     NOT NULL DEFAULT '00000000' COMMENT '父id，00000000表示一级分类',
  `name`   varchar(50) DEFAULT NULL COMMENT '名称',
  `sort`   int         DEFAULT '0' COMMENT '顺序',
  PRIMARY KEY (`id`),
  KEY `idx_category_parent` (`parent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分类';

-- 课程-分类 关联
CREATE TABLE `course_category` (
  `id`          char(8) NOT NULL COMMENT 'id',
  `course_id`   char(8) DEFAULT NULL COMMENT '课程id',
  `category_id` char(8) DEFAULT NULL COMMENT '分类id',
  PRIMARY KEY (`id`),
  KEY `idx_cc_course` (`course_id`),
  KEY `idx_cc_category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程分类';

-- 课程内容（富文本，主键与课程id相同）
CREATE TABLE `course_content` (
  `id`      char(8) NOT NULL COMMENT '课程id',
  `content` mediumtext COMMENT '课程内容，HTML',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程内容';

-- 课程内容附件
CREATE TABLE `course_content_file` (
  `id`        char(8)      NOT NULL COMMENT 'id',
  `course_id` char(8)      DEFAULT NULL COMMENT '课程id',
  `url`       varchar(200) DEFAULT NULL COMMENT '地址',
  `name`      varchar(100) DEFAULT NULL COMMENT '文件名',
  `size`      int          DEFAULT NULL COMMENT '大小，byte',
  PRIMARY KEY (`id`),
  KEY `idx_ccf_course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程内容文件';

-- ---------------------------------------------------------------------
--  文件 / 上传
-- ---------------------------------------------------------------------

-- 文件（含分片上传信息）
CREATE TABLE `file` (
  `id`          char(8)      NOT NULL COMMENT 'id',
  `path`        varchar(200) DEFAULT NULL COMMENT '相对路径',
  `name`        varchar(100) DEFAULT NULL COMMENT '文件名',
  `suffix`      varchar(10)  DEFAULT NULL COMMENT '后缀',
  `size`        int          DEFAULT NULL COMMENT '大小，byte',
  `use`         char(1)      DEFAULT NULL COMMENT '用途：C课程 T讲师',
  `created_at`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `shard_index` int          DEFAULT NULL COMMENT '分片下标',
  `shard_size`  int          DEFAULT NULL COMMENT '分片大小',
  `shard_total` int          DEFAULT NULL COMMENT '分片总数',
  `key`         varchar(100) DEFAULT NULL COMMENT '文件唯一标识，md5',
  `vod`         char(32)     DEFAULT NULL COMMENT '阿里云VOD视频id',
  PRIMARY KEY (`id`),
  KEY `idx_file_key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件';

-- ---------------------------------------------------------------------
--  系统管理：用户 / 角色 / 资源
-- ---------------------------------------------------------------------

-- 后台用户
CREATE TABLE `user` (
  `id`         char(8)     NOT NULL COMMENT 'id',
  `login_name` varchar(50) NOT NULL COMMENT '登录名',
  `name`       varchar(50) DEFAULT NULL COMMENT '昵称',
  `password`   char(32)    NOT NULL COMMENT '密码，md5二次加密',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_login_name` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台用户';

-- 角色
CREATE TABLE `role` (
  `id`   char(8)      NOT NULL COMMENT 'id',
  `name` varchar(50)  DEFAULT NULL COMMENT '角色名',
  `desc` varchar(100) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- 资源（菜单 + 接口权限），id 形如 01 / 0101，前台菜单按 id 硬编码判断
CREATE TABLE `resource` (
  `id`      char(8)      NOT NULL COMMENT 'id',
  `name`    varchar(50)  DEFAULT NULL COMMENT '名称',
  `page`    varchar(100) DEFAULT NULL COMMENT '页面路径，如 /admin/system/user',
  `request` varchar(500) DEFAULT NULL COMMENT '接口权限，JSON数组，如 ["admin/user"]',
  `parent`  char(8)      NOT NULL DEFAULT '' COMMENT '父id，空串表示一级',
  PRIMARY KEY (`id`),
  KEY `idx_resource_parent` (`parent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源';

-- 角色-资源 关联
CREATE TABLE `role_resource` (
  `id`          char(8) NOT NULL COMMENT 'id',
  `role_id`     char(8) DEFAULT NULL COMMENT '角色id',
  `resource_id` char(8) DEFAULT NULL COMMENT '资源id',
  PRIMARY KEY (`id`),
  KEY `idx_rr_role` (`role_id`),
  KEY `idx_rr_resource` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色资源';

-- 角色-用户 关联
CREATE TABLE `role_user` (
  `id`      char(8) NOT NULL COMMENT 'id',
  `role_id` char(8) DEFAULT NULL COMMENT '角色id',
  `user_id` char(8) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`),
  KEY `idx_ru_role` (`role_id`),
  KEY `idx_ru_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色用户';

-- ---------------------------------------------------------------------
--  会员 / 短信
-- ---------------------------------------------------------------------

-- 前台会员
CREATE TABLE `member` (
  `id`            char(8)      NOT NULL COMMENT 'id',
  `mobile`        varchar(11)  NOT NULL COMMENT '手机号',
  `password`      char(32)     NOT NULL COMMENT '密码，md5二次加密',
  `name`          varchar(50)  DEFAULT NULL COMMENT '昵称',
  `photo`         varchar(200) DEFAULT NULL COMMENT '头像',
  `register_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_member_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员';

-- 会员-课程 报名关联
CREATE TABLE `member_course` (
  `id`        char(8)  NOT NULL COMMENT 'id',
  `member_id` char(8)  DEFAULT NULL COMMENT '会员id',
  `course_id` char(8)  DEFAULT NULL COMMENT '课程id',
  `at`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
  PRIMARY KEY (`id`),
  KEY `idx_mc_member` (`member_id`),
  KEY `idx_mc_course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员课程';

-- 短信验证码
CREATE TABLE `sms` (
  `id`     char(8)     NOT NULL COMMENT 'id',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `code`   char(6)     DEFAULT NULL COMMENT '验证码',
  `use`    char(1)     DEFAULT NULL COMMENT '用途：R注册 F忘记密码',
  `at`     datetime    DEFAULT NULL COMMENT '生成时间',
  `status` char(1)     DEFAULT NULL COMMENT '状态：U已使用 N未使用',
  PRIMARY KEY (`id`),
  KEY `idx_sms_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信';

-- 示例表（TestMapper 依赖，可自行删除）
CREATE TABLE `test` (
  `id`   int         NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试';


-- =====================================================================
--  初始化数据
-- =====================================================================
--  控制台账号：admin / admin123
--  前台会员：  13800000000 / member123
--
--  密码规则（前端 public/static/js/md5.js 里 KEY = "!@#QWERT"）：
--    入库值 = md5( md5(明文 + KEY) )
--  改密码时按同样规则算，或直接控制台里改。
-- =====================================================================

-- 资源：id 必须和 admin/src/views/admin.vue 里 hasResource('xx') 一致
INSERT INTO `resource` (`id`, `name`, `page`, `request`, `parent`) VALUES
('01',   '系统管理', '',                        '[]', ''),
('0101', '用户管理', '/admin/system/user',      '["admin/user"]', '01'),
('0102', '资源管理', '/admin/system/resource',  '["admin/resource"]', '01'),
('0103', '角色管理', '/admin/system/role',      '["admin/role"]', '01'),
('02',   '业务管理', '',                        '[]', ''),
('0201', '分类管理', '/admin/business/category','["admin/category"]', '02'),
('0202', '课程管理', '/admin/business/course',  '["admin/course","admin/chapter","admin/section","admin/courseContentFile","admin/teacher","admin/category","admin/upload","admin/oss","admin/vod","admin/check"]', '02'),
('0203', '讲师管理', '/admin/business/teacher', '["admin/teacher"]', '02'),
('0204', '会员管理', '/admin/business/member',  '["admin/member","admin/memberCourse"]', '02'),
('0205', '短信管理', '/admin/business/sms',     '["admin/sms"]', '02'),
('03',   '文件管理', '',                        '[]', ''),
('0301', '文件管理', '/admin/file/file',        '["admin/file"]', '03');

-- 角色
INSERT INTO `role` (`id`, `name`, `desc`) VALUES
('R0000001', '超级管理员', '拥有全部权限');

-- 后台用户
INSERT INTO `user` (`id`, `login_name`, `name`, `password`) VALUES
('U0000001', 'admin', '超级管理员', 'a36280cf624505470a88347c10391769');

-- 用户-角色
INSERT INTO `role_user` (`id`, `role_id`, `user_id`) VALUES
('RU000001', 'R0000001', 'U0000001');

-- 角色-资源：超级管理员拥有全部资源
INSERT INTO `role_resource` (`id`, `role_id`, `resource_id`) VALUES
('RR000001', 'R0000001', '01'),
('RR000002', 'R0000001', '0101'),
('RR000003', 'R0000001', '0102'),
('RR000004', 'R0000001', '0103'),
('RR000005', 'R0000001', '02'),
('RR000006', 'R0000001', '0201'),
('RR000007', 'R0000001', '0202'),
('RR000008', 'R0000001', '0203'),
('RR000009', 'R0000001', '0204'),
('RR000010', 'R0000001', '0205'),
('RR000011', 'R0000001', '03'),
('RR000012', 'R0000001', '0301');

-- 分类
INSERT INTO `category` (`id`, `parent`, `name`, `sort`) VALUES
('C0000001', '00000000', '前端开发', 1),
('C0000002', '00000000', '后端开发', 2),
('C0000003', 'C0000001', 'Vue.js', 1),
('C0000004', 'C0000001', 'React', 2),
('C0000005', 'C0000002', 'Java', 1),
('C0000006', 'C0000002', 'Spring Cloud', 2);

-- 讲师
INSERT INTO `teacher` (`id`, `name`, `nickname`, `image`, `position`, `motto`, `intro`) VALUES
('T0000001', '张三', '阿三', NULL, '高级前端工程师', '代码即简历', '8 年一线前端开发经验，专注 Vue 技术栈。'),
('T0000002', '李四', '老李', NULL, '架构师', '把复杂留给自己', '专注微服务架构与中间件，多个亿级流量项目经验。');

-- 课程（演示数据，可删）
INSERT INTO `course` (`id`, `name`, `summary`, `time`, `price`, `image`, `level`, `charge`, `status`, `enroll`, `sort`, `teacher_id`) VALUES
('CO000001', 'Vue.js 从入门到实战', '从零搭建一个 Vue2 项目，掌握组件化开发。', 1500, 0.00, NULL, '1', 'F', 'P', 128, 1, 'T0000001'),
('CO000002', 'Java 微服务实战', 'Spring Cloud Alibaba 全家桶，落地一套课程平台。', 1200, 199.00, NULL, '2', 'C', 'P', 56, 2, 'T0000002');

-- 课程-分类
INSERT INTO `course_category` (`id`, `course_id`, `category_id`) VALUES
('CC000001', 'CO000001', 'C0000003'),
('CC000002', 'CO000002', 'C0000005'),
('CC000003', 'CO000002', 'C0000006');

-- 大章
INSERT INTO `chapter` (`id`, `course_id`, `name`) VALUES
('CH000001', 'CO000001', '第一章 快速上手'),
('CH000002', 'CO000002', '第一章 微服务入门');

-- 小节
INSERT INTO `section` (`id`, `title`, `course_id`, `chapter_id`, `video`, `time`, `charge`, `sort`, `vod`) VALUES
('S0000001', '1-1 环境搭建', 'CO000001', 'CH000001', NULL, 600, 'F', 1, NULL),
('S0000002', '1-2 项目初始化', 'CO000001', 'CH000001', NULL, 900, 'F', 2, NULL),
('S0000003', '1-1 Spring Boot 起步', 'CO000002', 'CH000002', NULL, 1200, 'C', 1, NULL);

-- 课程内容
INSERT INTO `course_content` (`id`, `content`) VALUES
('CO000001', '<h3>课程介绍</h3><p>本课程带你从零开始学习 Vue.js。</p>'),
('CO000002', '<h3>课程介绍</h3><p>从单体到微服务，一步步拆解。</p>');

-- 前台会员
INSERT INTO `member` (`id`, `mobile`, `password`, `name`, `photo`) VALUES
('M0000001', '13800000000', '7cf9fe9c74e3c270b125ac851702f05b', '测试会员', NULL);
