package com.grupo13.grupo13.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.repository.EquipmentRepository;
import com.grupo13.grupo13.model.Equipment;

@Service
public class EquipmentService{
    @Autowired
	private EquipmentRepository equipmentRepository;

	public List<Equipment> findAll() {
		return equipmentRepository.findAll();
	}

	public Optional<Equipment> findById(long id) {
		return equipmentRepository.findById(id);
	}

	public void save(Equipment equipment) {
		equipmentRepository.save(equipment);		
	}

	public boolean isWeapon(Equipment equipment){

		return (equipment instanceof Weapon);

	}

	public int getAttribute(Equipment equipment){
		return equipment.getAttribute();
	}


}
