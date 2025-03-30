package com.grupo13.grupo13.controller;
import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.repository.ArmorRepository;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.service.ArmorService;
import com.grupo13.grupo13.service.CharacterService;
import com.grupo13.grupo13.service.UserService;
import com.grupo13.grupo13.service.WeaponService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

@Controller
public class sessionController {

    // attributes
    @Autowired
    private ArmorRepository armorRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private WeaponService weaponService;

    @Autowired
    private ArmorService armorService;

    @Autowired
    private CharacterService characterService;
    private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");

    @GetMapping("/")
    public String index(Model model, HttpSession session) {

        List<Weapon> currentInventoryWeapon = userService.currentUserInventoryWeapon();

        List<Armor> currentInventoryArmor = userService.currentUserInventoryArmor();
        Character character = userService.getCharacter();

        // gets the character and its inventory for mustache
        model.addAttribute("character", character);
        model.addAttribute("currentWeapon", currentInventoryWeapon);
        model.addAttribute("currentArmor", currentInventoryArmor);
        model.addAttribute("user", userService.getLoggedUser().get());

        // checks if the user has "logged", in the first fase is creating the character
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
        // creates the character
        Character character = new Character(characterDesc, nameOfCharacter);

        // saves the character in the repository
        userService.saveCharacter(character);
        characterService.saveUser(character);
        characterService.save(character, characterImage);
        userService.save(userService.getLoggedUser().get());

        /* // saves image in the correspondent folder
        Files.createDirectories(IMAGES_FOLDER);
        Path imagePath = IMAGES_FOLDER.resolve(imageName);
        characterImage.transferTo(imagePath);
        */

        model.addAttribute("character", character);
        List<Weapon> currentWeapon = userService.currentUserInventoryWeapon();
        List<Armor> currentArmor = userService.currentUserInventoryArmor();
        model.addAttribute("currentW", currentWeapon);
        model.addAttribute("currentA", currentArmor);
        model.addAttribute("user", userService.getLoggedUser().get());

        return "character_view";
    }

    @GetMapping("/list_objects")
    public String iterationObj(Model model, HttpSession session, Pageable page) {

        List<Weapon> equipmentListWeapon = weaponService.findAll();
        List<Armor> equipmentListArmor = armorService.findAll();

        ArrayList<Weapon> availableWeapons = new ArrayList<>();
        ArrayList<Armor> availableArmors = new ArrayList<>();
        List<Weapon> currentInventoryWeapons = userService.currentUserInventoryWeapon();
        List<Armor> currentInventoryArmors = userService.currentUserInventoryArmor();

        // gets the listing of the current equipment in the repository and the inventory
        // creates a list of the equipment that the user doesnt have
        // the html will present the inventory as purchased and the not purchased
        // (available) equipments
        for (Weapon equipmentWeapon : equipmentListWeapon) {
            if (!currentInventoryWeapons.contains(equipmentWeapon)) {
                availableWeapons.add(equipmentWeapon);
            }
        }
        for (Armor equipmentArmor : equipmentListArmor) {
            if (!currentInventoryArmors.contains(equipmentArmor)) {
                availableArmors.add(equipmentArmor);
            }
        }
        model.addAttribute("user", userService.getLoggedUser().get());
        model.addAttribute("currentA", currentInventoryArmors);
        model.addAttribute("currentW", currentInventoryWeapons);
        model.addAttribute("availableA", availableArmors);
        model.addAttribute("availableW", availableWeapons);

        model.addAttribute("armor", armorRepository.findAll(page));
        boolean hasPrev = page.getPageNumber() >= 1;
        boolean hasNext = (page.getPageNumber()*page.getPageSize()) < armorRepository.count();

        model.addAttribute("hasPrev", hasPrev);
		model.addAttribute("prev", page.getPageNumber() - 1);
		model.addAttribute("hasNext", hasNext);
		model.addAttribute("next", page.getPageNumber() + 1);

        return "listing";
    }

    @PostMapping("/purchaseWeapon")
    public String purchaseWeapon(@RequestParam long id, Model model) {

        Optional<Weapon> eqOptional = weaponService.findById(id);
        if (eqOptional.isPresent()) {
            // checks if the user has enough money or if it's homeless
            int money = userService.getMoney();
            if (money >= eqOptional.get().getPrice()) {
                userService.saveWeapon(id);
                return "redirect:/list_objects";
            } else {
                model.addAttribute("message", "You don't have any money left, go work or something");
                return "sp_errors";
            }
        }
        model.addAttribute("message", "Could not purchase, doesnt exist");
        return "sp_errors";
    }

