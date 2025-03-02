package com.grupo13.grupo13;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


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
        
        return "saved_weapon";
    }

    @PostMapping("/equipment/{id}/delete")
	public String deleteEquipment(Model model, @PathVariable long id) throws IOException {

        equipmentRepository.deleteById(id);

		return "deleted_weapon";
	}

   
    
}
