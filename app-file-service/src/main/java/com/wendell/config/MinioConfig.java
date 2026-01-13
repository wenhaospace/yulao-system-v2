package com.wendell.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhangWenhao
 * @date 2026/1/13 15:52
 */
@Configuration
public class MinioConfig {

    @Bean
    public MinioClient createMinioClient() {
        try {
            return MinioClient.builder()
                    .endpoint("http://47.245.100.81:9000") // 替换为你的 MinIO 地址
                    .credentials("yulao-minio", "yulao-minio-password") // 替换为实际的 AK/SK
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("无法连接到 MinIO 服务器", e);
        }
    }
}
