package com.grupo13.grupo13;

import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;


@Controller
public class sessionController {
    @Autowired
    private Character character;

    private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");

   

    @PostMapping("/formProcess")
    public String procesarFormulario(Model model, @RequestParam String nameOfCharacter,
            @RequestParam String characterDesc,
            @RequestParam String imageName, @RequestParam MultipartFile characterImage) throws IOException {

        character.setDesc(characterDesc);
        character.setName(nameOfCharacter);

        Files.createDirectories(IMAGES_FOLDER);
        Path imagePath = IMAGES_FOLDER.resolve("image.gif");

        characterImage.transferTo(imagePath);
        model.addAttribute("character", character);

        return "character_view";
    }

    @GetMapping("/download_image")
    public ResponseEntity<Object> downloadImage(Model model) throws MalformedURLException {

        Path imagePath = IMAGES_FOLDER.resolve("image.gif");

        Resource image = new UrlResource(imagePath.toUri());

        String contentType;
        try {
            contentType = Files.probeContentType(imagePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
        } catch (IOException e) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(image);
    }

}
