package com.wendell.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ZhangWenhao
 * @date 2026/1/14 13:49
 */
@Data
@Accessors(chain = true)
public class FileVo {

    private String id;

    private String fileName;

    private String createTime;
}
