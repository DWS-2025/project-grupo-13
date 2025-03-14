package com.grupo13.grupo13.controller;
import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.service.CharacterService;
import com.grupo13.grupo13.service.EquipmentService;
import com.grupo13.grupo13.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class sessionController {

    //attributes
    @Autowired
    private UserService userService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private CharacterService characterService;
    private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");

    @GetMapping("/")
    public String index(Model model, HttpSession session) {

        ArrayList<Weapon> currentInventoryWeapon = userService.currentUserInventoryWeapon();
        ArrayList<Armor> currentInventoryArmor = userService.currentUserInventoryArmor();
        Character character = userService.getCharacter();

        //gets the character and its inventory for mustache
        model.addAttribute("character", character);
        model.addAttribute("currentW", currentInventoryWeapon);
        model.addAttribute("currentA", currentInventoryWeapon);
        model.addAttribute("user", userService.getLoggedUser());


        //checks if the user has "logged", in the first fase is creating the character
        if (character == null) {
            return "index";
        } else {
            return "character_view";
        }
    }

    @PostMapping("/formProcess")
    public String procesarFormulario(Model model, HttpSession session, @RequestParam String nameOfCharacter,
            @RequestParam String characterDesc,
            @RequestParam String imageName, @RequestParam MultipartFile characterImage) throws IOException {
        
                if (nameOfCharacter.isBlank() || characterDesc.isBlank() || imageName.isBlank()) {
                    model.addAttribute("message", "Some or all parameters were left blank");
                    return "sp_errors";
                }
        //creates the character
        Character character = new Character();
        character.setDesc(characterDesc);
        character.setName(nameOfCharacter);
        imageName += ".jpg";
        character.setImageName(imageName);

        //saves the character in the repository
        characterService.save(character);
        userService.saveCharacter(character);

        //saves image in the correspondent folder
        Files.createDirectories(IMAGES_FOLDER);
        Path imagePath = IMAGES_FOLDER.resolve(imageName);
        characterImage.transferTo(imagePath);

        model.addAttribute("character", character);
        ArrayList<Weapon> currentWeapon = userService.currentUserInventoryWeapon();
        ArrayList<Armor> currentArmor = userService.currentUserInventoryArmor();
        model.addAttribute("currentW", currentWeapon);
        model.addAttribute("currentA", currentArmor);
        model.addAttribute("user", userService.getLoggedUser());

        return "character_view";
    }

    @GetMapping("/list_objects")
    public String iterationObj(Model model, HttpSession session) {

        List<Weapon> equipmentListW = equipmentService.findAllWeapon();
        List<Armor> equipmentListA = equipmentService.findAllArmor();
        ArrayList<Weapon> availableW = new ArrayList<>();
        ArrayList<Armor> availableA = new ArrayList<>();
        ArrayList<Weapon> currentInventoryW = userService.currentUserInventoryWeapon();
        ArrayList<Armor> currentInventoryA = userService.currentUserInventoryArmor();

        //gets the listing of the current equipment in the repository and the inventory
        //creates a list of the equipment that the user doesnt have
        //the html will present the inventory as purchased and the not purchased (available) equipments
        for (Weapon equipmentW : equipmentListW) {
            if (!currentInventoryW.contains(equipmentW)) {
                availableW.add(equipmentW);
            }
        }
        for (Armor equipmentA : equipmentListA) {
            if (!currentInventoryA.contains(equipmentA)) {
                availableA.add(equipmentA);
            }
        }
        model.addAttribute("user", userService.getLoggedUser());
        model.addAttribute("currentA", currentInventoryA);
        model.addAttribute("currentW", currentInventoryW);
        model.addAttribute("availableA", availableA);
        model.addAttribute("availableW", availableW);
        return "listing";
    }

    @PostMapping("/purchaseWeapon")
    public String purchaseWeapon(@RequestParam long id, Model model) {

        Optional<Weapon> eqOptional = equipmentService.findWeaponById(id);
        if (eqOptional.isPresent()) {
            //checks if the user has enough money or if it's homeless
            int money = userService.getMoney();
            if (money >= eqOptional.get().getPrice()) {
                userService.saveWeapon(id);
                return "redirect:/list_objects";
            } else{
                model.addAttribute("message", "You don't have any money left, go work or something");
                return "sp_errors";
            }
        }
        model.addAttribute("message", "Could not purchase, doesnt exist");
        return "sp_errors";
    }
    @PostMapping("/purchaseArmor")
    public String purchaseArmor(@RequestParam long id, Model model) {

        Optional<Armor> eqOptional = equipmentService.findArmorById(id);
        if (eqOptional.isPresent()) {
            //checks if the user has enough money or if it's homeless
            int money = userService.getMoney();
            if (money >= eqOptional.get().getPrice()) {
                userService.saveArmor(id);
                return "redirect:/list_objects";
            } else{
                model.addAttribute("message", "You don't have any money left, go work or something");
                return "sp_errors";
            }
        }
        model.addAttribute("message", "Could not purchase, doesnt exist");
        return "sp_errors";
    }

    @GetMapping("/download_image")
    public ResponseEntity<Object> downloadImage(Model model, HttpSession session) throws MalformedURLException {

        Character character = userService.getCharacter();
        Path imagePath = IMAGES_FOLDER.resolve(character.getImageName());
        Resource image = new UrlResource(imagePath.toUri());
        String contentType;

        try { //gets the type of the image
            contentType = Files.probeContentType(imagePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
        } catch (IOException e) {
            contentType = "application/octet-stream";
        }

        //returns the image
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(image);
    }

    @PostMapping("/equipWeapon")
    public String equipWeapon(@RequestParam long id, Model model) {
        
        Character character = userService.getCharacter();
        Optional<Weapon> equipment = equipmentService.findWeaponById(id);

        if(equipment.isPresent()){ //if it exists
        
                characterService.equipWeapon(equipment.get(), character); //equips it, adding the necessary attributes
            
            return "redirect:/";

        }else{
            model.addAttribute("message", "Could not equip, doesnt exist");
            return "sp_errors";
        }
    }
    @PostMapping("/equipArmor")
    public String equipArmor(@RequestParam long id, Model model) {
        
        Character character = userService.getCharacter();
        Optional<Armor> equipment = equipmentService.findArmorById(id);

        if(equipment.isPresent()){ //if it exists
        
                characterService.equipArmor(equipment.get(), character); //equips it, adding the necessary attributes
            
            return "redirect:/";

        }else{
            model.addAttribute("message", "Could not equip, doesnt exist");
            return "sp_errors";
        }
    }

    @PostMapping("/unEquipWeapon")
    public String unEquipWeapon(@RequestParam long id, Model model) {
        
        Character character = userService.getCharacter();
        Optional<Weapon> equipment = equipmentService.findWeaponById(id);

        if(equipment.isPresent()){
      
        characterService.unEquipWeapon(character, id);
               
            return "redirect:/";

        }else{
            model.addAttribute("message", "Could not unEquip, doesnt exist");
            return "sp_errors";
        }
    }
    @PostMapping("/unEquipArmor")
    public String unEquipArmor(@RequestParam long id, Model model) {
        
        Character character = userService.getCharacter();
        Optional<Armor> equipment = equipmentService.findArmorById(id);

        if(equipment.isPresent()){
      
        characterService.unEquipArmor(character, id);
               
            return "redirect:/";

        }else{
            model.addAttribute("message", "Could not unEquip, doesnt exist");
            return "sp_errors";
        }
    }

    @GetMapping("/image/{imageName}")
    public ResponseEntity<Object> getImage(Model model, @PathVariable String imageName ) throws MalformedURLException {
        
        Path IMP_IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "images", "imp_imgs");
        Path imagePath = IMP_IMAGES_FOLDER.resolve(imageName);
        Resource image = new UrlResource(imagePath.toUri());
        String contentType;

        try { //gets the type of the image
            contentType = Files.probeContentType(imagePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
        } catch (IOException e) {
            contentType = "application/octet-stream";
        }

        //returns the image
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(image);
    }
    
    
}