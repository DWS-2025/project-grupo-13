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

		List<Armor> armorList = new ArrayList<>();

        
        armorList.add(new Armor("Wood Armor", 20, 
            "https://dummyimage.com/320x240/87f7ff/000000.png&text=Wood+Armor", 
            "Obtained from an oak tree. Light but fragile."));

        armorList.add(new Armor("Iron Plate", 50, 
            "https://dummyimage.com/320x240/a8a8a8/000000.png&text=Iron+Plate", 
            "A strong iron armor that provides good protection."));

        armorList.add(new Armor("Golden Chestplate", 40, 
            "https://dummyimage.com/320x240/ffd700/000000.png&text=Golden+Chestplate", 
            "Made of gold. Shiny but weak."));


		model.addAttribute("armors", armorList);

        List<Weapon> weaponList = new ArrayList<>();

      
        weaponList.add(new Weapon("Wood Sword", 20, 
            "https://i.imgur.com/ZJDJakr.jpeg", 
            "A basic sword made from oak wood."));

        weaponList.add(new Weapon("Iron Longsword", 50, 
            "https://i.imgur.com/xq1BDBK.jpeg", 
            "A strong and reliable iron longsword."));

        weaponList.add(new Weapon("Golden Rapier", 40, 
            "https://i.imgur.com/Bm5NeRx.jpeg", 
            "A golden rapier. Stylish but fragile."));

        weaponList.add(new Weapon("Dragon Fang Blade", 80, 
            "https://dummyimage.com/320x240/dc143c/000000.png&text=Dragon+Fang", 
            "A blade crafted from the fang of an ancient dragon."));

        weaponList.add(new Weapon("Shadow Dagger", 25, 
            "https://dummyimage.com/320x240/1a1a1a/ffffff.png&text=Shadow+Dagger", 
            "A dagger infused with dark energy, perfect for stealth attacks."));

       

            ArrayList<Equipment> armor_available = new ArrayList<>();
            ArrayList<Equipment> weapon_available = new ArrayList<>();

            ArrayList<Equipment> currentInventory = userService.currentUserInventory();

            for (Equipment equipment : armorList) {
                if (!currentInventory.contains(equipment)) {
                    armor_available.add(equipment);
                }
            }

            for (Equipment equipment : weaponList) {
                if (!currentInventory.contains(equipment)) {
                    weapon_available.add(equipment);
                }
            }

            model.addAttribute("current", currentInventory);
            model.addAttribute("armor_avaliable", armor_available);
            model.addAttribute("weapon_available", weapon_available);

		return "listing";
	}

    @PostMapping("/purchase")
    public String purchase(@RequestParam int attribute, @RequestParam String name, @RequestParam String image,
            @RequestParam String desc, @RequestParam String type) {


        if (Objects.equals("armor", type)) {
            Armor armor_new = new Armor(name, 10, image, desc);
            userService.saveEquipment(armor_new);
        } else {
            Weapon weapon_new = new Weapon(name, 10, image, desc);
            userService.saveEquipment(weapon_new);
        }
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
