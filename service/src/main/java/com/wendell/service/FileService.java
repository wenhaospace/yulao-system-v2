package com.wendell.service;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangWenhao
 * @date 2026/1/13 16:10
 */
@Service
public class FileService {
    @Resource
    @Qualifier("minioService")
    private ObjectStorageService objectStorageService;

    private static final String BUCKET_NAME = "test-bucket";

    public void test() {
        try {
            objectStorageService.createBucketIfNotExists("test-bucket");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadFile() {
        try {
            objectStorageService.createBucketIfNotExists(BUCKET_NAME);
            objectStorageService.uploadFile(BUCKET_NAME, "example.txt", "Hello, MinIO!".getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPresignedUrl() {
        try {
            return objectStorageService.getPresignedUrl(BUCKET_NAME, "example.txt", 3600);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] downloadFile() {
        return objectStorageService.downloadFile(BUCKET_NAME, "example.txt");
    }

    public List<String> listFiles() {
        List<String> result = null;
        try {
            result = objectStorageService.listObjects(BUCKET_NAME, "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
