CREATE TABLE IF NOT EXISTS yulao.file (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    bucketName VARCHAR(100) NOT NULL DEFAULT '' COMMENT '存储桶的名称',
    objectName VARCHAR(100) NOT NULL DEFAULT '' COMMENT '对象存储路径',
   	fileName VARCHAR(100) NOT NULL DEFAULT '' COMMENT '文件名',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='对象存储信息表';

ALTER TABLE yulao.file
ADD COLUMN logicDeleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志：0-未删除，1-已删除';

select * from yulao.file;
SHOW INDEX FROM yulao.file;
drop table yulao.file;