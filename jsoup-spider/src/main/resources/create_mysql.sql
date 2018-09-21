CREATE TABLE `sp_project_sale` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
`project` varchar(256) DEFAULT NULL COMMENT '项目名',
`company` varchar(256) DEFAULT NULL COMMENT '开发公司',
`sale_date` varchar(256) DEFAULT NULL COMMENT '开售时间',
`loc` varchar(128) DEFAULT NULL COMMENT '楼幢',
`rom_num` varchar(128) DEFAULT NULL COMMENT '房号',
`area` varchar(128)  DEFAULT NULL COMMENT '建筑面积',
`inner_area` varchar(128)  DEFAULT NULL COMMENT '套内建筑面积',
`acquire_rate` varchar(128) DEFAULT NULL COMMENT '得房率',
`price` varchar(128)  DEFAULT NULL COMMENT '申请毛坯单价',
`fit_price` varchar(128)  DEFAULT NULL COMMENT '装修价',
`total_price` varchar(128)  DEFAULT NULL COMMENT '总价',
`sale_status` varchar(128)  DEFAULT NULL COMMENT '销售状态',
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目开售表';



CREATE TABLE `sp_project` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `project` varchar(256) DEFAULT NULL COMMENT '项目名',
  `project_url` varchar(256) DEFAULT NULL COMMENT '项目链接',
  `pre_sale_num` varchar(256) DEFAULT NULL COMMENT '预售号',
  `loc` varchar(128) DEFAULT NULL COMMENT '楼幢',
  `sale_date` varchar(128) DEFAULT NULL COMMENT '开售时间',
  `company` varchar(256) DEFAULT NULL COMMENT '开发公司',
  `address` varchar(256) DEFAULT NULL COMMENT '房屋地址',
  `sale_address` varchar(256) DEFAULT NULL COMMENT '销售地址',
  `total` varchar(128) DEFAULT NULL COMMENT '总套数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 COMMENT='项目表';

CREATE TABLE `hj_video_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `author_id` varchar(128) DEFAULT NULL COMMENT '作者ID',
  `uid` varchar(128) DEFAULT NULL COMMENT '主播ID',
  `uid_name` varchar(256) DEFAULT NULL COMMENT '主播姓名',
  `play_count` int(16) DEFAULT NULL COMMENT '播放次数',
  `mp4` varchar(256) DEFAULT NULL COMMENT '视频地址',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '入库时间戳',
  `md5sum` varchar(256) DEFAULT NULL COMMENT '加密唯一键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='HJ视频表';