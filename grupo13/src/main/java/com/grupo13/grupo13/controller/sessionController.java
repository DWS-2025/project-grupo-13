package com.grupo13.grupo13.controller;
import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.DTOs.ArmorBasicDTO;
import com.grupo13.grupo13.DTOs.ArmorDTO;
import com.grupo13.grupo13.DTOs.CharacterDTO;
import com.grupo13.grupo13.DTOs.UserDTO;
import com.grupo13.grupo13.DTOs.WeaponBasicDTO;
import com.grupo13.grupo13.DTOs.WeaponDTO;
import com.grupo13.grupo13.mapper.CharacterMapper;
import com.grupo13.grupo13.service.ArmorService;
import com.grupo13.grupo13.service.CharacterService;
import com.grupo13.grupo13.service.UserService;
import com.grupo13.grupo13.service.WeaponService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import com.grupo13.grupo13.util.InputSanitizer;
@Controller
public class sessionController {

    // attributes
    @Autowired
    private UserService userService;
    @Autowired
    private WeaponService weaponService;
    @Autowired
    private ArmorService armorService;
    @Autowired
    private CharacterService characterService;
    @Autowired
    private CharacterMapper characterMapper;




    @GetMapping("/character")
    public String index(Model model, HttpSession session) {

        List<WeaponBasicDTO> currentInventoryWeapon = userService.currentUserInventoryWeapon();
        List<ArmorBasicDTO> currentInventoryArmor = userService.currentUserInventoryArmor();
        CharacterDTO characterDTO = userService.getCharacter();

        // gets the character and its inventory for mustache
        model.addAttribute("character", characterDTO);
        model.addAttribute("currentWeapon", currentInventoryWeapon);
        model.addAttribute("currentArmor", currentInventoryArmor);
        model.addAttribute("user", userService.getLoggedUserDTO());

        // checks if the user has "logged", in the first fase is creating the character
        if (characterDTO == null) {
            return "index";
        } else {
            return "character_view";
        }
    }
    @GetMapping("/user")
    public String user(Model model, HttpServletRequest request) {

        UserDTO user = userService.getLoggedUserDTO();
        model.addAttribute("user", user);
        return "profile";
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
        nameOfCharacter= InputSanitizer.whitelistSanitize(nameOfCharacter);
        imageName= InputSanitizer.whitelistSanitize(imageName);
        characterDesc= InputSanitizer.sanitizeRichText(characterDesc);
        if (!InputSanitizer.isImageValid(characterImage)) {
            model.addAttribute("message", "File not allowed or missing");
            return "sp_errors";
        }

        Character character = new Character(characterDesc, nameOfCharacter);
        CharacterDTO characterDTO = characterMapper.toDTO(character);

        CharacterDTO savedCharacterDTO = characterService.save(characterDTO);

        // saves the character in the repository
        userService.saveCharacter(savedCharacterDTO);
        characterService.saveUser(savedCharacterDTO);    
        characterService.save(savedCharacterDTO, characterImage, imageName);
        userService.save(userService.getLoggedUserDTO());

        /* // saves image in the correspondent folder
        Files.createDirectories(IMAGES_FOLDER);
        Path imagePath = IMAGES_FOLDER.resolve(imageName);
        characterImage.transferTo(imagePath);
        */

        model.addAttribute("character", character);
        List<WeaponBasicDTO> currentWeapon = userService.currentUserInventoryWeapon();
        List<ArmorBasicDTO> currentArmor = userService.currentUserInventoryArmor();
        model.addAttribute("currentW", currentWeapon);
        model.addAttribute("currentA", currentArmor);
        model.addAttribute("user", userService.getLoggedUserDTO());

        return "character_view";
    }

    @GetMapping("/")
    public String redirectToProfile() {
        return "redirect:/list_weapons";
    }
   
