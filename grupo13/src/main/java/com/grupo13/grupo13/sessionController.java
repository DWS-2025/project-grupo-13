package com.grupo13.grupo13;

import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class sessionController {
    @Autowired
    private Character character;

    @Autowired
    private User user;

    @Autowired
    private UserService userService;

    @Autowired
    private EquipmentService equipmentService;

    private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");
   
    @GetMapping("/")
    public String index(Model model, HttpSession session) {
    
        session.setAttribute("user", user);
        if(character.getName()==null){
            return "index";
        } else{
            return "character_view";
        }
    }
    


    @PostMapping("/formProcess")
    public String procesarFormulario(Model model, HttpSession session, @RequestParam String nameOfCharacter,
            @RequestParam String characterDesc,
            @RequestParam String imageName, @RequestParam MultipartFile characterImage) throws IOException {

        character.setDesc(characterDesc);
        character.setName(nameOfCharacter);

        Files.createDirectories(IMAGES_FOLDER);
        Path imagePath = IMAGES_FOLDER.resolve("image.gif");

        characterImage.transferTo(imagePath);
        session.setAttribute("character", character);
        model.addAttribute("character", session.getAttribute("character"));
        
        return "character_view";
    }

    @GetMapping("/list_objects")
	public String iterationObj(Model model, HttpSession session) {

        List<Equipment> equipmentList = equipmentService.findAll();

        ArrayList<Equipment> available = new ArrayList<>();

        ArrayList<Equipment> currentInventory = userService.currentUserInventory();

        for (Equipment equipment : equipmentList) {
            if (!currentInventory.contains(equipment)) {
                    available.add(equipment);
            }
        }

        model.addAttribute("current", currentInventory);
        model.addAttribute("available", available);

		return "listing";
	}

    @PostMapping("/purchase")
    public String purchase(@RequestParam long id) {

        Optional<Equipment> eqOptional = equipmentService.findById(id);
        if(eqOptional.isPresent()){
            int money = userService.getMoney();
            if(money >= eqOptional.get().getPrice()){
                userService.saveEquipment(id);
            }
        } //cambiarlo a ver cuando tengamos el error de no encontrado o smth like that
        return "redirect:/list_objects";
    }

    @GetMapping("/download_image")
    public ResponseEntity<Object> downloadImage(Model model, HttpSession session) throws MalformedURLException {

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
