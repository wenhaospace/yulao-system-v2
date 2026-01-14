package com.wendell.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wendell.entity.FileDB;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ZhangWenhao
 * @date 2026/1/13 17:58
 */
@Mapper
public interface FileMapper  extends BaseMapper<FileDB> {
}
