package com.example.restaurantmanagement.service;

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
    private final String FOLDER_PATH = System.getProperty("user.dir") + "/uploads/";
    private final String SERVER_URL = String.format("http://%s:8080/api/uploads/" , defineIpAddress());

    public static String defineIpAddress(){
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return "localhost";
        }
        return localHost.getHostAddress();
    }


    public Resource getImage(String fileName) {
        Path path = Paths.get(String.format("%s/%s",FOLDER_PATH , fileName));
        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException("ImageProblem");
        }

        return null;
    }

    public String upLoadImageAndGetUrl(MultipartFile file) {
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
        return SERVER_URL + savedFileName;
    }

    private String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return dotIndex != -1 ? filename.substring(dotIndex) : "";
    }


    public void deleteImage(String filePath) {
        if (filePath == null) return;
        filePath = this.getImagePath(filePath);
        Path path = Paths.get(filePath);
        try {
            Files.delete(path);
        } catch (IOException e) {
            System.out.println("IMAGE_CANNOT_DELETED");
        }
    }

    public String getImagePath(String imageUrl) {
        String toRemove = SERVER_URL;
        // Check if the original string contains the part you want to remove
        if (imageUrl.contains(toRemove)) {
            int startIndex = imageUrl.indexOf(toRemove) + toRemove.length();
            String remaining = imageUrl.substring(startIndex);
            return FOLDER_PATH + remaining;
        } else {
            throw new RuntimeException("Image Path not found!!!");
        }
    }

    public void imageValidator(MultipartFile image){
        if(image == null || image.isEmpty())
            throw new NullFieldException(
                    "image cann't be null",
                    "ACTION.ERROR.imageValidator | field is null in request body"
            );

    }


}