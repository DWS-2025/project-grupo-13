package com.grupo13.grupo13.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.grupo13.grupo13.repository.ArmorRepository;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.User;
import com.grupo13.grupo13.model.Character;

@Service
public class ArmorService {

	//attributes
	@Autowired
	private ArmorRepository armorRepository;
	@Autowired
	@Lazy
	private CharacterService characterService;

	//returns all equipments in a list
	public List<Armor> findAll() {
		return armorRepository.findAll();
	}

	//searches by id
	public Optional<Armor> findById(long id) {
		return armorRepository.findById(id);
	}

	//saves in repository
	public void save(Armor armor) {
		armorRepository.save(armor);
	}

	//gets the attribute for an equipment
	public int getStylee(Armor armor) {
		return armor.getStyle();
	}

	//updates an equipment when edited, each attribute
	public void update(Armor older, Armor newer) {
		Armor nolder = (Armor) older;
		Armor nnewer = (Armor) newer;
		nolder.setName(nnewer.getName());
		nolder.setStyle(nnewer.getStyle());
		nolder.setPicture(nnewer.getPicture());
		nolder.setStyle(nnewer.getStyle());
		nolder.setDescription(nnewer.getDescription());
		nolder.setPrice(nnewer.getPrice());
		for(Character character : nolder.getCharacters()){
			character.setDefense(nnewer.getStyle());
		}
		armorRepository.save(nolder);
	}

	//deletes an equipment
	public void delete(long id) {

		if (findById(id).isPresent()) {
			Armor armor = findById(id).get();
			//deletes from users inventory
			for (User user : armor.getUsers()) {
				user.getInventory().remove(armor);
			}
			//deletes from characters equipped
			for(Character character : armor.getCharacters()){	
				character.setArmor(null);
       			character.setDefense(0);
       			character.setArmorEquiped(false);
				}
		}

		armorRepository.deleteById(id);
	}

	
}