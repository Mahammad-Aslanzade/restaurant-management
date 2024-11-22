package com.example.restaurantmanagement.service;

import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;

    @Value("${minio.config.url}")
    private String minioServerUrl;

    public String putObjectGetPath(MultipartFile multipartFile, String bucketName, String fileName) {

        if (!checkBucketExist(bucketName)) {
            throw new RuntimeException("BUCKET_NOT_EXIST");
        }


        try {

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(multipartFile.getInputStream(), -1, 10485760) // 10 MB max part size
                            .contentType(multipartFile.getContentType())
                            .build()
            );


            return getFileAccessLink(bucketName, fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteObject(String fileUrl) {
        String bucketName = parseFileDetails(fileUrl).bucketName;
        String objectName = parseFileDetails(fileUrl).fileName;
        try{
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }


    public boolean checkBucketExist(String bucketName) {
        try {
            return minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public String getFileAccessLink(String bucketName, String fileName) {
        return String.format("%s/%s/%s", minioServerUrl, bucketName, fileName);
    }

    private ObjectDetail parseFileDetails(String imageUrl) {
        String relativePath = imageUrl.replace(minioServerUrl, "");

        // Ensure no leading slash
        if (relativePath.startsWith("/")) {
            relativePath = relativePath.substring(1);
        }

        // Extract bucket name and file name
        String[] parts = relativePath.split("/", 2);
        String bucketName = parts[0];
        String fileName = parts[1];

        return new ObjectDetail(bucketName , fileName);
    }

    @AllArgsConstructor
    class ObjectDetail {
        public String bucketName;
        public String fileName;
    }


}