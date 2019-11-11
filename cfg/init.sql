CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(32) NOT NULL,
  `wechatName` varchar(64) NOT NULL,
  `imgUrl` varchar(256) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `INDEX_OPENID` (`openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_store_ext` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `store_id` int(11) NOT NULL,
  `start_am` tinyint(2) NOT NULL,
  `end_am` tinyint(2) NOT NULL,
  `start_pm` tinyint(2) NOT NULL,
  `end_pm` tinyint(2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FOREIGN_STORE` (`store_id`),
  CONSTRAINT `FOREIGN_STORE` FOREIGN KEY (`store_id`) REFERENCES `t_store` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_store_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `store_id` int(11) NOT NULL,
  `type` tinyint(1) NOT NULL,
  `text` varchar(32) NOT NULL,
  `satisfy_value` int(4) NOT NULL,
  `return_value` int(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `INDEX_ACTIVITY` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_store` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `is_open` tinyint(1) NOT NULL DEFAULT '1',
  `public_msg` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `img_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `INDEX_IS_OPEN` (`is_open`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_order_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `dish_count` int(3) NOT NULL,
  `dish_name` varchar(32) NOT NULL,
  `dish_price` int(4) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FOREIGN_ORDER_ID` (`order_id`),
  CONSTRAINT `FOREIGN_ORDER_ID` FOREIGN KEY (`order_id`) REFERENCES `t_order` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `store_id` int(11) NOT NULL,
  `origin_price` int(6) NOT NULL,
  `final_price` int(6) NOT NULL,
  `create_time` timestamp(6) NOT NULL,
  `status` tinyint(1) DEFAULT '0',
  `remark` varchar(64) DEFAULT NULL,
  `del_user_name` varchar(32) NOT NULL,
  `del_phone` varchar(13) NOT NULL,
  `del_company` varchar(64) NOT NULL,
  `is_redeem` tinyint(1) NOT NULL DEFAULT '0',
  `coupon_id` int(11) DEFAULT NULL,
  `is_discount` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `INDEX_ORDER_USERID` (`user_id`,`store_id`,`is_redeem`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_dish_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `store` int(11) DEFAULT NULL,
  `enable` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_dish` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `price` int(4) NOT NULL,
  `category` int(11) NOT NULL,
  `img` varchar(256) DEFAULT NULL,
  `store` int(11) NOT NULL,
  `enable` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FOREIGN_CATEGORY` (`category`),
  CONSTRAINT `FOREIGN_CATEGORY` FOREIGN KEY (`category`) REFERENCES `t_dish_category` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_deliver_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_deliver_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `user_name` varchar(32) NOT NULL,
  `phone` varchar(13) NOT NULL,
  `company_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `INDEX_ADDR` (`user_id`,`company_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_value` int(5) NOT NULL,
  `user_id` int(11) NOT NULL,
  `store_id` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `INDEX_COUPON` (`user_id`,`store_id`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
