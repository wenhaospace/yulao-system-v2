package com.wendell.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wendell.entity.Note;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ZhangWenhao
 * @date 2026/1/5 16:50
 */
@Mapper
public interface TagMapper extends BaseMapper<Note> {

}
