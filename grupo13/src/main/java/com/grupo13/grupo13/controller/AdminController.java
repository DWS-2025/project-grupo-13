package com.grupo13.grupo13.controller;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths; 
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.Equipment;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.service.CharacterService;
import com.grupo13.grupo13.service.EquipmentService;
import com.grupo13.grupo13.service.UserService;

@Controller
public class AdminController {

    //attributes
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private CharacterService characterService;
    private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "src/main/resources/imp_imgs");

    @GetMapping("/equipment/{id}")
    public String showEquipment(Model model, @PathVariable long id) {

        Optional<Equipment> equipment = equipmentService.findById(id);
        
        //detects if it's a weapon or an armor
        if (equipment.isPresent() & equipmentService.isWeapon(equipment.get())) {
            model.addAttribute("equipment", equipment.get());
            return "show_weapon";
        } else {
            model.addAttribute("equipment", equipment.get());
            return "show_armor";
        }//else{return "equipment not found"}
    }

    @GetMapping("/equipment_manager")
    public String iterationObj(Model model) {

        //gets all equipments and characters
        model.addAttribute("equipment", equipmentService.findAll());
        model.addAttribute("character", userService.getCharacter());

        return "equipment_manager";
    }

    @PostMapping("/weapon/new")
    public String newEquipment(Model model, Weapon weapon, @RequestParam MultipartFile weaponImage, @RequestParam String image) throws IOException {
        if (weapon.getName().isBlank()||weapon.getDescription().isBlank()|| weaponImage.isEmpty() || image.isBlank()) {
            model.addAttribute("message", "Some or all parameters were left blank");
            return "sp_errors";
        }
        //recives the information to create a new weapon
        String defenitiveImage;
        image += ".jpg";
        defenitiveImage = "imp_imgs/" + image;

        weapon.setPicture(defenitiveImage);

        equipmentService.save(weapon);

        Path imagePath = IMAGES_FOLDER.resolve(image);
        weaponImage.transferTo(imagePath);

        return "saved_weapon";
    }

    @PostMapping("/armor/new")
    public String newEquipment(Model model, Armor armor, @RequestParam MultipartFile armorImage, @RequestParam String picture) throws IOException {
        if (armor.getName().isBlank()||armor.getDescription().isBlank()|| armorImage.isEmpty() || picture.isBlank()) {
            model.addAttribute("message", "Some or all parameters were left blank");
            return "sp_errors";
        }
        //recives the information to create a new armor
        String defenitivePicture;
        picture += ".jpg";
        defenitivePicture = "imp_imgs/" + picture;
        armor.setPicture(defenitivePicture);
        equipmentService.save(armor);

        Path imagePath = IMAGES_FOLDER.resolve(picture);
        armorImage.transferTo(imagePath);       
        return "saved_armor";
    }

    @PostMapping("/equipment/{id}/delete")
    public String deleteEquipment(Model model, @PathVariable long id) throws IOException {
        equipmentService.delete(id);
        return "deleted_equipment";
    }

    @GetMapping("/equipment/{id}/edit")
	public String editEquipment(Model model, @PathVariable long id) {

		Optional<Equipment> equipment = equipmentService.findById(id);
        
		if (equipment.isPresent()) { //if the equipment exists, it leads to edit weapon/armor
            if (equipmentService.isWeapon(equipment.get())) {
                model.addAttribute("equipment", equipment.get());
                return "edit_weapon";
            } else { //if not, it sends an error message
                model.addAttribute("equipment", equipment.get());
                return "edit_armor";
            }
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
    
    @PostMapping("/equipment/{id}/edit")
	public String updateEquipment(Model model, @PathVariable long id, @RequestParam String name, @RequestParam String description, @RequestParam int intimidation, @RequestParam int style, @RequestParam int attribute, @RequestParam int price, @RequestParam String picture){

        if (name.isBlank()||description.isBlank()|| picture.isBlank() ) {
            model.addAttribute("message", "Some or all parameters were left blank");
            return "sp_errors";
        }

		Optional<Equipment> editedEquipment = equipmentService.findById(id);

		if (editedEquipment.isPresent()) { //if the equipment exists, it reates a weapon/armor with the new information
			Equipment oldEquipment = editedEquipment.get();
            if(equipmentService.isWeapon(oldEquipment)){
                Equipment updatedEquipment = new Weapon(name, intimidation, attribute, picture, description, price);
                equipmentService.update(oldEquipment, updatedEquipment);
			    return "redirect:/equipment/" + id;
            }else{
                Equipment updatedEquipment = new Armor(name, style, attribute, picture, description, price);
                equipmentService.update(oldEquipment, updatedEquipment);
			    return "redirect:/equipment/" + id;
            }
			
		}else{ //if not, it sends an error message
            model.addAttribute("message", "Could not manage, not found");
            return "sp_errors";
        }
	}
    
}
