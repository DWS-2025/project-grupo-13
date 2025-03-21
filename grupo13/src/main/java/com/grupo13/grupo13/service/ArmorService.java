package com.grupo13.grupo13.service;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.grupo13.grupo13.repository.ArmorRepository;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.User;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.model.Character;

@Service
public class ArmorService {

	private final Character character;

	//attributes
	@Autowired
	private ArmorRepository armorRepository;

	ArmorService(Character character) {
        this.character = character;
    }

	//saves in repository
    public void save(Armor armor){
        armorRepository.save(armor);
    }

    //saves the armor's image
    public void save(Armor armor, MultipartFile imageFile) throws IOException{
        if(!imageFile.isEmpty()){
            armor.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
        }
        this.save(armor);
    }

	//returns all armors in a list
    public List<Armor> findAll(){
        return armorRepository.findAll();
    }

	//searches an armor by its id
    public Optional<Armor> findById(long id){
        return armorRepository.findById(id);
    }

    //deletes an armor by its id
    public void deleteById(long id){
        armorRepository.deleteById(id);
    }
	
    //updates an armor when edited
    public void update(Armor oldArmor, Armor updatedArmor){
        oldArmor.setName(updatedArmor.getName());
        oldArmor.setDescription(updatedArmor.getDescription());
        oldArmor.setDefense(updatedArmor.getDefense());
        oldArmor.setPrice(updatedArmor.getPrice());
        oldArmor.setStyle(updatedArmor.getStyle());

        oldArmor.getCharacters().forEach(character -> character.setStrength(updatedArmor.getDefense()));

        armorRepository.save(oldArmor);
    }

    //deletes an armor
    public void delete(long id){
        if(findById(id).isPresent()){
            Armor armor = findById(id).get();
            
            //deletes from users inventory
            armor.getUsers().forEach(user -> user.getInventory().remove(armor));

            //falta el eliminar del personaje
        }
    }
    
    /*	
	//deletes an equipment
	public void delete(long id) {

		if (findById(id).isPresent()) {
			Equipment equipment = findById(id).get();
			//deletes from users inventory
			for (User user : equipment.getUsers()) {            for each
				user.getInventory().remove(equipment);
			}
			//deletes from characters equipped
			for(Character character : equipment.getCharacters()){
				if (isWeapon(equipment)) {
					character.setWeapon(null);
        			character.setStrength(0);
        			character.setWeaponEquiped(false);
				}else{
					character.setArmor(null);
        			character.setDefense(0);
        			character.setArmorEquiped(false);
				}
			}
			equipmentRepository.deleteById(id);
		}

	}

    */	
}