    @PostMapping("/purchaseArmor")
    public String purchaseArmor(@RequestParam long id, Model model) {

        Optional<Armor> eqOptional = armorService.findById(id);
        if (eqOptional.isPresent()) {
            // checks if the user has enough money or if it's homeless
            int money = userService.getMoney();
            if (money >= eqOptional.get().getPrice()) {
                userService.saveArmor(id);
                armorService.save(eqOptional.get());
                return "redirect:/list_objects";
            } else {
                model.addAttribute("message", "You don't have any money left, go work or something");
                return "sp_errors";
            }
        }
        model.addAttribute("message", "Could not purchase, doesnt exist");
        return "sp_errors";
    }

    @PostMapping("/equipWeapon")
    public String equipWeapon(@RequestParam long id, Model model) {

        Character character = userService.getCharacter();
        Optional<Weapon> equipment = weaponService.findById(id);

        if (equipment.isPresent()) { // if it exists

            characterService.equipWeapon(equipment.get(), character); // equips it, adding the necessary attributes
            weaponService.addCharacter(character, equipment.get());

            return "redirect:/";

        } else {
            model.addAttribute("message", "Could not equip, doesnt exist");
            return "sp_errors";
        }
    }

    @PostMapping("/equipArmor")
    public String equipArmor(@RequestParam long id, Model model) {

        Character character = userService.getCharacter();
        Optional<Armor> equipment = armorService.findById(id);

        if (equipment.isPresent()) { // if it exists

            characterService.equipArmor(equipment.get(), character); // equips it, adding the necessary attributes
            armorService.addCharacter(character, equipment.get());

            return "redirect:/";

        } else {
            model.addAttribute("message", "Could not equip, doesnt exist");
            return "sp_errors";
        }
    }

    @PostMapping("/unEquipWeapon")
    public String unEquipWeapon(@RequestParam long id, Model model) {

        Character character = userService.getCharacter();
        Optional<Weapon> equipment = weaponService.findById(id);

        if (equipment.isPresent()) {

            characterService.unEquipWeapon(character, id);

            return "redirect:/";

        } else {
            model.addAttribute("message", "Could not unEquip, doesnt exist");
            return "sp_errors";
        }
    }

    @PostMapping("/unEquipArmor")
    public String unEquipArmor(@RequestParam long id, Model model) {

        Character character = userService.getCharacter();
        Optional<Armor> equipment = armorService.findById(id);

        if (equipment.isPresent()) {

            characterService.unEquipArmor(character, id);

            return "redirect:/";

        } else {
            model.addAttribute("message", "Could not unEquip, doesnt exist");
            return "sp_errors";
        }
    }

    @GetMapping("/image/{imageName}")
    public ResponseEntity<Object> getImage(Model model, @PathVariable String imageName) throws MalformedURLException {

        Path IMP_IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "images", "imp_imgs");
        Path imagePath = IMP_IMAGES_FOLDER.resolve(imageName);
        Resource image = new UrlResource(imagePath.toUri());
        String contentType;

        try { // gets the type of the image
            contentType = Files.probeContentType(imagePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
        } catch (IOException e) {
            contentType = "application/octet-stream";
        }

        // returns the image
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(image);
    }

    @GetMapping("/Weapon/{id}/image")
    public ResponseEntity<Object> downloadImageWeapon(@PathVariable long id) throws SQLException {
        Optional<Weapon> op = weaponService.findById(id);
        if (op.isPresent() && op.get().getimageFile() != null) {
            Blob image = op.get().getimageFile();
            Resource file = new InputStreamResource(image.getBinaryStream());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(image.length()).body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/Armor/{id}/image")
    public ResponseEntity<Object> downloadImageArmor(@PathVariable long id) throws SQLException {
        Optional<Armor> op = armorService.findById(id);
        if (op.isPresent() && op.get().getimageFile() != null) {
            Blob image = op.get().getimageFile();
            Resource file = new InputStreamResource(image.getBinaryStream());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(image.length()).body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/character/{id}/image")
    public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException {
        Optional<Character> op = characterService.findById(id);
        if (op.isPresent() && op.get().getImageFile() != null) {
            Blob image = op.get().getImageFile();
            Resource file = new InputStreamResource(image.getBinaryStream());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(image.length()).body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}