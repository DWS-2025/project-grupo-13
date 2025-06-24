package com.grupo13.grupo13.controller;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.grupo13.grupo13.DTOs.ArmorBasicDTO;
import com.grupo13.grupo13.DTOs.ArmorDTO;
import com.grupo13.grupo13.DTOs.CharacterDTO;
import com.grupo13.grupo13.DTOs.UserBasicDTO;
import com.grupo13.grupo13.DTOs.UserDTO;
import com.grupo13.grupo13.DTOs.WeaponBasicDTO;
import com.grupo13.grupo13.DTOs.WeaponDTO;
import com.grupo13.grupo13.mapper.WeaponMapper;
import com.grupo13.grupo13.mapper.armorMapper;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.service.ArmorService;
import com.grupo13.grupo13.service.CharacterService;
import com.grupo13.grupo13.service.UserService;
import com.grupo13.grupo13.service.WeaponService;
import com.grupo13.grupo13.util.InputSanitizer;

@Controller
public class AdminController {

    //attributes
    @Autowired
    private WeaponService weaponService;
    @Autowired
    private ArmorService armorService;
    @Autowired
    private UserService userService;   
    @Autowired
    private armorMapper armorMapper;
    @Autowired
    private WeaponMapper weaponMapper;
    @Autowired
    private CharacterService characterService;

    //private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "src/main/resources/imp_imgs");
      
    @GetMapping("weapon/{id}")
    public String showWeapon(Model model, @PathVariable long id){
        WeaponDTO weapon = weaponService.findByIdDTO(id);
        if (weapon != null) {
            model.addAttribute("weapon", weapon);
            return "show_weapon";
        }else{
            return "error";
        }
    }
    
    @GetMapping("/armor/{id}")
    public String showArmor(Model model, @PathVariable long id) {
        ArmorDTO armor = armorService.findByIdDTO(id);
        if(armor != null){
            model.addAttribute("armor", armor);
            return "show_armor";
        }else{
            return "error";
        }
    }

    @GetMapping("/new_armor")
    public String newArmor(Model model) {
        model.addAttribute("user", userService.getLoggedUserDTO());
        return "new_armor";
    }

    @GetMapping("/new_weapon")
    public String newWeapon(Model model) {
        model.addAttribute("user", userService.getLoggedUserDTO());
        return "new_weapon";
    }

    @PostMapping("/weapon/new")
    public String newWeapon(Model model, @RequestParam String name, @RequestParam int intimidation,
            @RequestParam int strength, @RequestParam String description, @RequestParam int price, 
            @RequestParam MultipartFile weaponImage) throws IOException {

        if (name.isBlank()||description.isBlank()|| weaponImage.isEmpty() || !InputSanitizer.isImageValid(weaponImage)) {
            model.addAttribute("message", "Some or all parameters were left blank");
            return "sp_errors";
        }
        if (name.length()>255) {
            model.addAttribute("message", "You have 4 457 328 976 628 032 310 960 505 682 458 941 198 711 991 549 516 106 627 559 418 874 736 854 616 990 926 154 563 233 442 050 001 775 332 602 367 762 026 062 514 350 490 020 288 118 293 032 540 896 850 538 206 460 041 208 241 186 662 921 960 227 090 636 748 910 840 240 756 196 348 773 114 755 617 650 747 164 485 025 018 892 794 341 880 385 569 578 419 234 397 708 485 601 847 108 758 503 103 414 694 794 735 059 509 671 326 329 177 465 338 412 391 856 003 420 968 070 605 896 652 214 953 420 282 507 508 205 447 599 347 588 543 298 651 133 082 200 882 973 508 651 096 860 161 307 061 515 481 851 387 590 630 802 753 643 options and still want a longer name?");
            return "sp_errors";
        }
        if (description.length()>255) {
            model.addAttribute("message", "This is not wikipedia. Be brief.");
            return "sp_errors";
        }
        if(strength>2147483647 || price>2147483647 || intimidation>2147483647 || strength<0 || intimidation<0 || price<0){
            model.addAttribute("message", "We are trying to make a balanced game, change the stats.");
            return "sp_errors";            
        }

        InputSanitizer.validateWhitelist(name);
        InputSanitizer.validateWhitelist(description);        
        Weapon weapon = new Weapon(name, description, intimidation, strength, price);
        WeaponDTO weaponDTO = weaponMapper.toDTO(weapon);
        weaponService.saveDTO(weaponDTO, weaponImage);
        return "saved_weapon";
    }

