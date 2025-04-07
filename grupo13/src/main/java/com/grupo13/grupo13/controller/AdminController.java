package com.grupo13.grupo13.controller;
import java.io.IOException;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.service.ArmorService;
import com.grupo13.grupo13.service.CharacterService;
import com.grupo13.grupo13.service.UserService;
import com.grupo13.grupo13.service.WeaponService;

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
    private CharacterService characterService;

    //private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "src/main/resources/imp_imgs");
      
    @GetMapping("weapon/{id}")
    public String showWeapon(Model model, @PathVariable long id){
        Optional<Weapon> weapon = weaponService.findById(id);

        if (weapon.isPresent()) {
            
            model.addAttribute("weapon", weapon.get());

            return "show_weapon";
        }else{
            return "error";
        }

    }
    
    @GetMapping("/armor/{id}")
    public String showArmor(Model model, @PathVariable long id) {
        
        Optional<Armor> armor = armorService.findById(id);
        
        if(armor.isPresent()){

            model.addAttribute("armor", armor.get());
            return "show_armor";
        }else{
            return "error";
        }
        
    }

    @GetMapping("/equipment_manager")
    public String iterationObj(Model model) {

        //gets all equipments and characters
        model.addAttribute("weapon", weaponService.findAll());
        model.addAttribute("armor", armorService.findAll());
        model.addAttribute("character", userService.getCharacter());

        return "equipment_manager";
    }

    @PostMapping("/weapon/new")
    public String newWeapon(Model model, Weapon weapon, @RequestParam MultipartFile weaponImage) throws IOException {
        if (weapon.getName().isBlank()||weapon.getDescription().isBlank()|| weaponImage.isEmpty()) {
            model.addAttribute("message", "Some or all parameters were left blank");
            return "sp_errors";
        }

        weaponService.save(weapon);
        weaponService.save(weapon, weaponImage);

        return "saved_weapon";
    }

    @PostMapping("/armor/new")
    public String newArmor(Model model, Armor armor, @RequestParam MultipartFile armorImage) throws IOException {
        if (armor.getName().isBlank()||armor.getDescription().isBlank()|| armorImage.isEmpty()) {
            model.addAttribute("message", "Some or all parameters were left blank");
            return "sp_errors";
        }
        
        armorService.save(armor);
        armorService.save(armor, armorImage);

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

        Optional<Weapon> weapon = weaponService.findById(id);

        if(weapon.isPresent()){
            model.addAttribute("weapon", weapon.get());
            return "edit_weapon";
        }else{
            return "error";
        }
    }

    @GetMapping("/armor/{id}/edit")
    public String editArmor(Model model, @PathVariable long id) {

        Optional<Armor> armor = armorService.findById(id);

        if(armor.isPresent()){
            model.addAttribute("armor", armor.get());
            return "edit_armor";
        }else{
            model.addAttribute("message", "Could not manage, not found");
            return "sp_errors";
        }
    }
    
    @GetMapping("/delete_Character")
    public String getMethodName(Model model) {
        characterService.delete(userService.getCharacter());
        return "deleted_character";
    }
    
    @PostMapping("/weapon/{id}/edit")
	public String updateWeapon(Model model, @PathVariable long id, Weapon updatedWeapon, @RequestParam MultipartFile weaponImage) throws IOException{
        
        if (updatedWeapon.getName().isBlank() || updatedWeapon.getDescription().isBlank() /*|| picture.isBlank()*/ ) {
            model.addAttribute("message", "Some or all parameters were left blank");
            return "sp_errors";
        }

        Optional<Weapon> editedWeapon = weaponService.findById(id);

        if(editedWeapon.isPresent()){
            weaponService.update(id, updatedWeapon);

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
	public String updateArmor(Model model, @PathVariable long id, Armor updatedArmor, @RequestParam MultipartFile armorImage) throws IOException{
        
        if (updatedArmor.getName().isBlank() || updatedArmor.getDescription().isBlank() /*|| picture.isBlank()*/ ) {
            model.addAttribute("message", "Some or all parameters were left blank");
            return "sp_errors";
        }

        Optional<Armor> editedArmor = armorService.findById(id);

        if(editedArmor.isPresent()){
            if(!armorImage.isEmpty()){
                weaponService.replaceImage(id, armorImage.getInputStream(), armorImage.getSize());
            }

            armorService.update(id, updatedArmor);
            return "redirect:/armor/" + id;
        }else{
            model.addAttribute("message", "Could not manage, not found");
            return "sp_errors";
        }
	}
    
}
