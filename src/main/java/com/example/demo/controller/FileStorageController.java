package com.example.demo.controller;

import com.example.demo.model.Pictures;
import com.example.demo.service.FileStorageService;
import com.example.demo.service.PicturesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
public class FileStorageController {
    private final FileStorageService fileStorageService;
    private final PicturesService picturesService;

    @GetMapping(value = "/", produces = MediaType.IMAGE_JPEG_VALUE)
    public String index(Model model) {
        List<Pictures> pictures = picturesService.getAllPictures();
        log.info(pictures.get(0).getImageUrl());
        model.addAttribute("pictures", picturesService.getAllPictures());
        return "index";
    }

    @PostMapping(value = "/add_picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam(value = "file")MultipartFile file) throws IOException {
        String filename = fileStorageService.saveFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download_file/")
                .path(filename).toUriString();

        Pictures pictures = Pictures.builder()
                .imagePath("/uploads/" + filename)
                .imageUrl(fileDownloadUri)
                .build();
        picturesService.addPicture(pictures);
        return "index";
    }
}
