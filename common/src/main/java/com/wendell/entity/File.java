package com.wendell.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ZhangWenhao
 * @date 2026/1/13 17:57
 */
@Data
@TableName("yulao.file")
public class File {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id; // BIGINT UNSIGNED -> Long

    @TableField("bucketName")
    private String bucketName = "";

    @TableField("objectName")
    private String objectName;

    @TableField("fileName")
    private String fileName;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
