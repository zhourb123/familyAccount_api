package com.zhourb.familyaccount_api.config.minio;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author RenQun
 * @version 1.0
 * @date 2022/4/5 15:23
 * @description
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {
    private  String endpoint;
    private  String accessKey;
    private  String secretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
