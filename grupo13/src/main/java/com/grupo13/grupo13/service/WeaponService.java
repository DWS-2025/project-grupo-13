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
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile; 
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.DTOs.CharacterDTO;
import com.grupo13.grupo13.DTOs.WeaponBasicDTO;
import com.grupo13.grupo13.DTOs.WeaponDTO;
import com.grupo13.grupo13.mapper.CharacterMapper;
import com.grupo13.grupo13.mapper.WeaponMapper;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.repository.WeaponRepository;
import com.grupo13.grupo13.util.InputSanitizer;

@Service
public class WeaponService {
    
    //attributes
    @Autowired
    private WeaponRepository weaponRepository;
    @Autowired
    private WeaponMapper mapper;
    @Autowired
    private CharacterMapper characterMapper;
    @Lazy
    @Autowired
    private UserService userService;

    //saves in repository
    public void saveDTO(WeaponDTO weaponDTO){
        if( userService.getLoggedUser().getRoles().contains("ADMIN")){
        InputSanitizer.validateWhitelist(weaponDTO.name());
        InputSanitizer.validateWhitelist(weaponDTO.description());
        Weapon weapon = mapper.toDomain(weaponDTO);
        weaponRepository.save(weapon);
    }
    }

    //without dtos
    void save(Weapon weapon){
        if( userService.getLoggedUser().getRoles().contains("ADMIN")){
        InputSanitizer.validateWhitelist(weapon.getName());
        InputSanitizer.validateWhitelist(weapon.getDescription());
        weaponRepository.save(weapon);
        }
    }    

    //saves the weapon's image
    public void saveDTO(WeaponDTO weaponDTO, MultipartFile imageFile) throws IOException{
        if( userService.getLoggedUser().getRoles().contains("ADMIN")){
        Weapon weapon = mapper.toDomain(weaponDTO);
        if(!imageFile.isEmpty()){
            weapon.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
        }
        weaponRepository.save(weapon);
    }
}

    public void save(Weapon weapon, MultipartFile imageFile) throws IOException{
if( userService.getLoggedUser().getRoles().contains("ADMIN")){
      InputSanitizer.validateWhitelist(weapon.getName());
        InputSanitizer.validateWhitelist(weapon.getDescription());
        
        if(!imageFile.isEmpty()){
            weapon.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
        }
        weaponRepository.save(weapon);
    }

}

    public void addCharacter(CharacterDTO characterDTO, WeaponDTO weaponDTO){
         if (characterDTO.equals(userService.getLoggedUserDTO().character())||userService.getLoggedUser().getRoles().contains("ADMIN")){
        Weapon weapon = findById(weaponDTO.id());
        Character character = characterMapper.toDomain(characterDTO); ////////////////////////////////////////////////////
        weapon.getCharacters().add(character);
        weaponRepository.save(weapon);
    }
    }
    
	//returns all weapons in a list
    public List<WeaponBasicDTO> findAll(){
        return mapper.toBasicDTOs(weaponRepository.findAll());
    }

    //returns a page with all the weapons
    public Page<WeaponDTO> findAll(Pageable pageable) {
        return weaponRepository.findAll(pageable).map(weapon -> mapper.toDTO(weapon));
    }

    //returns a page with all the weapons only with the public info
    public Page<WeaponBasicDTO> findAllBasic(Pageable pageable) {
        return weaponRepository.findAll(pageable).map(weapon -> mapper.toBasicDTO(weapon));
    }
    
	//searches a weapon by its id
    public WeaponDTO findByIdDTO(long id){
        return mapper.toDTO(findById(id));
    }

    Weapon findById (long id){
        return weaponRepository.findById(id).orElseThrow();
    }

    //deletes a weapon by its id
    public void deleteById(long id){
         if( userService.getLoggedUser().getRoles().contains("ADMIN")){
        weaponRepository.deleteById(id);
    }
    }
	
    //updates a weapon when edited
    public void update(Long oldWeaponid, WeaponDTO updatedWeaponDTO){
        
        /*if (weaponRepository.existsById(oldWeaponid)) {
            Weapon updatedWeapon = findById(updatedWeaponDTO.id());
            updatedWeapon.setId(oldWeaponid);

            weaponRepository.save(updatedWeapon);
        }else{
            throw new NoSuchElementException();
        } */
 if( userService.getLoggedUser().getRoles().contains("ADMIN")){
        Weapon oldWeapon = findById(oldWeaponid);
        oldWeapon.setName(updatedWeaponDTO.name());
        oldWeapon.setDescription(updatedWeaponDTO.description());
        oldWeapon.setstrength(updatedWeaponDTO.strength());
        oldWeapon.setPrice(updatedWeaponDTO.price());
        oldWeapon.setIntimidation(updatedWeaponDTO.intimidation());
        weaponRepository.save(oldWeapon);
    }

    }

    //deletes a weapon
    public void delete(long id){
        if( userService.getLoggedUser().getRoles().contains("ADMIN")){
        if(weaponRepository.findById(id).isPresent()){
            Weapon weapon = weaponRepository.findById(id).get();
            
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
    }
    
    //returns the image of the id given
    public Resource getImageFile(long id) throws SQLException  {
        Weapon weapon = weaponRepository.findById(id).orElseThrow();

		if (weapon.getimageFile() != null) {
			return new InputStreamResource(weapon.getimageFile().getBinaryStream());
		} else {
			throw new NoSuchElementException();
		}
    }

    //change the image of the id given
    public void replaceImage(long id, InputStream inputStream, long size) {
         if( userService.getLoggedUser().getRoles().contains("ADMIN")){
		Weapon weapon = weaponRepository.findById(id).orElseThrow();
		weapon.setimageFile(BlobProxy.generateProxy(inputStream, size));
		weaponRepository.save(weapon);
        }
	}

    public void createWeaponImage(long id, URI location, InputStream inputStream, long size) {
if( userService.getLoggedUser().getRoles().contains("ADMIN")){
		Weapon weapon = weaponRepository.findById(id).orElseThrow();
		weapon.setimageFile(BlobProxy.generateProxy(inputStream, size));
		weaponRepository.save(weapon);
	}
}
}