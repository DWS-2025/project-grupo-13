package com.grupo13.grupo13.controller;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.grupo13.grupo13.DTOs.ArmorDTO;
import com.grupo13.grupo13.DTOs.UserBasicDTO;
import com.grupo13.grupo13.DTOs.WeaponDTO;
import com.grupo13.grupo13.mapper.WeaponMapper;
import com.grupo13.grupo13.mapper.armorMapper;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.service.ArmorService;
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

    //private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "src/main/resources/imp_imgs");
      
    @GetMapping("weapon/{id}")
    public String showWeapon(Model model, @PathVariable long id){
        WeaponDTO weapon = weaponService.findById(id);

        if (weapon != null) {
            model.addAttribute("weapon", weapon);
            return "show_weapon";
        }else{
            return "error";
        }
    }
    
    @GetMapping("/armor/{id}")
    public String showArmor(Model model, @PathVariable long id) {
        ArmorDTO armor = armorService.findById(id);
        
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
<<<<<<< HEAD
       InputSanitizer.validateWhitelist(name);
       InputSanitizer.validateWhitelist(description);
       InputSanitizer.validateWhitelist(imageName);
=======

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

        name = InputSanitizer.whitelistSanitize(name);
        description = InputSanitizer.whitelistSanitize(description);
>>>>>>> main
        
        Weapon weapon = new Weapon(name, description, intimidation, strength, price);
        WeaponDTO weaponDTO = weaponMapper.toDTO(weapon);
        
        weaponService.save(weaponDTO, weaponImage);

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
<<<<<<< HEAD
         InputSanitizer.validateWhitelist(name);
         InputSanitizer.validateWhitelist(description);
         InputSanitizer.validateWhitelist(imageName);
=======

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

        name = InputSanitizer.whitelistSanitize(name);
        description = InputSanitizer.whitelistSanitize(description);
>>>>>>> main
        
        Armor armor = new Armor(name, description, style, defense, price);
        ArmorDTO armorDTO = armorMapper.toDTO(armor);
        
        armorService.save(armorDTO, armorImage);

        return "saved_armor";
    }

    @PostMapping("/weapon/{id}/delete")
    public String deleteWeapon(Model model, @PathVariable long id) throws IOException{
        weaponService.deleteById(id);
        
        return "deleted_weapon";
    }

    @PostMapping("/armor/{id}/delete")
    public String deleteArmor(Model model, @PathVariable long id) throws IOException{
        armorService.deleteById(id);
        
        return "deleted_armor";
    }
    
    @GetMapping("/weapon/{id}/edit")
    public String editWeapon(Model model, @PathVariable long id) {
        WeaponDTO weapon = weaponService.findById(id);

        if(weapon != null){
            model.addAttribute("weapon", weapon);
            return "edit_weapon";
        }else{
            return "error";
        }
    }

    @GetMapping("/armor/{id}/edit")
    public String editArmor(Model model, @PathVariable long id) {
        ArmorDTO armor = armorService.findById(id);

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

        updatedWeapon.setName(InputSanitizer.whitelistSanitize(updatedWeapon.getName()));
        updatedWeapon.setDescription(InputSanitizer.whitelistSanitize(updatedWeapon.getDescription()));

        WeaponDTO editedWeapon = weaponService.findById(id);
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
        updatedArmor.setName(InputSanitizer.whitelistSanitize(updatedArmor.getName()));
        updatedArmor.setDescription(InputSanitizer.whitelistSanitize(updatedArmor.getDescription()));
        ArmorDTO editedArmor = armorService.findById(id);
        if(editedArmor != null){
            if(!armorImage.isEmpty()){
                armorService.replaceImage(id, armorImage.getInputStream(), armorImage.getSize());
            }

            armorService.update(id, updatedArmorDTO);
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
    
    @PostMapping("/admin/user/{id}/delete")
	public String deleteUser(Model model, @PathVariable long id) throws IOException{
        if(userService.getLoggedUserDTO().id() != id){
            userService.deleteUser(id);
            return "deleted_user";
        }else{
            userService.deleteUser(id);
            return "/logout";
        }
	}
}
