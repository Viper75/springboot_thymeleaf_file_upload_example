package com.example.demo.service;

import com.example.demo.model.Pictures;
import com.example.demo.repository.PicturesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PicturesService {
    private final PicturesRepository picturesRepository;

    public void addPicture(Pictures pictures) {
        picturesRepository.save(pictures);
    }

    public List<Pictures> getAllPictures() {
        return picturesRepository.findAll();
    }
}
