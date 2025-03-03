package com.grupo13.grupo13.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/equipment/{id}")
    public String showEquipment(Model model, @PathVariable long id) {

        Optional<Equipment> equipment = equipmentService.findById(id);

        if(equipment.isPresent() && equipmentService.isWeapon(equipment.get())){
            model.addAttribute("equipment", equipment.get());
            return "show_weapon";
        }else{
            model.addAttribute("equipment", equipment.get());
            return "show_armor";
        }
	}

    @GetMapping("/equipment_manager")
	public String iterationObj(Model model) {

        model.addAttribute("equipment", equipmentService.findAll());

		return "equipment_manager";
	}

    @PostMapping("/weapon/new")
    public String newEquipment(Model model, Weapon weapon) {

        equipmentService.save(weapon);
        
        return "saved_weapon";
    }

    @PostMapping("/armor/new")
    public String newEquipment(Model model, Armor armor) {

        equipmentService.save(armor);
        
        return "saved_armor";
    }

    @PostMapping("/equipment/{id}/delete")
	public String deleteEquipment(Model model, @PathVariable long id) throws IOException {

        equipmentRepository.deleteById(id);

		return "deleted_equipment";
	}

   
    
}
