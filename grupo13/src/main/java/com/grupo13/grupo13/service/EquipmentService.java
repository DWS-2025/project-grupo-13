package com.grupo13.grupo13.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.repository.EquipmentRepository;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.Equipment;

@Service
public class EquipmentService {

	//attributes
	@Autowired
	private EquipmentRepository equipmentRepository;

	//returns all equipments in a list
	public List<Equipment> findAll() {
		return equipmentRepository.findAll();
	}

	//searches by id
	public Optional<Equipment> findById(long id) {
		return equipmentRepository.findById(id);
	}

	//saves in repository
	public void save(Equipment equipment) {
		equipmentRepository.save(equipment);
	}

	//checks if it is a weapon
	public boolean isWeapon(Equipment equipment) {
		return (equipment instanceof Weapon);
	}

	//gets the attribute for an equipment
	public int getAttribute(Equipment equipment) {
		return equipment.getAttribute();
	}

	//updates an equipment when edited, each attribute
	public void update(Equipment older, Equipment newer) {
		if (older instanceof Weapon & newer instanceof Weapon) {
			Weapon nolder = (Weapon) older;
			Weapon nnewer = (Weapon) newer;
			nolder.setIntimidation(nnewer.getIntimidation());
			nolder.setPicture(nnewer.getPicture());
			nolder.setAttribute(nnewer.getAttribute());
			nolder.setDescription(nnewer.getDescription());
			nolder.setnPrice(nnewer.getPrice());
			equipmentRepository.save(nolder);
		} else if (older instanceof Armor & newer instanceof Armor) {
			Armor nolder = (Armor) older;
			Armor nnewer = (Armor) newer;
			nolder.setStyle(nnewer.getStyle());
			nolder.setPicture(nnewer.getPicture());
			nolder.setAttribute(nnewer.getAttribute());
			nolder.setDescription(nnewer.getDescription());
			nolder.setnPrice(nnewer.getPrice());
			equipmentRepository.save(nolder);
		}
	}

}
