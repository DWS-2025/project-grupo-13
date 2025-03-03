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
import com.grupo13.grupo13.repository.EquipmentRepository;
import com.grupo13.grupo13.service.EquipmentService;

@Controller
public class AdminController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentRepository equipmentRepository;

    private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/imp_imgs");

    @GetMapping("/equipment/{id}")
    public String showEquipment(Model model, @PathVariable long id) {

        Optional<Equipment> equipment = equipmentService.findById(id);

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

        model.addAttribute("equipment", equipmentService.findAll());

        return "equipment_manager";
    }

    @PostMapping("/weapon/new")
    public String newEquipment(Model model, Weapon weapon, @RequestParam MultipartFile weaponImage, @RequestParam String image) throws IOException {

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

        equipmentRepository.deleteById(id);

        return "deleted_equipment";
    }

    @GetMapping("/equipment/{id}/edit")
	public String editEquipment(Model model, @PathVariable long id) {

		Optional<Equipment> equipment = equipmentService.findById(id);
        
		if (equipment.isPresent()) {
            if (equipmentService.isWeapon(equipment.get())) {
                model.addAttribute("equipment", equipment.get());
                return "edit_weapon";
            } else {
                model.addAttribute("equipment", equipment.get());
                return "show_armor";
            }
		}else{
            return "equipment_manager"; //not_found
        }
	}   

	@PostMapping("/equipment/{id}/edit")
	public String updateEquipment(Model model, @PathVariable long id, Equipment updatedEquipment) {

        
		Optional<Equipment> editedEquipment = equipmentService.findById(id);

		if (editedEquipment.isPresent()) {
			Equipment oldEquipment = editedEquipment.get();
			equipmentService.update(oldEquipment, updatedEquipment);
			return "redirect:/equipment/" + id;
		}else{
            return "equipment_manager"; //not_found
        }
	}
    
}
