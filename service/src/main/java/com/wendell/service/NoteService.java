package com.wendell.service;

import com.wendell.entity.Note;
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
public class NoteService {

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


}

