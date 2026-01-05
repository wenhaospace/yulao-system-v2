package com.wendell.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ZhangWenhao
 * @date 2026/1/5 16:52
 */
@Data
@TableName("yulao.notes")
public class Note {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id; // BIGINT UNSIGNED -> Long

    @TableField("platform")
    private String platform = "";

    @TableField("description")
    private String description;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField("url")
    private String url;

    @TableField("sort")
    private Integer sort = 0;

}