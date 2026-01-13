package com.wendell.service.minioImpl;

import com.wendell.service.ObjectStorageService;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.http.Method;
import io.minio.messages.Item;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangWenhao
 * @date 2026/1/13 16:01
 */
@Service("minioService")
public class MinioService implements ObjectStorageService {

    private static final Logger logger = LoggerFactory.getLogger(MinioService.class);

    @Resource
    private MinioClient minioClient;

    @Override
    public void createBucketIfNotExists(String bucketName) throws Exception {

        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            logger.info("✅ 存储桶 '{}' 创建成功！", bucketName);
        } else {
            logger.warn("🟨 存储桶已存在");
        }
    }

    @Override
    public void uploadFile(String bucketName, String objectName, byte[] data) throws Exception{

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName) // 存储路径（对象名）
                        .stream(
                                new java.io.ByteArrayInputStream(data),
                                data.length,
                                -1)
                        .build());

        logger.info("✅ 文件上传成功！");
    }

    @Override
    public String getPresignedUrl(String bucketName, String objectName, int expiryInSeconds) throws Exception {
        String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET) // 可改为 PUT（用于上传）
                        .bucket(bucketName)
                        .object(objectName)
                        .expiry(expiryInSeconds) // 链接有效期（秒）：例如 24 小时 = 86400 秒
                        .build());

        logger.info("🔗 可访问的临时链接：{}" , url);

        return url;
    }


    @Override
    public byte[] downloadFile(String bucketName, String objectName) {

        try (InputStream inputStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build())) {

            // 将输入流转换为 byte[]
            byte[] content = inputStream.readAllBytes(); // JDK 9+
            logger.info("✅ 成功下载文件: {}，大小: {} 字节", objectName, content.length);
            return content;

        } catch (ErrorResponseException e) {
            throw new RuntimeException("❌ 请求错误：对象不存在或权限不足", e);
        } catch (Exception e) {
            throw new RuntimeException("❌ 下载文件时发生异常: " + bucketName + "/" + objectName, e);
        }
    }


    @Override
    public List<String> listObjects(String bucketName, String prefix) throws Exception {
        List<String> objectList = new ArrayList<>();
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .prefix(prefix) // 可选前缀过滤, 例如 "images/"
                        .build());

        for (Result<Item> result : results) {
            Item item = result.get();
            logger.info("📁 " + item.objectName() + " | Size: " + item.size());
            objectList.add(item.objectName());
        }

        return objectList;
    }
}
