package com.grupo13.grupo13;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping("/show_equipment")
	public String iterationObj(Model model) {

        model.addAttribute("equipment", equipmentService.findAll());

		return "show_equipment";
	}
}