    @PostMapping("/armor/new")
    public String newArmor(Model model, @RequestParam String name, @RequestParam int style,
            @RequestParam int defense, @RequestParam String description, @RequestParam int price,
            @RequestParam MultipartFile armorImage) throws IOException {

        if (name.isBlank()||description.isBlank()|| armorImage.isEmpty() || !InputSanitizer.isImageValid(armorImage)) {
            model.addAttribute("message", "Some or all parameters were left blank");
            return "sp_errors";
        }
        if (name.length()>255) {
            model.addAttribute("message", "You have 4 457 328 976 628 032 310 960 505 682 458 941 198 711 991 549 516 106 627 559 418 874 736 854 616 990 926 154 563 233 442 050 001 775 332 602 367 762 026 062 514 350 490 020 288 118 293 032 540 896 850 538 206 460 041 208 241 186 662 921 960 227 090 636 748 910 840 240 756 196 348 773 114 755 617 650 747 164 485 025 018 892 794 341 880 385 569 578 419 234 397 708 485 601 847 108 758 503 103 414 694 794 735 059 509 671 326 329 177 465 338 412 391 856 003 420 968 070 605 896 652 214 953 420 282 507 508 205 447 599 347 588 543 298 651 133 082 200 882 973 508 651 096 860 161 307 061 515 481 851 387 590 630 802 753 643 options and still want a longer name?");
            return "sp_errors";
        }
        if (description.length()>255) {
            model.addAttribute("message", "This is not wikipedia. Be brief.");
            return "sp_errors";
        }
        if(defense>2147483647 || price>2147483647 || style>2147483647 || defense<0 || style<0 || price<0){
            model.addAttribute("message", "We are trying to make a balanced game, change the stats.");
            return "sp_errors";            
        }
        InputSanitizer.validateWhitelist(name);
        InputSanitizer.validateWhitelist(description);
        Armor armor = new Armor(name, description, style, defense, price);
        ArmorDTO armorDTO = armorMapper.toDTO(armor);
        armorService.saveDTO(armorDTO, armorImage);
        return "saved_armor";
    }

    @PostMapping("/weapon/{id}/delete")
    public String deleteWeapon(Model model, @PathVariable long id) throws IOException{
        weaponService.delete(id);
        return "deleted_weapon";
    }

    @PostMapping("/armor/{id}/delete")
    public String deleteArmor(Model model, @PathVariable long id) throws IOException{
        armorService.delete(id);
        return "deleted_armor";
    }
    
    @GetMapping("/weapon/{id}/edit")
    public String editWeapon(Model model, @PathVariable long id) {
        WeaponDTO weapon = weaponService.findByIdDTO(id);
        if(weapon != null){
            model.addAttribute("weapon", weapon);
            return "edit_weapon";
        }else{
            return "error";
        }
    }

    @GetMapping("/armor/{id}/edit")
    public String editArmor(Model model, @PathVariable long id) {
        ArmorDTO armor = armorService.findByIdDTO(id);
        if(armor != null){
            model.addAttribute("armor", armor);
            return "edit_armor";
        }else{
            model.addAttribute("message", "Could not manage, not found");
            return "sp_errors";
        }
    }
    
    /*@GetMapping("/delete_Character")
    public String getMethodName(Model model) {
        characterService.delete(userService.getCharacter());
        return "deleted_character";

    }
    */

    @PostMapping("/weapon/{id}/edit")
	public String updateWeapon(Model model, @PathVariable long id, WeaponDTO updatedWeaponDTO, @RequestParam MultipartFile weaponImage) throws IOException{
        Weapon updatedWeapon = weaponMapper.toDomain(updatedWeaponDTO);
        if (updatedWeapon.getName().isBlank() || updatedWeapon.getDescription().isBlank()) {
            model.addAttribute("message", "Some or all parameters were left blank");
            return "sp_errors";
        }
        if (updatedWeapon.getName().length()>255) {
            model.addAttribute("message", "You have 4 457 328 976 628 032 310 960 505 682 458 941 198 711 991 549 516 106 627 559 418 874 736 854 616 990 926 154 563 233 442 050 001 775 332 602 367 762 026 062 514 350 490 020 288 118 293 032 540 896 850 538 206 460 041 208 241 186 662 921 960 227 090 636 748 910 840 240 756 196 348 773 114 755 617 650 747 164 485 025 018 892 794 341 880 385 569 578 419 234 397 708 485 601 847 108 758 503 103 414 694 794 735 059 509 671 326 329 177 465 338 412 391 856 003 420 968 070 605 896 652 214 953 420 282 507 508 205 447 599 347 588 543 298 651 133 082 200 882 973 508 651 096 860 161 307 061 515 481 851 387 590 630 802 753 643 options and still want a longer name?");
            return "sp_errors";
        }
        if (updatedWeapon.getDescription().length()>255) {
            model.addAttribute("message", "This is not wikipedia. Be brief.");
            return "sp_errors";
        }
        if(updatedWeapon.getstrength()>2147483647 || updatedWeapon.getPrice()>2147483647 || updatedWeapon.getIntimidation()>2147483647 || updatedWeapon.getstrength()<0 || updatedWeapon.getIntimidation()<0 || updatedWeapon.getPrice()<0){
            model.addAttribute("message", "We are trying to make a balanced game, change the stats.");
            return "sp_errors";            
        }
        if(updatedWeapon.getstrength()>2147483647 || updatedWeapon.getPrice()>2147483647 || updatedWeapon.getIntimidation()>2147483647 || updatedWeapon.getstrength()<0 || updatedWeapon.getIntimidation()<0 || updatedWeapon.getPrice()<0){
            model.addAttribute("message", "We are trying to make a balanced game, change the stats.");
            return "sp_errors";            
        }

        InputSanitizer.validateWhitelist(updatedWeapon.getName());
        InputSanitizer.validateWhitelist(updatedWeapon.getDescription());
        WeaponDTO editedWeapon = weaponService.findByIdDTO(id);
        if(editedWeapon != null || !InputSanitizer.isImageValid(weaponImage) ){
            weaponService.update(id, updatedWeaponDTO);
            if(!weaponImage.isEmpty()){
                weaponService.replaceImage(id, weaponImage.getInputStream(), weaponImage.getSize());
            }
            return "redirect:/weapon/" + id;
        }else{
            model.addAttribute("message", "Could not manage, not found");
            return "sp_errors";
        }
	}

