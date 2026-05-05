package com.hqd.teashopping.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.hqd.teashopping.config.OssProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.UUID;

/**
 * 阿里云 OSS 上传工具类
 */
@Component
public class AliOssUtil {

    @Autowired
    private OssProperties ossProperties;

    /**
     * 上传文件到 OSS
     * @param inputStream 文件输入流
     * @param originalFilename 原始文件名
     * @return 上传后的文件访问 URL
     */
    public String upload(InputStream inputStream, String originalFilename) {
        // 生成唯一文件名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName = "images/" + UUID.randomUUID().toString().replace("-", "") + suffix;

        // 创建 OSS 客户端
        OSS ossClient = new OSSClientBuilder().build(
                ossProperties.getEndpoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret()
        );

        try {
            // 上传文件
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    ossProperties.getBucketName(),
                    objectName,
                    inputStream
            );
            ossClient.putObject(putObjectRequest);

            // 返回文件访问 URL
            return "https://" + ossProperties.getBucketName() + "." + ossProperties.getEndpoint() + "/" + objectName;
        } finally {
            // 关闭客户端
            ossClient.shutdown();
        }
    }
}
