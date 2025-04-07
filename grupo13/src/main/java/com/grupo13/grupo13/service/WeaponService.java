package com.grupo13.grupo13.service;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile; 
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.repository.WeaponRepository;

@Service
public class WeaponService {
    
    //attributes
    @Autowired
    private WeaponRepository weaponRepository;

    //saves in repository
    public void save(Weapon weapon){
        weaponRepository.save(weapon);
    }

    //saves the weapon's image
    public void save(Weapon weapon, MultipartFile imageFile) throws IOException{
        if(!imageFile.isEmpty()){
            weapon.setimageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
        }
        weapon.setImageName("/Weapon/" + weapon.getId() + "/image");

        this.save(weapon);
    }

    public void addCharacter(Character character, Weapon weapon){
        weapon.getCharacters().add(character);
        weaponRepository.save(weapon);
    }
    
	//returns all weapons in a list
    public List<Weapon> findAll() {
        return weaponRepository.findAll();
    }

    public Page<Weapon> findAll(Pageable pageable) {
        return weaponRepository.findAll(pageable);
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
    public void update(Long oldWeaponid, Weapon updatedWeapon){
        Optional<Weapon> oldWeaponOp = findById(oldWeaponid);
        
        if (oldWeaponOp.isPresent()) {
            Weapon oldWeapon = oldWeaponOp.get();

            oldWeapon.setName(updatedWeapon.getName());
            oldWeapon.setDescription(updatedWeapon.getDescription());
            oldWeapon.setstrength(updatedWeapon.getstrength());
            oldWeapon.setPrice(updatedWeapon.getPrice());
            oldWeapon.setIntimidation(updatedWeapon.getIntimidation());

            oldWeapon.getCharacters().forEach(character -> character.setStrength(updatedWeapon.getstrength()));

            weaponRepository.save(oldWeapon);
        }
    }

    //deletes a weapon
    public void delete(long id){
        if(findById(id).isPresent()){
            Weapon weapon = findById(id).get();
            
            //deletes from users inventory
            weapon.getUsers().forEach(user -> user.getInventoryWeapon().remove(weapon));

            for(Character character : weapon.getCharacters()){
                character.setWeapon(null);
        		character.setStrength(0);
        		character.setWeaponEquiped(false);
            }
            weaponRepository.deleteById(id);
        }
    }

 

    public Resource getImageFile(long id) throws SQLException  {
        Weapon weapon = weaponRepository.findById(id).orElseThrow();

		if (weapon.getimageFile() != null) {
			return new InputStreamResource(weapon.getimageFile().getBinaryStream());
		} else {
			throw new NoSuchElementException();
		}
    }

    public void replaceImage(long id, InputStream inputStream, long size) {

		Weapon weapon = weaponRepository.findById(id).orElseThrow();

		if(weapon.getImageName() == null){
			throw new NoSuchElementException();
		}

		weapon.setimageFile(BlobProxy.generateProxy(inputStream, size));

		weaponRepository.save(weapon);
	}

    public void createWeaponImage(long id, URI location, InputStream inputStream, long size) {

		Weapon weapon = weaponRepository.findById(id).orElseThrow();

		weapon.setImageName(location.toString());
		weapon.setimageFile(BlobProxy.generateProxy(inputStream, size));
		weaponRepository.save(weapon);
	}
}