package com.grupo13.grupo13.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.repository.WeaponRepository;

@Service
public class WeaponService {

    private final Character character;
    
    //attributes
    @Autowired
    private WeaponRepository weaponRepository;

    WeaponService(Character character) {
        this.character = character;
    }

    //saves in repository
    public void save(Weapon weapon){
        weaponRepository.save(weapon);
    }

    //saves the weapon's image
    public void save(Weapon weapon, MultipartFile imageFile) throws IOException{
        if(!imageFile.isEmpty()){
            weapon.setimageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
        }
        this.save(weapon);
    }

	//returns all weapons in a list
    public List<Weapon> findAll(){
        return weaponRepository.findAll();
    }

	//searches a weapon by its id
    public Optional<Weapon> findById(long id){
        return weaponRepository.findById(id);
    }

    //deletes a weapon by its id
    public void deleteById(long id){
        weaponRepository.deleteById(id);
    }
	
    //updates a weapon when edited
    public void update(Weapon oldWeapon, Weapon updatedWeapon){
        oldWeapon.setName(updatedWeapon.getName());
        oldWeapon.setDescription(updatedWeapon.getDescription());
            oldWeapon.setstrength(updatedWeapon.getstrength());
        oldWeapon.setPrice(updatedWeapon.getPrice());
        oldWeapon.setIntimidation(updatedWeapon.getIntimidation());

        oldWeapon.getCharacters().forEach(character -> character.setStrength(updatedWeapon.getstrength()));

        weaponRepository.save(oldWeapon);
    }

    //deletes a weapon
    public void delete(long id){
        if(findById(id).isPresent()){
            Weapon weapon = findById(id).get();
            
            //deletes from users inventory
            weapon.getUsers().forEach(user -> user.getInventory().remove(weapon));

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