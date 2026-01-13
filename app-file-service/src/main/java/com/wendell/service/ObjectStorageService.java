package com.wendell.service;

import java.util.List;

/**
 * @author ZhangWenhao
 * @date 2026/1/13 15:56
 */
public interface ObjectStorageService {

    void createBucketIfNotExists(String bucketName) throws Exception;

    void uploadFile(String bucketName, String objectName, byte[] data) throws Exception;

    String getPresignedUrl(String bucketName, String objectName, int expiryInSeconds) throws Exception;

    byte[] downloadFile(String bucketName, String objectName);

    List<String> listObjects(String bucketName, String prefix) throws Exception;

}
