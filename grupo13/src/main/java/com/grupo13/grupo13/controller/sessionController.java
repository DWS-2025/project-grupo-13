package com.grupo13.grupo13.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.model.User;
import com.grupo13.grupo13.DTOs.ArmorBasicDTO;
import com.grupo13.grupo13.DTOs.ArmorDTO;
import com.grupo13.grupo13.DTOs.CharacterBasicDTO;
import com.grupo13.grupo13.DTOs.CharacterDTO;
import com.grupo13.grupo13.DTOs.UserDTO;
import com.grupo13.grupo13.DTOs.WeaponBasicDTO;
import com.grupo13.grupo13.DTOs.WeaponDTO;
import com.grupo13.grupo13.DTOs.WeaponSearchDTO;
import com.grupo13.grupo13.mapper.CharacterMapper;
import com.grupo13.grupo13.mapper.UserMapper;
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

    private static final Path BACKUP_FOLDER = Paths.get("").toAbsolutePath()
            .resolve("backups")
            .resolve("characters")
            .normalize();

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

    @Autowired
    private UserMapper userMapper;

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
        List<WeaponBasicDTO> currentInventoryWeapon = userService.currentUserInventoryWeapon();
        List<ArmorBasicDTO> currentInventoryArmor = userService.currentUserInventoryArmor();
        CharacterDTO characterDTO = userService.getCharacter();

        // gets the character and its inventory for mustache
        model.addAttribute("character", characterDTO);
        model.addAttribute("currentWeapon", currentInventoryWeapon);
        model.addAttribute("currentArmor", currentInventoryArmor);

        UserDTO user = userService.getLoggedUserDTO();
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/formProcess")
    public String procesarFormulario(Model model, HttpSession session, @RequestParam String nameOfCharacter,
            @RequestParam String characterDesc,
            @RequestParam MultipartFile characterImage) throws IOException {

        if (nameOfCharacter.length() > 255) {
            model.addAttribute("message",
                    "You have 4 457 328 976 628 032 310 960 505 682 458 941 198 711 991 549 516 106 627 559 418 874 736 854 616 990 926 154 563 233 442 050 001 775 332 602 367 762 026 062 514 350 490 020 288 118 293 032 540 896 850 538 206 460 041 208 241 186 662 921 960 227 090 636 748 910 840 240 756 196 348 773 114 755 617 650 747 164 485 025 018 892 794 341 880 385 569 578 419 234 397 708 485 601 847 108 758 503 103 414 694 794 735 059 509 671 326 329 177 465 338 412 391 856 003 420 968 070 605 896 652 214 953 420 282 507 508 205 447 599 347 588 543 298 651 133 082 200 882 973 508 651 096 860 161 307 061 515 481 851 387 590 630 802 753 643 options and still want a longer name?");
            return "sp_errors";
        }
        if (characterDesc.length() > 255) {
            model.addAttribute("message", "We don't want your life story, be brief.");
            return "sp_errors";
        }
        if (nameOfCharacter.isBlank() || characterDesc.isBlank()) {
            model.addAttribute("message", "Some or all parameters were left blank");
            return "sp_errors";
        }
        // creates the character
        InputSanitizer.validateWhitelist(nameOfCharacter);
        characterDesc = InputSanitizer.sanitizeRichText(characterDesc);
        if (!InputSanitizer.isImageValid(characterImage)) {
            model.addAttribute("message", "File not allowed or missing: you must upload a jpg file.");
            return "sp_errors";
        }
        InputSanitizer.checkIfNameEmpty(characterImage);
        String originalFilename = characterImage.getOriginalFilename();
        InputSanitizer.validateWhitelist(originalFilename);
        int dotIndex = originalFilename.lastIndexOf('.');
        String baseName = (dotIndex == -1) ? originalFilename : originalFilename.substring(0, dotIndex);
        String extension = (dotIndex == -1) ? "" : originalFilename.substring(dotIndex);
        String imageName = baseName + "-" + userService.getLoggedUser().getUserName() + extension;
        if (imageName.equals(null)) {
            model.addAttribute("message", "Make sure the image has a valid name.");
            return "sp_errors";
        }
        characterService.checkImageName(imageName);
        Character character = new Character(characterDesc, nameOfCharacter, imageName);
        CharacterDTO characterDTO = characterMapper.toDTO(character);
        CharacterDTO savedCharacterDTO = characterService.save(characterDTO);

        // saves the character in the repository
        userService.saveCharacter(savedCharacterDTO);
        characterService.save(savedCharacterDTO, characterImage, imageName);
        characterService.saveUser(savedCharacterDTO);
        userService.save(userService.getLoggedUserDTO());

        model.addAttribute("character", character);
        List<WeaponBasicDTO> currentWeapon = userService.currentUserInventoryWeapon();
        List<ArmorBasicDTO> currentArmor = userService.currentUserInventoryArmor();
        model.addAttribute("currentW", currentWeapon);
        model.addAttribute("currentA", currentArmor);
        model.addAttribute("user", userService.getLoggedUserDTO());
        return "redirect:/character";
    }

    @GetMapping("/")
    public String redirectToProfile() {
        return "redirect:/weaponshop";
    }

    // used to show the weapons on the shop
    @GetMapping("/weaponshop")
    public String showWeapons(Model model, Pageable pageable) {
        model.addAttribute("user", userService.getLoggedUserDTOOrNull());
        return "listing_weapons";
    }

    // used to show the armors on the shop
    @GetMapping("/armorshop")
    public String showArmors(Model model, Pageable pageable) {
        model.addAttribute("user", userService.getLoggedUserDTOOrNull());
        return "listing_armors";
    }

    @GetMapping("/search")
    public String searchWeapons(Model model, @ModelAttribute WeaponSearchDTO probe) {
        // set 0 as null
        Integer strength = (probe.strength() != null && probe.strength() != 0) ? probe.strength() : null;
        Integer price = (probe.price() != null && probe.price() != 0) ? probe.price() : null;
        Integer intimidation = (probe.intimidation() != null && probe.intimidation() != 0) ? probe.intimidation()
                : null;
        String name = (probe.name() != null && !probe.name().isBlank()) ? probe.name() : null;
        String description = (probe.description() != null && !probe.description().isBlank()) ? probe.description()
                : null;

        int nonNullFields = 0;
        if (name != null)
            nonNullFields++;
        if (description != null)
            nonNullFields++;
        if (strength != null)
            nonNullFields++;
        if (price != null)
            nonNullFields++;
        if (intimidation != null)
            nonNullFields++;

        if (nonNullFields >= 2) {
            model.addAttribute("weapons", weaponService.search(probe));
        } else {
            model.addAttribute("error", "Search with at least 2 fields");
            model.addAttribute("weapons", weaponService.findAll());
        }
        return "search";
    }

    @GetMapping("weaponview/{id}")
    public String showWeapon(Model model, @PathVariable long id) {
        WeaponDTO weapon = weaponService.findByIdDTO(id);
        if (weapon != null) {
            model.addAttribute("weapon", weapon);
            return "view_weapon";
        } else {
            return "error";
        }
    }

    @PostMapping("/purchaseWeapon")
    public String purchaseWeapon(@RequestParam long id, Model model) {
        WeaponDTO weaponDTO = weaponService.findByIdDTO(id);
        if (weaponDTO == null) {
            model.addAttribute("message", "Could not purchase, doesnt exist");
            return "sp_errors";
        }
        if (userService.hasWeapon(id)) {
            model.addAttribute("message", "You alredy own that weapon");
            return "sp_errors";
        } else {
            long urid = userService.getLoggedUser().getId();
            if (userService.getMoney(urid) >= weaponDTO.price()) {
                userService.setMoney(urid, userService.getMoney(urid) - weaponDTO.price());
                userService.saveWeapon(id);
                return "redirect:/weaponshop";
            } else {
                model.addAttribute("message", "You don't have any money left, go work or something");
                return "sp_errors";
            }
        }
    }

    @PostMapping("/purchaseArmor")
    public String purchaseArmor(@RequestParam long id, Model model) {
        ArmorDTO armorDTO = armorService.findByIdDTO(id);
        // checks if the user has enough money or not
        if (armorDTO == null) {
            model.addAttribute("message", "Could not purchase, doesnt exist");
            return "sp_errors";
        }
        if (userService.hasArmor(id)) {
            model.addAttribute("message", "You alredy own that armor");
            return "sp_errors";
        } else {
            long urid = userService.getLoggedUser().getId();
            if (userService.getMoney(urid) >= armorDTO.price()) {
                userService.setMoney(urid, userService.getMoney(urid) - armorDTO.price());
                userService.saveArmor(id);
                return "redirect:/armorshop";
            } else {
                model.addAttribute("message", "You don't have any money left, go work or something");
                return "sp_errors";
            }
        }
    }

    @PostMapping("/equipWeapon")
    public String equipWeapon(@RequestParam long id, Model model) {
        CharacterDTO characterDTO = userService.getCharacter();
        WeaponDTO weaponDTO = weaponService.findByIdDTO(id);
        if (weaponDTO != null) { // if it exists
            characterService.equipWeapon(weaponDTO, characterDTO.id()); // equips it, adding the necessary attributes
            // weaponService.addCharacter(characterDTO, weaponDTO);
            return "redirect:/character";
        } else {
            model.addAttribute("message", "Could not equip, doesnt exist");
            return "sp_errors";
        }
    }

    @PostMapping("/equipArmor")
    public String equipArmor(@RequestParam long id, Model model) {
        CharacterDTO characterDTO = userService.getCharacter();
        ArmorDTO armorDTO = armorService.findByIdDTO(id);
        if (armorDTO != null) { // if it exists
            characterService.equipArmor(armorDTO, characterDTO.id()); // equips it, adding the necessary attributes
            // armorService.addCharacter(characterDTO, armorDTO);
            return "redirect:/character";
        } else {
            model.addAttribute("message", "Could not equip, doesnt exist");
            return "sp_errors";
        }
    }

    @PostMapping("/unEquipWeapon")
    public String unEquipWeapon(@RequestParam long id, Model model) {
        CharacterDTO characterDTO = userService.getCharacter();
        Character character = characterMapper.toDomain(characterDTO);
        WeaponDTO weaponDTO = weaponService.findByIdDTO(id);
        if (weaponDTO != null) {
            characterService.unEquipWeapon(character.getId(), id); // unequips it
            return "redirect:/character";
        } else {
            model.addAttribute("message", "Could not unEquip, doesnt exist");
            return "sp_errors";
        }
    }

    @PostMapping("/unEquipArmor")
    public String unEquipArmor(@RequestParam long id, Model model) {
        CharacterDTO characterDTO = userService.getCharacter();
        Character character = characterMapper.toDomain(characterDTO);
        ArmorDTO armorDTO = armorService.findByIdDTO(id);
        if (armorDTO != null) {
            characterService.unEquipArmor(character.getId(), id); // unequips it
            return "redirect:/character";
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

    @GetMapping("/character/{id}/image")
    public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException {
        CharacterDTO characterDTO = characterService.findByIdDTO(id);
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

    @GetMapping("/editUser")
    public String editUser(Model model, HttpSession session) {
        model.addAttribute("user", userService.getLoggedUserDTO());
        return "edit_user";
    }

    @PostMapping("/editUser")
    public String updateUser(Model model, @RequestParam String userName) throws IOException {
        if (userName.isBlank()) {
            model.addAttribute("message", "The name cannot be left blank");
            return "sp_errors";
        }
        UserDTO oldUserDTO = userService.getLoggedUserDTO();
        User oldUser = userMapper.toDomain(oldUserDTO);
        if (oldUser != null) {
            userService.updateName(oldUserDTO, userName);
            return "/logout";
        } else {
            model.addAttribute("message", "Could not manage, not found");
            return "sp_errors";
        }
    }

    @PostMapping("/deleteUser")
    public String deleteUser(Model model) throws IOException {
        UserDTO u = userService.getLoggedUserDTO();
        userService.deleteUser(u.id());
        return "/logout";
    }

    @GetMapping("/userImage")
    public ResponseEntity<Object> downloadUserImage() throws MalformedURLException {
        CharacterBasicDTO c = userService.getLoggedUserDTO().character();
        String imageName = c.imageName();
        return characterService.returnImage(imageName);
    }

    @PostMapping("/editCharacter")
    public String editCharacter(Model model, @RequestParam String name) {
        CharacterDTO characterDTO = userService.getCharacter();
        if (characterDTO == null) {
            model.addAttribute("message", "Some or all parameters were left blank");
            return "sp_errors";
        } else {
            characterService.editCharacterName(name, characterDTO.id());
            return "redirect:/character";
        }
    }

    @PostMapping("/deleteCharacter")
    public String deleteCharacter(Model model) {
        long userid = userService.getLoggedUserDTO().id();
        UserDTO u = userService.findById(userid);
        if (u.character() != null) {
            userService.deleteCharacter(userid);
        } else {
            model.addAttribute("message", "This user doesn't have a character");
            return "sp_errors";
        }
        return "index";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadImage(Model model) throws IOException, IllegalAccessException {
        try {
            Long id = userService.getCharacter().id();
            Resource image = characterService.downloadImage(id);
            String cleanFileName = image.getFilename();
            String username = userService.getLoggedUserDTO().userName();

            if (cleanFileName != null && username != null) {
                cleanFileName = cleanFileName.replaceFirst("-" + Pattern.quote(username) + "(?=\\.[^.]+$)", "");
            }

            String contentType = "application/octet-stream";
            Path path = Paths.get(image.getURI());
            String detectedType = Files.probeContentType(path);
            if (detectedType != null) {
                contentType = detectedType;
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + cleanFileName + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(image);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The character doesn't have an image");
        }
    }
}