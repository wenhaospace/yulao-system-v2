package com.wendell.controller;

import com.wendell.entity.Note;
import com.wendell.entity.go.ApiResponse;
import com.wendell.entity.go.PageResult;
import com.wendell.service.NoteService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/page")
    public ApiResponse<PageResult<Note>> fetchNotesWithPage(@RequestParam(defaultValue = "1") int pageNum ,
                                                            @RequestParam(defaultValue = "10") int pageSize ){
        PageResult<Note> result =  noteService.getNotesByPage(pageNum,pageSize);
        return ApiResponse.ok(result);
    }

}
