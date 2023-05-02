package com.example.pidevcocomarket.controllers;

import com.example.pidevcocomarket.entities.Boutique;
import com.example.pidevcocomarket.repositories.BoutiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private BoutiqueRepository boutiqueRepository;

    @PostMapping("/upload-logo/{id}")
    public ResponseEntity<String> uploadLogo(@PathVariable("id") int id,
                                             @RequestParam("file") MultipartFile file) {
        Optional<Boutique> optionalBoutique = boutiqueRepository.findById(id);
        if (optionalBoutique.isPresent()) {
            Boutique boutique = optionalBoutique.get();
            try {
               boutique.setLogo(file.getBytes().toString());
                boutiqueRepository.save(boutique);
                return new ResponseEntity<>("Logo uploaded successfully", HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Error uploading logo", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Boutique not found", HttpStatus.NOT_FOUND);
        }
    }


}
