package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.configuration.MinioBuckets;
import com.example.restaurantmanagement.exceptions.InvalidException;
import com.example.restaurantmanagement.exceptions.NullFieldException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final MinioService minioService;


    public String upLoadImageAndGetUrl(MultipartFile file , MinioBuckets minioBucket) {
        if (file == null || file.isEmpty()) {
            throw new InvalidException("file", "FILE_IS_NULL",
                    "ACTION.ERROR.uploadImage message : file is null"
            );
        }

        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null) {
            throw new InvalidException("fileName", "INVALID_FILE_NAME",
                    "ACTION.ERROR.uploadImage message : file name is invalid"
            );
        }

        String extension = getExtension(originalFilename); // ex : .png (it contains .)
        String fileName = originalFilename.replace(extension, "");
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String savedFileName = fileName + "_" + timestamp + extension;

        return minioService.putObjectGetPath(file, minioBucket.label(), savedFileName);

    }

    public void deleteImage(String imageUrl){
        minioService.deleteObject(imageUrl);
    }



    private String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return dotIndex != -1 ? filename.substring(dotIndex) : "";
    }




    public void imageValidator(MultipartFile image) {
        if (image == null || image.isEmpty())
            throw new NullFieldException(
                    "image cann't be null",
                    "ACTION.ERROR.imageValidator | field is null in request body"
            );

    }


}