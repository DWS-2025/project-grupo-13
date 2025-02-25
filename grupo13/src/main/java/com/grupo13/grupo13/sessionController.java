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

        armorList.add(new Armor("Dragon Scale Armor", 80, 
            "https://dummyimage.com/320x240/dc143c/000000.png&text=Dragon+Scale", 
            "Forged from the scales of an ancient dragon."));

        armorList.add(new Armor("Shadow Cloak", 25, 
            "https://dummyimage.com/320x240/1a1a1a/ffffff.png&text=Shadow+Cloak", 
            "A mysterious cloak that helps the wearer move unseen."));

        armorList.add(new Armor("Crystal Armor", 65, 
            "https://dummyimage.com/320x240/87ceeb/000000.png&text=Crystal+Armor", 
            "A magical armor made of enchanted crystals."));

        armorList.add(new Armor("Obsidian Plate", 90, 
            "https://dummyimage.com/320x240/2c2c2c/ffffff.png&text=Obsidian+Plate", 
            "Extremely tough armor, made from volcanic obsidian."));

        armorList.add(new Armor("Elven Leather", 35, 
            "https://dummyimage.com/320x240/6b8e23/000000.png&text=Elven+Leather", 
            "Lightweight and flexible, used by elven warriors."));

        armorList.add(new Armor("Demonic Armor", 95, 
            "https://dummyimage.com/320x240/8b0000/ffffff.png&text=Demonic+Armor", 
            "An armor cursed with demonic energy, extremely durable."));

        armorList.add(new Armor("Celestial Robes", 55, 
            "https://dummyimage.com/320x240/ffffff/000000.png&text=Celestial+Robes", 
            "Blessed by the gods, grants divine protection."));
		

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

        weaponList.add(new Weapon("Crystal Spear", 65, 
            "https://dummyimage.com/320x240/87ceeb/000000.png&text=Crystal+Spear", 
            "A magical spear made from enchanted crystals."));

        weaponList.add(new Weapon("Obsidian War Axe", 90, 
            "https://dummyimage.com/320x240/2c2c2c/ffffff.png&text=Obsidian+Axe", 
            "A massive axe forged from volcanic obsidian."));

        weaponList.add(new Weapon("Elven Bow", 35, 
            "https://dummyimage.com/320x240/6b8e23/000000.png&text=Elven+Bow", 
            "A lightweight bow used by elven hunters."));

        weaponList.add(new Weapon("Demonic Scythe", 95, 
            "https://dummyimage.com/320x240/8b0000/ffffff.png&text=Demonic+Scythe", 
            "A cursed scythe imbued with demonic power."));

        weaponList.add(new Weapon("Celestial Staff", 55, 
            "https://dummyimage.com/320x240/ffffff/000000.png&text=Celestial+Staff", 
            "A holy staff blessed by the gods, channeling divine energy."));

            model.addAttribute("weapons", weaponList);
		return "listing";
	}

    @PostMapping("/purchase")
    public String postMethodName(@RequestParam int attribute, @RequestParam String name, @RequestParam String image,
            @RequestParam String desc, @RequestParam String type) {

        if (Objects.equals("armor", type)) {
            Armor armor_new = new Armor(name, 10, image, desc);
            if (!user.getArmorInventory().contains(armor_new))
                user.getArmorInventory().add(armor_new);
        } else {
            Weapon weapon_new = new Weapon(name, 10, image, desc);
            if (!user.getWeaponInventory().contains(weapon_new))
                user.getWeaponInventory().add(weapon_new);
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
