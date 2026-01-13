CREATE TABLE IF NOT EXISTS yulao.notes (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    platform VARCHAR(100) NOT NULL DEFAULT '' COMMENT '平台/例如微信公众号',
    description TEXT COMMENT '描述/主要是什么内容',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    url VARCHAR(512) DEFAULT NULL COMMENT '文章地址',
    sort INT NOT NULL DEFAULT 0 COMMENT '排序',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='笔记表';
select * from yulao.notes;
SHOW INDEX FROM yulao.notes;
drop table yulao.notes;

CREATE TABLE IF NOT EXISTS yulao.tags (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    name VARCHAR(100) NOT NULL COMMENT '标签名',
    PRIMARY KEY (id),
    UNIQUE KEY uk_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='标签表';
select * from yulao.tags;
SHOW INDEX FROM yulao.tags;
drop table yulao.tags;


CREATE TABLE IF NOT EXISTS yulao.note_tag (
    note_id BIGINT UNSIGNED NOT NULL COMMENT '笔记的ID',
    tag_id  BIGINT UNSIGNED NOT NULL COMMENT '标签的ID',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '关联创建时间',
    PRIMARY KEY (note_id, tag_id),
    KEY idx_tag_id (tag_id),
    CONSTRAINT fk_note_tag_note FOREIGN KEY (note_id) REFERENCES notes(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_note_tag_tag  FOREIGN KEY (tag_id)  REFERENCES tags(id)  ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='笔记-标签关联表';
select * from yulao.note_tag;
SHOW INDEX FROM yulao.note_tag;

-- ===========================================================
insert into yulao.notes (platform,description,url)
VALUES ('微信公众号', 'FRP - 内网穿透实践','https://mp.weixin.qq.com/s/HDlkE1ZZRopxXI6y1LJzNw');