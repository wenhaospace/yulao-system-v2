package com.wendell.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wendell.entity.Note;
import com.wendell.entity.go.PageResult;
import com.wendell.repository.NoteMapper;
import com.wendell.repository.TagMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhangWenhao
 * @date 2026/1/5 16:46
 */

@Service
public class NoteService extends ServiceImpl<NoteMapper, Note> {

    @Resource
    private NoteMapper noteMapper;

    @Resource
    private TagMapper tagMapper;

    public void createNewNote(){

        Note newNote = new Note();

        noteMapper.insert(newNote);

    }

    public void deleteNote(){

    }

    public void updateNote(){

    }

    public List<Note> getAllNotes(){

        return noteMapper.selectList(null);
    }

    public PageResult<Note> getNotesByPage(int pageNum, int pageSize) {
        // 创建分页对象
        Page<Note> page = new Page<>(pageNum, pageSize);

        // 执行分页查询
        Page<Note> notePage = this.page(page, new QueryWrapper<>());

        // 封装成 PageResult 返回
        return PageResult.of(
                notePage.getCurrent(),
                notePage.getSize(),
                notePage.getTotal(),
                notePage.getRecords()
        );
    }




}

