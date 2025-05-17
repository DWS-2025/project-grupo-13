package com.grupo13.grupo13.service;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.grupo13.grupo13.DTOs.ArmorDTO;
import com.grupo13.grupo13.DTOs.CharacterBasicDTO;
import com.grupo13.grupo13.DTOs.CharacterDTO;
import com.grupo13.grupo13.DTOs.WeaponDTO;
import com.grupo13.grupo13.mapper.CharacterMapper;
import com.grupo13.grupo13.mapper.WeaponMapper;
import com.grupo13.grupo13.mapper.armorMapper;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.repository.ArmorRepository;
import com.grupo13.grupo13.repository.CharacterRepository;
import com.grupo13.grupo13.repository.UserRepository;
import com.grupo13.grupo13.repository.WeaponRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
public class CharacterService {

    private static final Path BACKUP_FOLDER = 
        
        Paths.get("").toAbsolutePath()
             .resolve("backups")
             .resolve("characters")
             .normalize();

    // attributes
    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private ArmorRepository armorRepository;
    @Autowired
    private WeaponRepository weaponRepository;
    @Autowired
    private WeaponService weaponService;
    @Autowired
    private ArmorService armorService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CharacterMapper mapper;
    @Autowired
    private WeaponMapper weaponMapper;
    @Autowired
    private armorMapper armorMapper;

    // returns all characters in a list
    public List<CharacterBasicDTO> findAll() {
        return mapper.toBasicDTOs(characterRepository.findAll());
    }

    // returns a specific character by its id
    public CharacterDTO findById(long id) {
        return mapper.toDTO(characterRepository.findById(id).get());
    }

    // creates a new character
    public void save(CharacterDTO characterDTO) {
        Character character = mapper.toDomain(characterDTO);
        characterRepository.save(character);
    }

    //saves the character's image
    public void save(CharacterDTO characterDTO, MultipartFile imageFile) throws IOException {
        Character character = mapper.toDomain(characterDTO);

        if (!imageFile.isEmpty()) {
            character.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
        }
        character.setImageName("/character/" + character.getId() + "/image");
        
        characterRepository.save(character);
    }



    public void saveUser(CharacterDTO characterDTO) {
        Character character = mapper.toDomain(characterDTO);
        character.setUser(userRepository.findById((long)1).get());
    }

    // for equipping armor or weapon, sets the necessary values from the equipment
    // and adds the character to the equipment
    public void equipWeapon(WeaponDTO weaponDTO, long charId) {
        Character character = characterRepository.findById(charId).get();
        character.setWeaponEquiped(true);
        character.setStrength(weaponDTO.strength());
        character.setWeapon(weaponRepository.findById(weaponDTO.id()).get());
        characterRepository.save(character);
    }

    // equips an armor to the character that recives
    public void equipArmor(ArmorDTO armorDTO, long charId) {
        Character character = characterRepository.findById(charId).get();
        character.setArmorEquiped(true);
        character.setDefense(armorDTO.defense());
        character.setArmor(armorRepository.findById(armorDTO.id()).get());
        characterRepository.save(character);
    }

    // gets the weapon equipped
    public Weapon getEquipedWeapon(CharacterDTO characterDTO) {
        Character character = mapper.toDomain(characterDTO);

        return character.getWeapon();
    }

    // gets the armor in use    
    public Armor getEquipedArmor(CharacterDTO characterDTO) {
        Character character = mapper.toDomain(characterDTO);

        return character.getArmor();
    }

    // unequips the weapon in use
    public void unEquipWeapon(long charId, long id) {
        Character character = characterRepository.findById(charId).get();
        character.setWeapon(null);
        character.setStrength(0);
        character.setWeaponEquiped(false);
        // removes the character from the list
        if (weaponRepository.findById(id).isPresent()) {
            weaponRepository.findById(id).get().getCharacters().remove(character);

            weaponService.save(weaponMapper.toDTO(weaponRepository.findById(id).get()));
        }
        characterRepository.save(character);
    }

    // unequips the armor in use
    public void unEquipArmor(long charId, long id) {
        Character character = characterRepository.findById(charId).get();
        character.setArmor(null);
        character.setDefense(0);
        character.setArmorEquiped(false);
        // removes the character from the list
        if (armorRepository.findById(id).isPresent()) {
            armorRepository.findById(id).get().getCharacters().remove(character);
            armorService.save(armorMapper.toDTO(armorRepository.findById(id).get()));
        }
        characterRepository.save(character);
    }

    /*// deletes the given character
    public void delete(Character character) {
        if (character.getArmor() != null) {
            long id = character.getArmor().getId();
            if (armorRepository.findById(id).isPresent()) {
                armorMapper.toDomain(armorService.findById(id)).getCharacters().remove(character);

            }
        }
        if (character.getWeapon() != null) {
            long id = character.getWeapon().getId();
            if (weaponService.findById(id).isPresent()) {
                weaponService.findById(id).get().getCharacters().remove(character);
            }
        }
        userService.getLoggedUser().get().setCharacter(null);
        characterRepository.deleteById(character.getId());
    }
*/
    public void deleteById(long id) {
        characterRepository.deleteById(id);
    }

    //returns the image from the id it gets
    public Resource getImageFile(long id) throws SQLException  {
        Character character = characterRepository.findById(id).orElseThrow();

		if (character.getImageFile() != null) {
			return new InputStreamResource(character.getImageFile().getBinaryStream());
		} else {
			throw new NoSuchElementException();
		}
    }

    //change the image for a new one
    public void replaceImage(long id, InputStream inputStream, long size) {

		Character character = characterRepository.findById(id).orElseThrow();

		if(character.getImageName() == null){
			throw new NoSuchElementException();
		}

		character.setImageFile(BlobProxy.generateProxy(inputStream, size));
		characterRepository.save(character);
	}

    public void createCharacterImage(long id, URI location, InputStream inputStream, long size) {

		Character character = characterRepository.findById(id).orElseThrow();

		character.setImageName(location.toString());
		character.setImageFile(BlobProxy.generateProxy(inputStream, size));
		characterRepository.save(character);
	}
	
}
