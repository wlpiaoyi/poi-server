CREATE TABLE `poi_cell_data_01` (
  `id` bigint(20) NOT NULL,
  `c` int(11) NOT NULL DEFAULT '0' COMMENT '列',
  `r` int(11) NOT NULL DEFAULT '0' COMMENT '行',
  `v` blob NOT NULL COMMENT '单元格内容',
  `sheet_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `pcd_01_sheet_id_index` (`sheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `poi_cell_data_02` (
  `id` bigint(20) NOT NULL,
  `c` int(11) NOT NULL DEFAULT '0' COMMENT '列',
  `r` int(11) NOT NULL DEFAULT '0' COMMENT '行',
  `v` blob NOT NULL COMMENT '单元格内容',
  `sheet_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `pcd_02_sheet_id_index` (`sheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `poi_cell_data_03` (
  `id` bigint(20) NOT NULL,
  `c` int(11) NOT NULL DEFAULT '0' COMMENT '列',
  `r` int(11) NOT NULL DEFAULT '0' COMMENT '行',
  `v` blob NOT NULL COMMENT '单元格内容',
  `sheet_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `pcd_03_sheet_id_index` (`sheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `poi_cell_data_04` (
  `id` bigint(20) NOT NULL,
  `c` int(11) NOT NULL DEFAULT '0' COMMENT '列',
  `r` int(11) NOT NULL DEFAULT '0' COMMENT '行',
  `v` blob NOT NULL COMMENT '单元格内容',
  `sheet_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `pcd_04_sheet_id_index` (`sheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `poi_cell_data_05` (
  `id` bigint(20) NOT NULL,
  `c` int(11) NOT NULL DEFAULT '0' COMMENT '列',
  `r` int(11) NOT NULL DEFAULT '0' COMMENT '行',
  `v` blob NOT NULL COMMENT '单元格内容',
  `sheet_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `pcd_05_sheet_id_index` (`sheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `poi_cell_data_06` (
  `id` bigint(20) NOT NULL,
  `c` int(11) NOT NULL DEFAULT '0' COMMENT '列',
  `r` int(11) NOT NULL DEFAULT '0' COMMENT '行',
  `v` blob NOT NULL COMMENT '单元格内容',
  `sheet_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `pcd_06_sheet_id_index` (`sheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `poi_cell_data_07` (
  `id` bigint(20) NOT NULL,
  `c` int(11) NOT NULL DEFAULT '0' COMMENT '列',
  `r` int(11) NOT NULL DEFAULT '0' COMMENT '行',
  `v` blob NOT NULL COMMENT '单元格内容',
  `sheet_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `pcd_07_sheet_id_index` (`sheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `poi_cell_data_08` (
  `id` bigint(20) NOT NULL,
  `c` int(11) NOT NULL DEFAULT '0' COMMENT '列',
  `r` int(11) NOT NULL DEFAULT '0' COMMENT '行',
  `v` blob NOT NULL COMMENT '单元格内容',
  `sheet_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `pcd_08_sheet_id_index` (`sheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `poi_cell_data_09` (
  `id` bigint(20) NOT NULL,
  `c` int(11) NOT NULL DEFAULT '0' COMMENT '列',
  `r` int(11) NOT NULL DEFAULT '0' COMMENT '行',
  `v` blob NOT NULL COMMENT '单元格内容',
  `sheet_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `pcd_09_sheet_id_index` (`sheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `poi_cell_data_10` (
  `id` bigint(20) NOT NULL,
  `c` int(11) NOT NULL DEFAULT '0' COMMENT '列',
  `r` int(11) NOT NULL DEFAULT '0' COMMENT '行',
  `v` blob NOT NULL COMMENT '单元格内容',
  `sheet_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `pcd_10_sheet_id_index` (`sheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `poi_cell_data_11` (
  `id` bigint(20) NOT NULL,
  `c` int(11) NOT NULL DEFAULT '0' COMMENT '列',
  `r` int(11) NOT NULL DEFAULT '0' COMMENT '行',
  `v` blob NOT NULL COMMENT '单元格内容',
  `sheet_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `pcd_11_sheet_id_index` (`sheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `poi_cell_data_12` (
  `id` bigint(20) NOT NULL,
  `c` int(11) NOT NULL DEFAULT '0' COMMENT '列',
  `r` int(11) NOT NULL DEFAULT '0' COMMENT '行',
  `v` blob NOT NULL COMMENT '单元格内容',
  `sheet_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `pcd_12_sheet_id_index` (`sheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `poi_cell_merge` (
  `id` bigint(20) NOT NULL,
  `sheet_id` bigint(20) DEFAULT NULL,
  `first_row` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `last_row` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `first_col` int(11) NOT NULL DEFAULT '0',
  `last_col` int(11) NOT NULL DEFAULT '0',
  `status` int(2) DEFAULT '1' COMMENT '状态',
  `is_deleted` int(2) DEFAULT NULL COMMENT '是否删除',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_dept` bigint(20) DEFAULT NULL COMMENT '创建部门',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `pcm_sheet_id_index` (`sheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `poi_excel_data` (
  `id` bigint(20) NOT NULL,
  `template_id` bigint(20) DEFAULT NULL,
  `etype` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'Excel类型',
  `name` varchar(45) COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
  `condit_str01` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '条件1',
  `condit_str02` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `condit_str03` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `condit_long01` bigint(20) DEFAULT NULL COMMENT '条件1',
  `condit_long02` bigint(20) DEFAULT NULL COMMENT '条件1',
  `condit_long03` bigint(20) DEFAULT NULL COMMENT '条件1',
  `status` int(2) DEFAULT '1' COMMENT '状态',
  `is_deleted` int(2) DEFAULT NULL COMMENT '是否删除',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_dept` bigint(20) DEFAULT NULL COMMENT '创建部门',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='Excel基础内容';

CREATE TABLE `poi_form` (
  `id` bigint(20) NOT NULL,
  `table_code` varchar(32) COLLATE utf8mb4_bin NOT NULL,
  `ftype` tinyint(4) NOT NULL DEFAULT '1' COMMENT '表类型  1:Excel 2:Share',
  `form_rela_id` bigint(20) DEFAULT NULL COMMENT '关联Excel表Id',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `name` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '表单名称',
  `direction` tinyint(4) NOT NULL COMMENT '方向 0:一维横向 1:一维纵向 2:二维',
  `excel_id` bigint(20) NOT NULL,
  `sheet_id` bigint(20) NOT NULL,
  `status` int(2) DEFAULT '1' COMMENT '状态',
  `is_deleted` int(2) DEFAULT '0' COMMENT '是否删除',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_dept` bigint(20) DEFAULT NULL COMMENT '创建部门',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='表单';

CREATE TABLE `poi_form_head` (
  `id` bigint(20) NOT NULL,
  `head_type` tinyint(4) NOT NULL COMMENT '表头类型 1:普通 2:合计',
  `form_id` bigint(20) NOT NULL,
  `data_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '数据类型 0:String 1:数字 2:日期 3:百分比',
  `direction` tinyint(4) NOT NULL COMMENT '方向 1:一维横向 2:一维纵向 3:二维',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `parent_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '上级ID',
  `property` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '字段',
  `column_code` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '列',
  `data_id` bigint(20) NOT NULL,
  `sort` int(11) NOT NULL,
  `deep` int(11) NOT NULL DEFAULT '0' COMMENT '深度',
  `is_leaf` tinyint(4) NOT NULL DEFAULT '1' COMMENT '叶子节点 0:否 1:是',
  `is_deleted` int(2) DEFAULT '0',
  `status` int(2) DEFAULT '1' COMMENT '状态',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_dept` bigint(20) DEFAULT NULL COMMENT '创建部门',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='表单头';

CREATE TABLE `poi_form_head_hr` (
  `id` bigint(20) NOT NULL,
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型  1:隐藏 2:只读',
  `form_id` bigint(20) NOT NULL,
  `form_head_code` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '表数据项code',
  `is_deleted` int(2) DEFAULT '0',
  `status` int(2) DEFAULT '1' COMMENT '状态',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_dept` bigint(20) DEFAULT NULL COMMENT '创建部门',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='单元隐藏只读';

CREATE TABLE `poi_form_head_rela` (
  `head_id` bigint(20) NOT NULL,
  `head_rela_id` bigint(20) NOT NULL,
  `is_index` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否是索引 0:否 1:是',
  PRIMARY KEY (`head_id`,`head_rela_id`),
  UNIQUE KEY `head_id_UNIQUE` (`head_id`),
  UNIQUE KEY `head_rela_id_UNIQUE` (`head_rela_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='表头关联';

CREATE TABLE `poi_sheet_data` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `frozen` blob COMMENT '冻结行列',
  `thumb_image` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '缩略图',
  `excel_id` bigint(20) NOT NULL,
  `month` tinyint(4) NOT NULL DEFAULT '0' COMMENT '几月份新建的表，这里针推单元格数据做了分表',
  `rc_value` blob COMMENT '行宽和列高',
  `data_verification` blob,
  `status` int(2) DEFAULT '1' COMMENT '状态',
  `is_deleted` int(2) DEFAULT NULL COMMENT '是否删除',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_dept` bigint(20) DEFAULT NULL COMMENT '创建部门',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='Sheet基础内容';

CREATE TABLE `poi_template_menus` (
  `id` bigint(20) NOT NULL,
  `excel_id` bigint(20) NOT NULL,
  `report_num` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '报表编号',
  `create_post` bigint(20) NOT NULL COMMENT '制表单位',
  `thumb_image` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '预览图',
  `name` varchar(60) COLLATE utf8mb4_bin NOT NULL COMMENT '表格名称',
  `classify` tinyint(4) NOT NULL DEFAULT '0' COMMENT '表格分类',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '表格类型',
  `audit_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态',
  `audit_reason` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '审核意见',
  `is_share` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否支持分享',
  `issue_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '发布状态',
  `reason_file` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '发文依据',
  `status` int(2) DEFAULT '1' COMMENT '状态',
  `is_deleted` int(2) DEFAULT '0' COMMENT '是否删除',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_dept` bigint(20) DEFAULT NULL COMMENT '创建部门',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='模板目录';