    @PostMapping("/armor/{id}/edit")
	public String updateArmor(Model model, @PathVariable long id, ArmorDTO updatedArmorDTO, @RequestParam MultipartFile armorImage) throws IOException{
        Armor updatedArmor = armorMapper.toDomain(updatedArmorDTO);
        if (updatedArmor.getName().isBlank() || updatedArmor.getDescription().isBlank() /*|| picture.isBlank()*/ ) {
            model.addAttribute("message", "Some or all parameters were left blank");
            return "sp_errors";
        }
        if (updatedArmor.getName().length()>255) {
            model.addAttribute("message", "You have 4 457 328 976 628 032 310 960 505 682 458 941 198 711 991 549 516 106 627 559 418 874 736 854 616 990 926 154 563 233 442 050 001 775 332 602 367 762 026 062 514 350 490 020 288 118 293 032 540 896 850 538 206 460 041 208 241 186 662 921 960 227 090 636 748 910 840 240 756 196 348 773 114 755 617 650 747 164 485 025 018 892 794 341 880 385 569 578 419 234 397 708 485 601 847 108 758 503 103 414 694 794 735 059 509 671 326 329 177 465 338 412 391 856 003 420 968 070 605 896 652 214 953 420 282 507 508 205 447 599 347 588 543 298 651 133 082 200 882 973 508 651 096 860 161 307 061 515 481 851 387 590 630 802 753 643 options and still want a longer name?");
            return "sp_errors";
        }
        if (updatedArmor.getDescription().length()>255) {
            model.addAttribute("message", "This is not wikipedia. Be brief.");
            return "sp_errors";
        }
        if(updatedArmor.getDefense()>2147483647 || updatedArmor.getPrice()>2147483647 || updatedArmor.getStyle()>2147483647 || updatedArmor.getDefense()<0 || updatedArmor.getStyle()<0 || updatedArmor.getPrice()<0){
            model.addAttribute("message", "We are trying to make a balanced game, change the stats.");
            return "sp_errors";            
        }

        InputSanitizer.validateWhitelist(updatedArmor.getName());
        InputSanitizer.validateWhitelist(updatedArmor.getDescription());
        ArmorDTO editedArmor = armorService.findByIdDTO(id);
        if(editedArmor != null || !InputSanitizer.isImageValid(armorImage)){
            armorService.update(id, updatedArmorDTO);
            if(!armorImage.isEmpty()){
                armorService.replaceImage(id, armorImage.getInputStream(), armorImage.getSize());
            }           
            return "redirect:/armor/" + id;
        }else{
            model.addAttribute("message", "Could not manage, not found");
            return "sp_errors";
        }       
	}

    @GetMapping("/userList")
    public String listUsers(Model model) {
        List<UserBasicDTO> users = userService.findAll();
        model.addAttribute("users", users);
        return "userAdminList";
    }

    @GetMapping("/admin/character/{id}/view")
	public String viewCharacter(Model model, @PathVariable long id) throws IOException{
        if( userService.getLoggedUserDTO().roles().contains("ADMIN")){
            List<WeaponBasicDTO> currentInventoryWeapon = userService.UserInventoryWeaponById(id);
            List<ArmorBasicDTO> currentInventoryArmor = userService.UserInventoryArmorById(id);
            CharacterDTO characterDTO = userService.getCharacterById(id);
            // gets the character and its inventory for mustache
            model.addAttribute("character", characterDTO);
            model.addAttribute("currentWeapon", currentInventoryWeapon);
            model.addAttribute("currentArmor", currentInventoryArmor);
            // checks if the user has "logged", in the first fase is creating the character
            if (characterDTO == null) {
                model.addAttribute("message", "This user doesn't have a character");
                return "sp_errors";
            } else {
                return "admin_character_view";
            }
        }else{
            return "/logout";
        }
	}
    
