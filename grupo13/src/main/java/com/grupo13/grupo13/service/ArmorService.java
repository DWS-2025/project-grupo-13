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
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile; 
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.repository.ArmorRepository;

@Service
public class ArmorService {

	//attributes
	@Autowired
	private ArmorRepository armorRepository;

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

    public void addCharacter(Character character, Armor armor){
        armor.getCharacters().add(character);
        armorRepository.save(armor);
    }

	//returns all armors in a list
    public List<Armor> findAll() {
        return armorRepository.findAll();
    }

    public Page<Armor> findAll(Pageable pageable) {
        return armorRepository.findAll(pageable);
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
    public void update(Armor oldArmor, Armor updatedArmor, MultipartFile imageFile) throws IOException{
        if(!imageFile.isEmpty()){
            oldArmor.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
        }

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
            armor.getUsers().forEach(user -> user.getInventoryArmor().remove(armor));

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
    
    public Resource getImageFile(long id) throws SQLException  {
        Armor armor = armorRepository.findById(id).orElseThrow();

		if (armor.getimageFile() != null) {
			return new InputStreamResource(armor.getimageFile().getBinaryStream());
		} else {
			throw new NoSuchElementException();
		}
    }

    public void replaceImage(long id, InputStream inputStream, long size) {

		Armor armor = armorRepository.findById(id).orElseThrow();

		if(armor.getImageName() == null){
			throw new NoSuchElementException();
		}

		armor.setImageFile(BlobProxy.generateProxy(inputStream, size));

		armorRepository.save(armor);
	}

    public void createPostImage(long id, URI location, InputStream inputStream, long size) {

		Armor armor = armorRepository.findById(id).orElseThrow();

		armor.setImageName(location.toString());
		armor.setImageFile(BlobProxy.generateProxy(inputStream, size));
		armorRepository.save(armor);
	}
}