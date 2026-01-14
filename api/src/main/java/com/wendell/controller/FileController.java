package com.wendell.controller;

import cn.hutool.core.util.IdUtil;
import com.wendell.entity.FileDB;
import com.wendell.entity.go.ApiResponse;
import com.wendell.service.FileService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

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
    public ApiResponse<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ApiResponse.error(400,"文件不能为空");
        }

        fileService.uploadFile(file);
        return ApiResponse.ok("file upload success");
    }

    @GetMapping("/presigned-url/{fileId}")
    public ApiResponse<String> getPresignedUrl(@PathVariable String fileId) {
        String url = fileService.getPresignedUrl(fileId);
        return ApiResponse.ok(url);
    }

    @GetMapping("/download/{fileId}")
    public void downloadFile(@PathVariable String fileId, HttpServletResponse response) {
        FileDB fileDB = fileService.fetchFileMetaData(fileId);
        byte[] fileData = fileService.downloadFile(fileDB);

        // 需要对文件名进行 URL 编码，防止中文或特殊字符导致的问题
        String encoded = UriUtils.encode(fileDB.getFileName(), StandardCharsets.UTF_8);
        String outputFileName = encoded+"_"+ IdUtil.simpleUUID();
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\""+outputFileName+"\"");
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

    // 大文件下载示例 - TODO: 需结合实际业务场景完善
    @GetMapping("/bigDownload")
    public void downloadFileByStreamWay(HttpServletResponse response) {
        String filePath = "/path/to/your/large-file.dat"; // 应由 service 提供 InputStream
        File file = new File(filePath);

        if (!file.exists() || !file.isFile()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        response.setHeader("Content-Length", String.valueOf(file.length())); // 提前告诉客户端文件大小

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
             OutputStream outputStream = response.getOutputStream()) {

            byte[] buffer = new byte[8192]; // 8KB 缓冲区，不占用太多内存
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();

        } catch (IOException e) {
            throw new RuntimeException("File transfer failed", e);
        }
    }

}
