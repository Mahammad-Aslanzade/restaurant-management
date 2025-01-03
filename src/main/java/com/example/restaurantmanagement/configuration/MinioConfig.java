package com.example.restaurantmanagement.configuration;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.config.url}")
    private String endpoint;

    @Value("${minio.config.user}")
    private String accessKey;

    @Value("${minio.config.password}")
    private String secretKey;

    @Bean
    public MinioClient minioClient(){
        return io.minio.MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey , secretKey)
                .build();
    }


}