    //used to show the weapons on the shop
    @GetMapping("/list_weapons")
    public String showWeapons(Model model, @PageableDefault(size = 3) Pageable pageable) {
        Page<WeaponBasicDTO> showedWeapons;
        if(userService.getLoggedUserDTOOrNull() != null){
            Page<WeaponBasicDTO> allWeapons = weaponService.findAllBasic(pageable);
            List<WeaponBasicDTO> weaponsList = new LinkedList<>();
            for(WeaponBasicDTO weap : allWeapons){
                if(!userService.hasWeapon(weap)){
                    weaponsList.addLast(weap);
                }
            }
            showedWeapons = new PageImpl<>(weaponsList, pageable, allWeapons.getNumberOfElements()-weaponsList.size());
            model.addAttribute("user", userService.getLoggedUserDTO());
            model.addAttribute("weapon", showedWeapons);

        } else{
            showedWeapons = weaponService.findAllBasic(pageable);
            model.addAttribute("weapon", showedWeapons);
        }
        //buttons
        boolean hasPrev = showedWeapons.hasPrevious();
        boolean hasNext = showedWeapons.getTotalElements() > (showedWeapons.getNumber() + 1) * showedWeapons.getSize();
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", showedWeapons.getNumber() - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", showedWeapons.getNumber() + 1);
        model.addAttribute("size", showedWeapons.getSize());
        return "listing_weapons";
    }

    //used to show the armors on the shop
    @GetMapping("/list_armors")
    public String showArmors(Model model, @PageableDefault(size = 2) Pageable pageable) {
        Page<ArmorBasicDTO> showedArmors;
        if(userService.getLoggedUserDTOOrNull() != null){
            Page<ArmorBasicDTO> allArmors = armorService.findAllBasic(pageable);
            List<ArmorBasicDTO> armorsList = new LinkedList<>();
            for(ArmorBasicDTO arm : allArmors){
                if(!userService.hasArmor(arm)){
                    armorsList.addLast(arm);
                }
            }
            showedArmors = new PageImpl<>(armorsList, pageable, allArmors.getNumberOfElements()-armorsList.size());
            model.addAttribute("user", userService.getLoggedUserDTO());
            model.addAttribute("armor", showedArmors);

        } else{
            showedArmors = armorService.findAllBasic(pageable);
            model.addAttribute("armor", showedArmors);
        }
        //buttons
        boolean hasPrev = showedArmors.hasPrevious();
        boolean hasNext = showedArmors.getTotalElements() > (showedArmors.getNumber() + 1) * showedArmors.getSize();
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", showedArmors.getNumber() - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", showedArmors.getNumber() + 1);
        model.addAttribute("size", showedArmors.getSize());
        return "listing_armors";
    }

    @GetMapping("/search")
    public String search() {

        return "search";
    }
  @GetMapping("weaponview/{id}")
    public String showWeapon(Model model, @PathVariable long id){
        WeaponDTO weapon = weaponService.findById(id);

        if (weapon != null) {
            model.addAttribute("weapon", weapon);
            return "view_weapon";
        }else{
            return "error";
        }
    }

    @PostMapping("/purchaseWeapon")
    public String purchaseWeapon(@RequestParam long id, Model model) {
        WeaponDTO weaponDTO = weaponService.findById(id);
        
            // checks if the user has enough money or not
            if (weaponDTO == null) {
            model.addAttribute("message", "Could not purchase, doesnt exist");
            return "sp_errors";
            }
            if(userService.hasWeapon(id)){
            model.addAttribute("message", "You alredy own that weapon");
            return "sp_errors";

            }else {
            long urid=userService.getLoggedUser().getId();
            if (userService.getMoney(urid) >= weaponDTO.price()) {
                userService.setMoney(urid, userService.getMoney(urid)-weaponDTO.price());
                userService.saveWeapon(id);
                return "redirect:/list_weapons";
            } else {
                model.addAttribute("message", "You don't have any money left, go work or something");
                return "sp_errors";
            }
        }
    }

    @PostMapping("/purchaseArmor")
    public String purchaseArmor(@RequestParam long id, Model model) {
        ArmorDTO armorDTO = armorService.findById(id);
        // checks if the user has enough money or not
        if (armorDTO == null) {
            model.addAttribute("message", "Could not purchase, doesnt exist");
            return "sp_errors";
        }
         if(userService.hasArmor(id)){
            model.addAttribute("message", "You alredy own that armor");
            return "sp_errors";

            } else {
             long urid=userService.getLoggedUser().getId();

            if (userService.getMoney(urid) >= armorDTO.price()) {
                 userService.setMoney(urid, userService.getMoney(urid)-armorDTO.price());
                 userService.saveArmor(id);
                 armorService.save(armorDTO);
                 return "redirect:/list_armors";
            } else {
                model.addAttribute("message", "You don't have any money left, go work or something");
                return "sp_errors";
            }
        }

    }

    @PostMapping("/equipWeapon")
    public String equipWeapon(@RequestParam long id, Model model) {
        CharacterDTO characterDTO = userService.getCharacter();
        Character character = characterMapper.toDomain(characterDTO);
        WeaponDTO weaponDTO = weaponService.findById(id);

        if (weaponDTO != null) { // if it exists
            characterService.equipWeapon(weaponDTO, character.getId()); // equips it, adding the necessary attributes
            weaponService.addCharacter(characterDTO, weaponDTO);

            return "redirect:/";
        } else {
            model.addAttribute("message", "Could not equip, doesnt exist");
            return "sp_errors";
        }
    }

    @PostMapping("/equipArmor")
    public String equipArmor(@RequestParam long id, Model model) {
        CharacterDTO characterDTO= userService.getCharacter();
        Character character = characterMapper.toDomain(characterDTO);
        ArmorDTO armorDTO = armorService.findById(id);

        if (armorDTO != null) { // if it exists
            characterService.equipArmor(armorDTO, character.getId()); // equips it, adding the necessary attributes
            armorService.addCharacter(characterDTO, armorDTO);

            return "redirect:/";
        } else {
            model.addAttribute("message", "Could not equip, doesnt exist");
            return "sp_errors";
        }
    }

    @PostMapping("/unEquipWeapon")
    public String unEquipWeapon(@RequestParam long id, Model model) {
        CharacterDTO characterDTO = userService.getCharacter();
        Character character = characterMapper.toDomain(characterDTO);
        WeaponDTO weaponDTO = weaponService.findById(id);

        if (weaponDTO != null) {
            characterService.unEquipWeapon(character.getId(), id); // unequips it

            return "redirect:/";
        } else {
            model.addAttribute("message", "Could not unEquip, doesnt exist");
            return "sp_errors";
        }
    }

    @PostMapping("/unEquipArmor")
    public String unEquipArmor(@RequestParam long id, Model model) {
        CharacterDTO characterDTO = userService.getCharacter();
        Character character = characterMapper.toDomain(characterDTO);
        ArmorDTO armorDTO = armorService.findById(id);

        if (armorDTO != null) {
            characterService.unEquipArmor(character.getId(), id); // unequips it

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

  /*  @GetMapping("/Weapon/{id}/image")
    public ResponseEntity<Object> downloadImageWeapon(@PathVariable long id) throws SQLException {
        WeaponDTO op = weaponService.findById(id);
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
 */
    @GetMapping("/character/{id}/image")
    public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException {
        CharacterDTO characterDTO = characterService.findById(id);
        Character character = characterMapper.toDomain(characterDTO);

        if (character != null && character.getImageFile() != null) {
            Blob image = character.getImageFile();
            Resource file = new InputStreamResource(image.getBinaryStream());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(image.length()).body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}