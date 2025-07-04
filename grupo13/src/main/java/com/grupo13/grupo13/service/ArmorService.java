package com.grupo13.grupo13.service;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
import com.grupo13.grupo13.DTOs.CharacterBasicDTO;
import com.grupo13.grupo13.DTOs.CharacterDTO;
import com.grupo13.grupo13.mapper.CharacterMapper;
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

    @Autowired
    private CharacterMapper characterMapper;

    @Lazy
    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private CharacterService characterService;

	//saves in repository
    public void saveDTO(ArmorDTO armorDTO){
        if( userService.getLoggedUser().getRoles().contains("ADMIN")){
            InputSanitizer.validateWhitelist(armorDTO.name());
            InputSanitizer.validateWhitelist(armorDTO.description());
            Armor armor = mapper.toDomain(armorDTO);
            armorRepository.save(armor);
        }
    }

    //for the package to other services
    void save(Armor armor){
        if( userService.getLoggedUser().getRoles().contains("ADMIN")){
            InputSanitizer.validateWhitelist(armor.getName());
            InputSanitizer.validateWhitelist(armor.getDescription());
            armorRepository.save(armor);
        }
    }

    //saves the armor's image
    public void saveDTO(ArmorDTO armorDTO, MultipartFile imageFile) throws IOException{
        if( userService.getLoggedUser().getRoles().contains("ADMIN")){
            Armor armor = mapper.toDomain(armorDTO);
            if(!imageFile.isEmpty()&&InputSanitizer.isImageValid(imageFile)){
                armor.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
            }
            armorRepository.save(armor);
        } 
    }

    //without dtos for other services
    void save (Armor armor, MultipartFile imageFile) throws IOException{
        if( userService.getLoggedUser().getRoles().contains("ADMIN")){
            InputSanitizer.validateWhitelist(armor.getName());
            InputSanitizer.validateWhitelist(armor.getDescription());
            if(!imageFile.isEmpty()&&InputSanitizer.isImageValid(imageFile)){
                armor.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
            }
            armorRepository.save(armor);
        }
    }

    public void addCharacter(CharacterDTO characterDTO, ArmorDTO armorDTO){
       if ((characterDTO.equals(userService.getLoggedUserDTO().character())&&userService.hasArmor(armorDTO.id()))||userService.getLoggedUser().getRoles().contains("ADMIN")){
            Armor armor = findById(armorDTO.id());
            Character character = characterMapper.toDomain(characterDTO); ////////////////////////////////////////////////////
            armor.getCharacters().add(character);
            armorRepository.save(armor);
        }
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
       if( userService.getLoggedUser().getRoles().contains("ADMIN")){
        armorRepository.deleteById(id);}
    }
	
    //updates an armor when edited
    public void update(Long oldArmorId, ArmorDTO updatedArmorDTO){
        if(userService.getLoggedUser().getRoles().contains("ADMIN")){
            InputSanitizer.validateWhitelist(updatedArmorDTO.name());
            InputSanitizer.validateWhitelist(updatedArmorDTO.description());
            Armor oldArmor = findById(oldArmorId);
            oldArmor.setName(updatedArmorDTO.name());
            oldArmor.setDescription(updatedArmorDTO.description());
            oldArmor.setDefense(updatedArmorDTO.defense());
            oldArmor.setPrice(updatedArmorDTO.price());
            oldArmor.setStyle(updatedArmorDTO.style());
            armorRepository.save(oldArmor);
            //everyone who has the armor needs to get his stats updated
            for(CharacterBasicDTO c : characterMapper.toBasicDTOs(oldArmor.getCharacters())){
                characterService.equipArmor(updatedArmorDTO, c.id());
            }
        }
    }

    //deletes an armor
    public void delete(long id){
        if(userService.getLoggedUser().getRoles().contains("ADMIN")){
            if(armorRepository.findById(id).isPresent()){
                Armor armor = armorRepository.findById(id).get();
                //deletes from users inventory
                armor.getUsers().forEach(user -> user.getInventoryArmor().remove(armor));
                for(Character character : armor.getCharacters()){
                        character.setArmor(null);
                        character.setDefense(0);
                        character.setArmorEquiped(false);
                }
            }
        armorRepository.deleteById(id);
        }   
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
        if( userService.getLoggedUser().getRoles().contains("ADMIN")){
            Armor armor = armorRepository.findById(id).orElseThrow();
            armor.setImageFile(BlobProxy.generateProxy(inputStream, size));
            armorRepository.save(armor);
        }
    }

    public void createArmorImage(long id, URI location, InputStream inputStream, long size) {
        if( userService.getLoggedUser().getRoles().contains("ADMIN")){
            Armor armor = armorRepository.findById(id).orElseThrow();
            armor.setImageFile(BlobProxy.generateProxy(inputStream, size));
            armorRepository.save(armor);
        }
    }
}