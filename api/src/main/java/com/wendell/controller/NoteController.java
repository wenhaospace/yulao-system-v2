package com.wendell.controller;

import com.wendell.entity.Note;
import com.wendell.entity.go.ApiResponse;
import com.wendell.service.NoteService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ZhangWenhao
 * @date 2026/1/5 16:44
 */
@RestController
@RequestMapping("/notes")
@CrossOrigin("*")
public class NoteController {

    @Resource
    private NoteService noteService;

    @GetMapping()
    public ApiResponse<List<Note>> fetchAllNotes(){
        List<Note> result =  noteService.getAllNotes();
        return ApiResponse.ok(result);
    }

}
