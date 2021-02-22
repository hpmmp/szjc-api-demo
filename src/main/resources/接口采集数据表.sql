--像机列表
DROP TABLE IF EXISTS "dm"."dm_dyn_camera";
CREATE TABLE "dm"."dm_dyn_camera" (
"camera_id" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL,
"area_id" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL,
"area_name" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL,
"device_code" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL,
"device_type" int2 DEFAULT NULL,
"device_name" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL,
"status" int2 DEFAULT NULL,
"remark" varchar(800) COLLATE "pg_catalog"."default" DEFAULT NULL,
"ip" varchar(60) COLLATE "pg_catalog"."default" DEFAULT NULL,
"port" int4 DEFAULT NULL,
"access_user" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL,
"access_pwd" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL,
"addr" varchar(400) COLLATE "pg_catalog"."default" DEFAULT NULL,
"capture_server_id" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL,
"camera_type" int2 DEFAULT NULL,
"camera_rtsp_suffix" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL,
"camera_rtsp" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL,
"camera_server_type" varchar(10) COLLATE "pg_catalog"."default" DEFAULT NULL,
"updated_time" int8 DEFAULT NULL,
"updated_userid" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL,
"created_time" int8 DEFAULT NULL,
"created_userid" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL,
"flag" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL,
"inserttime" timestamp(6) DEFAULT now()
) distribute by hash(inserttime);

COMMENT ON COLUMN "dm"."dm_dyn_camera"."camera_id" IS '摄像头编号';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."area_id" IS '区域ID';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."area_name" IS '区域名称';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."device_code" IS '设备编号';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."device_type" IS '设备类型';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."device_name" IS '设备名称';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."status" IS '状态';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."remark" IS '备注';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."ip" IS 'ip地址';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."port" IS '端口';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."access_user" IS '用户名';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."access_pwd" IS '密码';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."addr" IS '安装地址';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."capture_server_id" IS '抓拍服务器id';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."camera_type" IS '抓拍服务器id';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."camera_rtsp_suffix" IS 'Rtsp后缀';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."camera_rtsp" IS 'Rtsp流';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."camera_server_type" IS '像机服务类型';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."updated_time" IS '更新时间';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."updated_userid" IS '更新人';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."created_time" IS '创建时间';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."created_userid" IS '创建人';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."flag" IS '标志';
COMMENT ON COLUMN "dm"."dm_dyn_camera"."inserttime" IS '数据插入时间';


--人群密度排队长度查询接口
DROP TABLE IF EXISTS "dm"."dm_dyn_camera_query";
CREATE TABLE "dm"."dm_dyn_camera_query" (
"camera_id" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
"camera_name" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
"head_count" int2 DEFAULT NULL,
"query_time" int8 DEFAULT NULL,
"flag" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL,
"inserttime"timestamp(6) DEFAULT now()
) distribute by hash(inserttime);

COMMENT ON COLUMN "dm"."dm_dyn_camera_query"."camera_id" IS '摄像头编号';
COMMENT ON COLUMN "dm"."dm_dyn_camera_query"."camera_name" IS '摄像头名称';
COMMENT ON COLUMN "dm"."dm_dyn_camera_query"."head_count" IS '人数';
COMMENT ON COLUMN "dm"."dm_dyn_camera_query"."query_time" IS '查询时间';
COMMENT ON COLUMN "dm"."dm_dyn_camera_query"."flag" IS '标志';
COMMENT ON COLUMN "dm"."dm_dyn_camera_query"."inserttime" IS '更新时间';


--mysql
drop table if exists `camera_info`;
CREATE TABLE `camera_info` (
  `camera_id` varchar(255) DEFAULT NULL,
  `area_id` varchar(255) DEFAULT NULL,
  `area_name` varchar(255) DEFAULT NULL,
  `device_code` varchar(255) DEFAULT NULL,
  `device_type` int(11) DEFAULT NULL,
  `device_name` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  `access_user` varchar(255) DEFAULT NULL,
  `access_pwd` varchar(255) DEFAULT NULL,
  `addr` varchar(255) DEFAULT NULL,
  `capture_server_id` varchar(255) DEFAULT NULL,
  `camera_type` int(11) DEFAULT NULL,
  `camera_rtsp_suffix` varchar(255) DEFAULT NULL,
  `camera_rtsp` varchar(255) DEFAULT NULL,
  `camera_server_type` varchar(255) DEFAULT NULL,
  `updated_time` BIGINT DEFAULT NULL,
  `updated_userid` varchar(255) DEFAULT NULL,
  `created_time` BIGINT DEFAULT NULL,
  `created_userid` varchar(255) DEFAULT NULL,
  `inserttime` TIMESTAMP  default CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--mysql
drop table if exists `camera_query_info`;
CREATE TABLE `camera_query_info` (
  `camera_id` varchar(255) DEFAULT NULL,
  `camera_name` varchar(255) DEFAULT NULL,
  `head_count` int(11) DEFAULT NULL,
  `query_time` BIGINT DEFAULT NULL,
  `inserttime` TIMESTAMP  default CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
