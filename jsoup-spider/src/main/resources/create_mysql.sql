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


CREATE TABLE `net_comic_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `source` varchar(32) DEFAULT NULL COMMENT '来源',
  `comic_id` varchar(64) DEFAULT NULL COMMENT 'comicId',
  `title` varchar(128) DEFAULT NULL COMMENT '标题',
  `author` varchar(128) DEFAULT NULL COMMENT '作者',
  `raw_tag` varchar(256) DEFAULT NULL COMMENT '标签',
  `cover` varchar(256) DEFAULT NULL COMMENT '封面图',
  `description` varchar(512) DEFAULT NULL COMMENT '描述',
  `status` int(16) DEFAULT NULL COMMENT '状态 0-初始化 1-已下载 2-已同步',
  PRIMARY KEY (`id`),
  INDEX IDX_COMIC_ID (`comic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='comic主表';

CREATE TABLE `net_comic_section` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `source` varchar(32) DEFAULT NULL COMMENT '来源',
  `comic_id` varchar(64) DEFAULT NULL COMMENT 'comicId',
  `section_id` varchar(64) DEFAULT NULL COMMENT '章节ID',
  `section_name` varchar(64) DEFAULT NULL COMMENT '章节名',
  `sort` varchar(32) DEFAULT NULL COMMENT '顺序',
  `status` int(16) DEFAULT NULL COMMENT '状态 0-初始化 1-已下载 2-已同步',
  PRIMARY KEY (`id`),
  INDEX IDX_COMIC_ID (`comic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='comic章节';


CREATE TABLE `mm_page_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `href` varchar(32) DEFAULT NULL COMMENT 'hrefID',
  `title` varchar(128) DEFAULT NULL COMMENT '标题',
  `publish` varchar(64) DEFAULT NULL COMMENT '发布时间',
  `view_num` varchar(64) DEFAULT NULL COMMENT '浏览数',
  `gmt_create` datetime NOT NULL COMMENT '爬取时间',
  PRIMARY KEY (`id`),
  INDEX IDX_HREF_ID (`href`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='图片集合列表';