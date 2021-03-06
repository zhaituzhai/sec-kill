-- 商品秒杀表sql

-- stock
CREATE TABLE `stock` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `count` int(11) NOT NULL COMMENT '库存',
  `sale` int(11) NOT NULL COMMENT '已售',
  `version` int(11) NOT NULL COMMENT '乐观锁，版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `sec_kill`.`stock` (`id`, `name`, `count`, `sale`, `version`) VALUES ('1', '手机', '10', '0', '0');


-- 商品订单
CREATE TABLE `stock_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL COMMENT '库存ID',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '商品名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=369 DEFAULT CHARSET=utf8;


CREATE TABLE `commodity` (
  `commodity_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `commodity_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `commodity_desc` varchar(255) DEFAULT NULL COMMENT '商品描述',
  `commodity_stock` bigint(20) DEFAULT NULL,
  `commodity_sale` bigint(20) DEFAULT '0',
  PRIMARY KEY (`commodity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

CREATE TABLE `commodity_order` (
  `order_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品订单id',
  `commodity_id` int(11) DEFAULT NULL COMMENT '对应商品id',
  `commodity_count` int(11) DEFAULT NULL COMMENT '商品数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


