package com.zhixing.weblog.admin.utils;

import com.zhixing.weblog.admin.config.MinioProperties;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
@Slf4j
public class MinioUtil {
    @Autowired
    private MinioProperties minioProperties;

    @Autowired
    private MinioClient minioClient;

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    public String uploadFile(MultipartFile file) throws Exception {
        // 判断文件是否为空
        if (file == null || file.getSize() == 0) {
            log.error("--》上传文件异常：文件大小为空");
        }
        // 文件的原始名称
        String originalFileName = file.getOriginalFilename();
        // 文件的content-type
        String contentType = file.getContentType();
        // 生成存储对象的名称
        String key = UUID.randomUUID().toString().replace("-", "");
        // 获取文件的后缀
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
        // 拼接上文件后缀后的文件名
        String objectName = String.format("%s%s", key, suffix);
        log.info("-->开始上传文件至Minio，objectName：{}", objectName);

        // 上传至Minio
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(objectName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(contentType)
                .build());

        // 返回文件的访问链接
        String url = String.format("%s/%s/%s", minioProperties.getEndpoint(), minioProperties.getBucketName(), objectName);
        log.info("-->上传文件至Minio成功，访问路径：{}", url);
        return url;
    }
}
