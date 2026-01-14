package com.wendell.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.wendell.entity.FileDB;
import com.wendell.entity.vo.FileVo;
import com.wendell.repository.FileMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

    @Resource
    private FileMapper fileMapper;

    private static final String BUCKET_NAME = "test-bucket";

    //==================== Minio  操作区域 ====================
    public void test() {
        try {
            objectStorageService.createBucketIfNotExists("test-bucket");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void uploadFile(MultipartFile file) {
        try {
            objectStorageService.createBucketIfNotExists(BUCKET_NAME);

            // 用于保证可以上传重复的文件
            String file_id = IdUtil.fastSimpleUUID();
            // 原始文件名
            String originalFileName = file.getOriginalFilename();
            // 文件类型
            String contentType = file.getContentType();
            // 重新组织文件名，用于Minio存储
            String objectName = DateUtil.date().toString("yyyy-MM-dd") + "_" + file_id + "_" + originalFileName;

            // 存储文件到Minio
            objectStorageService.uploadFile(BUCKET_NAME, objectName, file.getBytes(), contentType);

            // 更新本地数据库记录（示例代码，具体实现根据实际需求调整）
            FileDB fileDB = new FileDB().setFileName(originalFileName)
                    .setBucketName(BUCKET_NAME)
                    .setObjectName(objectName)
                    .setCreateTime(LocalDateTime.now());
            fileMapper.insert(fileDB);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPresignedUrl( String fileId) {
        try {

            FileDB fileDB = fileMapper.selectById(Long.valueOf(fileId));
            if (fileDB == null) {
                throw new RuntimeException("File not found with ID: " + fileId);
            }

            return objectStorageService.getPresignedUrl(fileDB.getBucketName(), fileDB.getObjectName(), 3600);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public FileDB fetchFileMetaData(String fileId) {
        FileDB fileDB = fileMapper.selectById(Long.valueOf(fileId));
        if (fileDB == null) {
            throw new RuntimeException("File not found with ID: " + fileId);
        }
        return fileDB;
    }

    public byte[] downloadFile(FileDB fileDB) {
        return objectStorageService.downloadFile(fileDB.getBucketName(), fileDB.getObjectName());
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

    //==================== Minio 操作区域 ====================

    // ==================== 本地数据库操作区域 ====================
    public List<FileVo> getAllFiles() {
        List<FileDB> fileDBS = fileMapper.selectList(null);

        List<FileVo> fileVos = new ArrayList<>();

        fileDBS.forEach(fileDB -> {
                    FileVo fileVo = new FileVo();
                    fileVo.setId(String.valueOf(fileDB.getId()));
                    fileVo.setFileName(fileDB.getFileName());
                    fileVo.setCreateTime(fileDB.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    fileVos.add(fileVo);
                });
        return fileVos;
    }



}
