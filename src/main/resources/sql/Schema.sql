DROP SCHEMA `DB_Check`;
CREATE DATABASE `DB_Check`;
USE DB_Check;

show tables;

/**
查询数据字典
 */
SELECT a.TABLE_NAME,b.TABLE_COMMENT,a.COLUMN_NAME,a.COLUMN_COMMENT FROM information_schema.COLUMNS
 a join information_schema.tables b ON a.table_schema
 = b.table_schema AND a.table_name = b.table_name
 WHERE a.table_name = 'tb_school';

/**
学校表
 */
CREATE TABLE tb_school(
`id` INT PRIMARY KEY
AUTO_INCREMENT COMMENT '学校的唯一标识',
`name` CHAR(30) NOT NULL COMMENT '学校名称',
`manager.id` CHAR(10) COMMENT '学校管理员工号'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/**
学生表
 */
CREATE TABLE tb_stu(
`id` CHAR(8) PRIMARY KEY COMMENT '学号',
`name` CHAR(10) NOT NULL COMMENT '学生姓名',
`grade.id` INT COMMENT '班级id',
`grade.name` CHAR(30) COMMENT '班级名称',
sex CHAR(1) COMMENT '性别',
phone CHAR(11) COMMENT '电话号码',
photo VARCHAR(200) COMMENT '照片路径',
pwd CHAR(10) NOT NULL DEFAULT '123456' COMMENT '密码,默认为123456',
`college.name` CHAR(30) COMMENT '学院名称',
power TINYINT DEFAULT 0 COMMENT
'用户权限,普通学生为0,考勤人为5,权限默认为0'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/**
老师表
 */
CREATE TABLE tb_tea(
`id` CHAR(8) PRIMARY KEY COMMENT '老师工号',
`name` CHAR(10) NOT NULL COMMENT '老师姓名',
sex CHAR(1) COMMENT '性别',
phone CHAR(11) COMMENT '电话号码',
photo VARCHAR(200) COMMENT '照片路径',
pwd CHAR(10) NOT NULL DEFAULT '123456' COMMENT '密码,默认为123456',
`college.id` INT COMMENT '学院id',
`college.name` CHAR(30) COMMENT '学院名称',
power TINYINT DEFAULT 30 COMMENT
'权限,普通任课老师为30，班主任为35,系部管理员为45,校级管理员为55'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/**
班级表
 */
CREATE TABLE tb_grade(
`id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '班级编号',
`name` CHAR(30) NOT NULL COMMENT '班级名称',
`manager.id` CHAR(8) COMMENT '班主任工号',
`room.id` INT COMMENT '晚自习教室编号',
`college.id` INT COMMENT '所属学院编号'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/**
学院表
 */
CREATE TABLE tb_college(
`id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '学院编号',
`name` CHAR(30) NOT NULL COMMENT '学院名称',
`manager.id` CHAR(8) COMMENT '系部管理员工号',
`school.id` INT COMMENT '所属学校编号'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/**
考勤任务基本信息表
 */
CREATE TABLE tb_taskInfo(
`id` int PRIMARY KEY AUTO_INCREMENT COMMENT '任务基本信息编号',
`name` CHAR(30) NOT NULL COMMENT '任务名称',
`sponsor.id` CHAR(8) COMMENT '发起人或课任老师工号',
`checker.id` CHAR(8) COMMENT '考勤人编号',
start TIME COMMENT '开始考勤时间',
end TIME COMMENT '考勤截止时间',
type TINYINT COMMENT'考勤类型,课程考勤为1,临时考勤为2',
`grade.id` INT COMMENT '考勤班级编号',
`sponsor.type` TINYINT COMMENT '发起人类型',
`checker.type` TINYINT COMMENT '考勤人类型',
`room.id` INT COMMENT '考勤教室编号'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/**
考勤完成情况表
 */
CREATE TABLE tb_completion(
`id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '考勤完成情况编号(相当于一次考勤)',
`date` DATE COMMENT '考勤日期',
state TINYINT DEFAULT 0 COMMENT '考勤状态,0为待考勤,1为已考勤,-1为未考勤,-2为特殊情况',
update_time DATETIME COMMENT '上次更新时间',
`taskinfo.id` int COMMENT '相关联的任务基本信息编号'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/**
考勤记录表
 */
CREATE TABLE tb_record(
`id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '考勤记录编号',
`completion.id` INT NOT NULL COMMENT '相关联的考勤完成情况编号',
`stu.id` CHAR(8) NOT NULL COMMENT '学生学号',
des VARCHAR(100) COMMENT '对于违规记录的描述',
result TINYINT COMMENT '违规情况:0为请假,-1为缺勤,-2为迟到,-3为早退'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/**
考勤记录更新情况表
 */
CREATE TABLE tb_update(
`id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '更新记录编号',
`record.id` INT COMMENT '相关记录编号',
`record.completion.id` INT NOT NULL COMMENT '完成情况编号',
`record.stu.id` CHAR(8) NOT NULL COMMENT '学生学号',
`record.result` TINYINT COMMENT '考勤结果',
`updater.id` CHAR(8) COMMENT '更新人id',
update_time DATETIME COMMENT '更新时间',
des VARCHAR(500) COMMENT '更新原因',
`updater.type` TINYINT COMMENT '更新人类型'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/**
审核情况表
 */
CREATE TABLE tb_audit(
`completion.id` INT COMMENT '相关的完成情况编号',
`checker.id` CHAR(8) COMMENT '审核人id',
state TINYINT COMMENT '审核状态',
update_time DATETIME COMMENT '更新时间',
`checker.type` TINYINT COMMENT '审核人类型',
PRIMARY KEY(`completion.id`,`checker.id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/**
教室表
 */
CREATE TABLE tb_room(
`id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '教室编号',
`name` CHAR(30) COMMENT '教室名称',
state TINYINT COMMENT '教室状态',
`college.id` INT COMMENT '所属学院编号',
des varchar(200) COMMENT '教室描述'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/**
其他情况表
 */
CREATE TABLE tb_otherstate(
`id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '特殊情况编号',
`completion.id` int COMMENT '完成情况id',
des VARCHAR(500) COMMENT '特殊情况的描述'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