    @PostMapping("/admin/user/{id}/delete")
	public String deleteUser(Model model, @PathVariable long id) throws IOException{
        if( userService.getLoggedUserDTO().roles().contains("ADMIN")){
            if(userService.getLoggedUserDTO().id() != id){
                userService.deleteUser(id);
                return "deleted_user";
            }else{
                userService.deleteUser(id);
                return "/logout";
            }
        }else{
            return "/logout";
        }
    }

    @PostMapping("/admin/user/{id}/deleteCharacter")
	public String deleteCharacter(Model model, @PathVariable long id) throws IOException, NotFoundException{
        if(userService.getLoggedUserDTO().roles().contains("ADMIN")){
            UserDTO u = userService.findById(id);
            if(u.character()!=null){
                userService.deleteCharacter(u);
            }else{
                model.addAttribute("message", "This user doesn't have a character");
                return "sp_errors";
            }
            return "redirect:/userList";
        }else{
            return "/logout";
        }  
    }
    
    @PostMapping("/admin/user/{id}/editCharacterName")
	public String editCharacter(Model model, @PathVariable long id, @RequestParam String newName) throws IOException{
        if(userService.getLoggedUserDTO().roles().contains("ADMIN")){
            long charId = userService.findById(id).character().id(); 
            characterService.editCharacterName(newName, charId);
            return "redirect:/userList";
        }else{
            return "/logout";
	    }
    }

    @PostMapping("/admin/user/{id}/editName")
	public String editUser(Model model, @PathVariable long id, @RequestParam String newName) throws IOException{
        if( userService.getLoggedUserDTO().roles().contains("ADMIN")){
            if(userService.getLoggedUserDTO().id() != id){
                userService.updateName(userService.findById(id), newName);
                return "redirect:/userList";
            }else{
                userService.updateName(userService.findById(id), newName);
                return "/logout";
            }
	    }else{
            return "/logout";
	    }
    }

    @GetMapping("/userImageAdmin/{id}")
	public ResponseEntity<Object> downloadUserImage(@PathVariable long id) throws MalformedURLException {
        if( userService.getLoggedUserDTO().roles().contains("ADMIN")){
            CharacterDTO c = userService.getCharacterById(id);
            String imageName = c.imageName();     
            return characterService.returnImage(imageName);
	    }else{ 
            throw new IllegalAccessError("Only an admin can acess this feature");
        }
    }

    @GetMapping("/downloadAdmin/{id}")
    public ResponseEntity<Resource> downloadImageAdmin(@PathVariable long id) throws IOException, IllegalAccessException {
    if( userService.getLoggedUserDTO().roles().contains("ADMIN")){
    Resource image = characterService.downloadImage(id);

    String cleanFileName = image.getFilename();
    String username = userService.findById(id).userName();

    if (cleanFileName != null && username != null) {
    cleanFileName = cleanFileName.replaceFirst("-" + Pattern.quote(username) + "(?=\\.[^.]+$)", "");
    }

    String contentType = "application/octet-stream";
    try {
        Path path = Paths.get(image.getURI());
        String detectedType = Files.probeContentType(path);
        if (detectedType != null) {
            contentType = detectedType;
        }
    } catch (Exception e) {
       
    }

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + cleanFileName + "\"")
        .contentType(MediaType.parseMediaType(contentType))
        .body(image);}
        
    else{ 
         throw new IllegalAccessError("Only an admin can acess this feature");
   }
}

//POR IMPLEMENTAR

/*@PostMapping("/unEquipWeaponAdmin/")
    public String unEquipWeapon(@RequestParam long id, Model model, @PathVariable long idUser) {
        if( userService.getLoggedUser().getRoles().contains("ADMIN")){
        CharacterDTO characterDTO = userService.getCharacterById(idUser);
        Character character = characterMapper.toDomain(characterDTO);
        WeaponDTO weaponDTO = weaponService.findByIdDTO(id);

        if (weaponDTO != null) {
            characterService.unEquipWeapon(character.getId(), id); // unequips it

            return "redirect:/character";
        } else {
            model.addAttribute("message", "Could not unEquip, doesnt exist");
            return "sp_errors";}
        }else{
             throw new IllegalAccessError("Only an admin can acess this feature");
        }


}

    @PostMapping("/unEquipArmorAdmin")
    public String unEquipArmor(@RequestParam long id, Model model, @PathVariable long idUser) {
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
*/

}