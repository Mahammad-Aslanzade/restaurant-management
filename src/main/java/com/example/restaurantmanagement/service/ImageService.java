package com.example.restaurantmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final String FOLDER_PATH = System.getProperty("user.dir") + "/uploads/";

    public Resource getImage(String fileName){
        Path path = Paths.get(String.format("C:/Users/Mahammad/Desktop/Java-Project/restaurant-management/uploads/%s", fileName));
        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException("ImageProblem");
        }

        return null;
    }

    public String upLoadImageAndGetPath(MultipartFile file){
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("FILE_IS_NULL");
        }

        String fileName = file.getName();
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new RuntimeException("INVALID_FILE_NAME");
        }

        String extension = getExtension(originalFilename);
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String savedFileName = fileName + "_" + timestamp + "_" + extension;
        String filePath = FOLDER_PATH + savedFileName;

        // Save the file
        File destinationFile = new File(filePath);
        try {
            file.transferTo(destinationFile);
        } catch (IOException e) {
            throw new RuntimeException("IMAGE_CANNOT_BE_UPLOADED");
        }

        // ImageController will apply getImage method
        return "http://localhost:8080/uploads/" + savedFileName;
    }

    private String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return dotIndex != -1 ? filename.substring(dotIndex) : "";
    }


    public void deleteImage(String filePath) {
        if (filePath == null) return;
        Path path = Paths.get(filePath);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException( "IMAGE_CANNOT_DELETED");
        }
    }


}