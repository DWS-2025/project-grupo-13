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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.grupo13.grupo13.repository.ArmorRepository;
import com.grupo13.grupo13.DTOs.ArmorBasicDTO;
import com.grupo13.grupo13.DTOs.ArmorDTO;
import com.grupo13.grupo13.mapper.armorMapper;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.Character;

@Service
public class ArmorService {

	//attributes
	@Autowired
	private ArmorRepository armorRepository;
    @Autowired
    private armorMapper mapper;

	//saves in repository
    public void save(ArmorDTO armorDTO){
        
        Armor armor = mapper.toDomain(armorDTO);
        armorRepository.save(armor);
    }

    //saves the armor's image
    public void save(ArmorDTO armorDTO, MultipartFile imageFile) throws IOException{
        
        Armor armor = mapper.toDomain(armorDTO);
        if(!imageFile.isEmpty()){
            armor.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
        }
        armor.setImageName("/Armor/" + armor.getId() + "/image");

        armorRepository.save(armor);
    }

    public void addCharacter(Character character, ArmorDTO armorDTO){
        Armor armor = mapper.toDomain(armorDTO);

        armor.getCharacters().add(character);
        armorRepository.save(armor);
    }

	//returns all armors in a list
    public List<ArmorBasicDTO> findAll(){
        return mapper.toDTOs(armorRepository.findAll());
    }

	//searches an armor by its id
    public ArmorDTO findById(long id){
        return mapper.toDTO(armorRepository.findById(id).get());
    }

    //deletes an armor by its id
    public void deleteById(long id){
        armorRepository.deleteById(id);
    }
	
    //updates an armor when edited
    public void update(Long oldArmorId, ArmorDTO updatedArmor){
        Optional<Armor> oldArmorOp = armorRepository.findById(oldArmorId);
        
        if (oldArmorOp.isPresent()) {
            Armor oldArmor = oldArmorOp.get();

            oldArmor.setName(updatedArmor.name());
            oldArmor.setDescription(updatedArmor.description());
            oldArmor.setDefense(updatedArmor.defense());
            oldArmor.setPrice(updatedArmor.price());
            oldArmor.setStyle(updatedArmor.style());

            oldArmor.getCharacters().forEach(character -> character.setStrength(updatedArmor.defense()));

            armorRepository.save(oldArmor);
        }
    }

      //deletes an armor
      public void delete(long id){
        if(armorRepository.findById(id).isPresent()){
            Armor armor = armorRepository.findById(id).get();
            
            //deletes from users inventory
            armor.getUsers().forEach(user -> user.getInventoryArmor().remove(armor));

            for(Character character : armor.getCharacters()){
					character.setWeapon(null);
        			character.setStrength(0);
        			character.setWeaponEquiped(false);
				}
			}
            armorRepository.deleteById(id);
        }
    
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

    public void createArmorImage(long id, URI location, InputStream inputStream, long size) {

		Armor armor = armorRepository.findById(id).orElseThrow();

		armor.setImageName(location.toString());
		armor.setImageFile(BlobProxy.generateProxy(inputStream, size));
		armorRepository.save(armor);
	}
}