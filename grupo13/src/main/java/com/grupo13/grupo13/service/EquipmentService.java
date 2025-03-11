package com.grupo13.grupo13.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.repository.ArmorRepository;
import com.grupo13.grupo13.repository.WeaponRepository;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.User;
import com.grupo13.grupo13.model.Character;

@Service
public class EquipmentService {

	//attributes
	@Autowired
	private ArmorRepository armorRepository;
	private WeaponRepository weaponRepository;
	@Autowired
	@Lazy
	private CharacterService characterService;

	//returns all equipments in a list
	public List<Weapon> findAllWeapon() {
		return weaponRepository.findAll();
	}

	public List<Armor> findAllArmor() {
		return armorRepository.findAll();
	}

	//searches by id
	public Optional<Weapon> findWeaponById(long id) {
		return weaponRepository.findById(id);
	}
	public Optional<Armor> findArmorById(long id) {
		return armorRepository.findById(id);
	}

	//saves in repository
	public void saveWeapon(Weapon equipment) {
		weaponRepository.save(equipment);
	}

	public void saveArmor(Armor equipment) {
		armorRepository.save(equipment);
	}

	//checks if it is a weapon
	public boolean isWeapon(Weapon equipment) {
		return true;
	}

	//gets the attribute for an equipment
	public int getWeaponAttribute(Weapon equipment) {
		return equipment.getAttribute();
	}
	public int getArmorAttribute(Armor equipment) {
		return equipment.getAttribute();
	}

	//updates an equipment when edited, each attribute
	public void updateWeapon(Weapon older, Weapon newer) {
			Weapon nolder = (Weapon) older;
			Weapon nnewer = (Weapon) newer;
			nolder.setName(nnewer.getName());
			nolder.setIntimidation(nnewer.getIntimidation());
			nolder.setPicture(nnewer.getPicture());
			nolder.setAttribute(nnewer.getAttribute());
			nolder.setDescription(nnewer.getDescription());
			nolder.setnPrice(nnewer.getPrice());
			for(Character character : nolder.getCharacters()){
				character.setStrength(nnewer.getAttribute());
			}
			weaponRepository.save(nolder);
		}


		public void updateArmor(Armor older, Armor newer){
			Armor nolder = (Armor) older;
			Armor nnewer = (Armor) newer;
			nolder.setName(nnewer.getName());
			nolder.setStyle(nnewer.getStyle());
			nolder.setPicture(nnewer.getPicture());
			nolder.setAttribute(nnewer.getAttribute());
			nolder.setDescription(nnewer.getDescription());
			nolder.setnPrice(nnewer.getPrice());
			for(Character character : nolder.getCharacters()){
				character.setDefense(nnewer.getAttribute());
			}
			armorRepository.save(nolder);
		}
	

	//deletes an equipment
	public void deleteWeapon(long id) {

		if (findWeaponById(id).isPresent()) {
			Weapon equipment = findWeaponById(id).get();
			//deletes from users inventory
			for (User user : equipment.getUsers()) {
				user.getInventoryWeapon().remove(equipment);
			}
			//deletes from characters equipped
			for(Character character : equipment.getCharacters()){
				
					character.setWeapon(null);
        			character.setStrength(0);
        			character.setWeaponEquiped(false);
				
			}

			weaponRepository.deleteById(id);
		}

	}

public void deleteArmor(long id) {

	if (findArmorById(id).isPresent()) {
		Armor equipment = findArmorById(id).get();
		//deletes from users inventory
		for (User user : equipment.getUsers()) {
			user.getInventoryArmor().remove(equipment);
		}
		//deletes from characters equipped
		for(Character character : equipment.getCharacters()){
			
				character.setArmor(null);
				character.setDefense(0);
				character.setArmorEquiped(false);
			
		}

		armorRepository.deleteById(id);
	}

}
}

