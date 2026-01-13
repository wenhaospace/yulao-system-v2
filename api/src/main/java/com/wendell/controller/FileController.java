package com.wendell.controller;

import com.wendell.entity.go.ApiResponse;
import com.wendell.service.FileService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhangWenhao
 * @date 2026/1/13 16:14
 */
@RestController
@RequestMapping("/files")
public class FileController {

    @Resource
    private FileService fileService;

    @GetMapping("/test")
    public String test() {
        fileService.test();
        return "file test success";
    }

    @PostMapping("/upload")
    public ApiResponse<String> uploadFile() {
        fileService.uploadFile();
        return ApiResponse.ok("file upload success");
    }

    @GetMapping("/presigned-url")
    public ApiResponse<String> getPresignedUrl() {
        String url = fileService.getPresignedUrl();
        return ApiResponse.ok(url);
    }

    @GetMapping("/download")
    public void downloadFile(HttpServletResponse response ) {
        byte[] fileData = fileService.downloadFile();
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"example.txt\"");
            response.getOutputStream().write(fileData);
            response.getOutputStream().flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/list")
    public ApiResponse<java.util.List<String>> listFiles() {
        java.util.List<String> files = fileService.listFiles();
        return ApiResponse.ok(files);
    }

}
