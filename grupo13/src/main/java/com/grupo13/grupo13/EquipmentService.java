package com.grupo13.grupo13;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
