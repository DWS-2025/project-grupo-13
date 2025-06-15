package com.grupo13.grupo13.service;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.grupo13.grupo13.repository.ArmorRepository;
import com.grupo13.grupo13.util.InputSanitizer;
import com.grupo13.grupo13.DTOs.ArmorBasicDTO;
import com.grupo13.grupo13.DTOs.ArmorDTO;
import com.grupo13.grupo13.DTOs.CharacterDTO;
import com.grupo13.grupo13.mapper.CharacterMapper;
import com.grupo13.grupo13.mapper.armorMapper;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.model.Weapon;

@Service
public class ArmorService {

	//attributes
	@Autowired
	private ArmorRepository armorRepository;
    @Autowired
    private armorMapper mapper;
    @Autowired
    private CharacterMapper characterMapper;

	//saves in repository
    public void saveDTO(ArmorDTO armorDTO){
        InputSanitizer.validateWhitelist(armorDTO.name());
        InputSanitizer.validateWhitelist(armorDTO.description());
        Armor armor = mapper.toDomain(armorDTO);
        armorRepository.save(armor);
    }
    //for the package to other services
    void save(Armor armor){
        InputSanitizer.validateWhitelist(armor.getName());
        InputSanitizer.validateWhitelist(armor.getDescription());
        armorRepository.save(armor);
    }

    //saves the armor's image
    public void saveDTO(ArmorDTO armorDTO, MultipartFile imageFile) throws IOException{
        
        Armor armor = mapper.toDomain(armorDTO);
        if(!imageFile.isEmpty()){
            armor.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
        }
        armorRepository.save(armor);
    }

    //without dtos for other services
    void save (Armor armor, MultipartFile imageFile) throws IOException{
        
        if(!imageFile.isEmpty()){
            armor.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
        }
        armorRepository.save(armor);
    }

    public void addCharacter(CharacterDTO characterDTO, ArmorDTO armorDTO){
        Armor armor = findById(armorDTO.id());
        Character character = characterMapper.toDomain(characterDTO); ////////////////////////////////////////////////////

        armor.getCharacters().add(character);
        armorRepository.save(armor);
    }

	//returns all armors in a list
    public List<ArmorBasicDTO> findAll(){
        return mapper.toBasicDTOs(armorRepository.findAll());
    }

    //returns a page with all the armors
    public Page<ArmorDTO> findAll(Pageable pageable) {
        return armorRepository.findAll(pageable).map(armor -> mapper.toDTO(armor));
    }

    //returns a page with all the armors only with the public info
    public Page<ArmorBasicDTO> findAllBasic(Pageable pageable){
        return armorRepository.findAll(pageable).map(armor -> mapper.toBasicDTO(armor));
    }

	//searches an armor by its id
    public ArmorDTO findByIdDTO(long id){
        return mapper.toDTO(findById(id));
    }

    Armor findById (long id){
        return armorRepository.findById(id).orElseThrow();
    }

    //deletes an armor by its id
    public void deleteById(long id){
        armorRepository.deleteById(id);
    }
	
    //updates an armor when edited
    public void update(Long oldArmorId, ArmorDTO updatedArmorDTO){
        
        /*if(armorRepository.existsById(oldArmorId)){

            Armor updatedArmor = findById(updatedArmorDTO.id());
            updatedArmor.setId(oldArmorId);

            armorRepository.save(updatedArmor);

        }else{
            throw new NoSuchElementException();
        }*/

        Armor oldArmor = findById(oldArmorId);
        oldArmor.setName(updatedArmorDTO.name());
        oldArmor.setDescription(updatedArmorDTO.description());
        oldArmor.setDefense(updatedArmorDTO.defense());
        oldArmor.setPrice(updatedArmorDTO.price());
        oldArmor.setStyle(updatedArmorDTO.style());
        armorRepository.save(oldArmor);
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

    //change the image for a new one
    public void replaceImage(long id, InputStream inputStream, long size) {
		Armor armor = armorRepository.findById(id).orElseThrow();
        armor.setImageFile(BlobProxy.generateProxy(inputStream, size));
		armorRepository.save(armor);
	}

    public void createArmorImage(long id, URI location, InputStream inputStream, long size) {
		Armor armor = armorRepository.findById(id).orElseThrow();
		armor.setImageFile(BlobProxy.generateProxy(inputStream, size));
		armorRepository.save(armor);
	}

    public int maxDefense(){
        int maxDef=0;
        for(ArmorBasicDTO arm : mapper.toBasicDTOs(armorRepository.findAll())){
            maxDef = arm.defense()>maxDef?arm.defense():maxDef;
        }
        return maxDef;
    }

    public int maxPrice(){
        int maxPrice=0;
        for(ArmorBasicDTO arm : mapper.toBasicDTOs(armorRepository.findAll())){
            maxPrice = arm.price()>maxPrice?arm.price():maxPrice;
        }
        return maxPrice;
    }

    public int maxStyle(){
        int maxStyle=0;
        for(ArmorBasicDTO arm : mapper.toBasicDTOs(armorRepository.findAll())){
            maxStyle = arm.style()>maxStyle?arm.style():maxStyle;
        }
        return maxStyle;
    }

}