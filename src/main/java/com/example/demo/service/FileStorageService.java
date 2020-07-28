package com.example.demo.service;

import com.example.demo.config.AppConstants;
import com.example.demo.exceptions.FileFormartExceprtion;
import com.example.demo.properties.FileStorageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectory(fileStorageLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String saveFile(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        log.info(filename);
        if (!filename.endsWith(AppConstants.PNG_FILE_FORMAT)
                && !filename.endsWith(AppConstants.JPEG_FILE_FORMAT)
                && !filename.endsWith(AppConstants.JPG_FILE_FORMAT)
                && !filename.endsWith(AppConstants.GIF_FILE_FORMAT))
            throw new FileFormartExceprtion("Invalid file format.");

        File temp = new File(AppConstants.TEMP_DIR + filename);
        temp.createNewFile();

        FileOutputStream fout = new FileOutputStream(temp);
        fout.write(file.getBytes());
        fout.close();

        if (temp.exists())
            temp.delete();

        filename = StringUtils.cleanPath(filename);
        String newFilename = System.currentTimeMillis() + "_" + filename;
        Path targetLocation = fileStorageLocation.resolve(newFilename);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return newFilename;
    }

    public Resource loadFileAsResource(String filename) {
        try {
            Path filePath = fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists())
                return resource;
            else
                throw new FileFormartExceprtion("File not found");
        } catch (MalformedURLException e) {
            throw new FileFormartExceprtion(e.getMessage());
        }
    }
}
