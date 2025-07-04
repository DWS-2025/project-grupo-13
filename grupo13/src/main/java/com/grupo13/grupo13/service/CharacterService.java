package com.grupo13.grupo13.service;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import java.io.InputStream;
import java.net.MalformedURLException;
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
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import com.grupo13.grupo13.DTOs.ArmorDTO;
import com.grupo13.grupo13.DTOs.CharacterBasicDTO;
import com.grupo13.grupo13.DTOs.CharacterDTO;
import com.grupo13.grupo13.DTOs.WeaponDTO;
import com.grupo13.grupo13.mapper.CharacterMapper;
import com.grupo13.grupo13.mapper.UserMapper;
import com.grupo13.grupo13.mapper.WeaponMapper;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.model.User;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.repository.ArmorRepository;
import com.grupo13.grupo13.repository.CharacterRepository;
import com.grupo13.grupo13.repository.WeaponRepository;
import com.grupo13.grupo13.util.InputSanitizer;
import java.nio.file.Path;

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
    private UserService userService;

    @Autowired
    private CharacterMapper mapper;

    @Autowired
    private WeaponMapper weaponMapper;

    @Autowired
    private UserMapper userMapper;

    // returns all characters in a list
    public List<CharacterBasicDTO> findAll() {
        return mapper.toBasicDTOs(characterRepository.findAll());
    }

    // returns a specific character by its id
    public CharacterDTO findByIdDTO(long id) {
        return mapper.toDTO(findById(id));
    }

    Character findById(long id) {
        return characterRepository.findById(id).orElseThrow();
    }

    // creates a new character
    public CharacterDTO save(CharacterDTO characterDTO) {
        InputSanitizer.validateWhitelist(characterDTO.name());
        InputSanitizer.sanitizeRichText(characterDTO.description());
        Character character = mapper.toDomain(characterDTO);
        Character savedCharacter = characterRepository.save(character);
        return mapper.toDTO(savedCharacter);
    }

    //saves the character's image
    public void save(CharacterDTO characterDTO, MultipartFile imageFile, String imageName) throws IOException {
        Character character = mapper.toDomain(characterDTO);
        if (!imageFile.isEmpty()&& InputSanitizer.isImageValid(imageFile)) {
            character.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
        }
        backupImage(imageFile, imageName);
        characterRepository.save(character);
    }

    public void checkImage(MultipartFile imageFile, String imageName){
        if (imageName.contains("..") || imageName.contains("/") || imageName.contains("\\") || imageName.startsWith(".")|| !InputSanitizer.isImageValid(imageFile)) {
            throw new SecurityException("Invalid file name");
        }
    }

    public void checkImageName(String imageName){
        if (imageName.contains("..") || imageName.contains("/") || imageName.contains("\\") || imageName.startsWith(".")) {
            throw new SecurityException("Invalid file name");
        }
    }
    
    public String backupImage(MultipartFile imageFile, String imageName) throws IOException {
        // sanitize name
        String baseName = Paths.get(imageName).getFileName().toString();
        checkImage(imageFile, imageName);

        String orig = imageFile.getOriginalFilename();
        String ext = "";
        if (orig != null) {
            int i = orig.lastIndexOf('.');
            if (i >= 0 && i < orig.length() - 1) {
                ext = orig.substring(i); 
            }
        }
        
        // check if file is using right extension
        String finalName;
        int dot = baseName.lastIndexOf('.');
        if (dot >= 0) {           
            finalName = baseName;
        } else {
            finalName = baseName + ext;
        }
        
        Path target = BACKUP_FOLDER.resolve(finalName).normalize();
        //final validation to check file exists in the right folder
        if (!target.startsWith(BACKUP_FOLDER)) {
            throw new IOException("Path Traversal detected: " + imageName);
        }
        // save file
        Files.createDirectories(target.getParent());
        imageFile.transferTo(target.toFile());
        return finalName;     
    }

    public void deleteImageSafely(String imageName) {
        Path imagePath = BACKUP_FOLDER.resolve(imageName).normalize();
        if (!imagePath.startsWith(BACKUP_FOLDER)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Path traversal detected"
            );
        }
        try {
            if (Files.exists(imagePath)) {
                Files.delete(imagePath);
            }
        } catch (IOException e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error deleting image file",
                e
            );
        }
    }

    //show image 
    public ResponseEntity<Object> returnImage(String imageName) throws MalformedURLException {
        if (!imageName.contains(".")) {
            imageName= imageName + ".jpg";
        }
        if (imageName.contains("..") || imageName.contains("/") || imageName.contains("\\") || imageName.startsWith(".")) {
            throw new SecurityException("Invalid file name: " + imageName);
        }
        Path normalized = BACKUP_FOLDER.resolve(imageName).normalize();
        if (!normalized.startsWith(BACKUP_FOLDER)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid image path.");
        }
	    Resource file = new UrlResource(normalized.toUri());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").body(file);
	}

    public void saveUser(CharacterDTO characterDTO) {
        Character character = mapper.toDomain(characterDTO);
        character.setUser(userService.getLoggedUser());
        characterRepository.save(character);
    }

    // for equipping armor or weapon, sets the necessary values from the equipment
    // and adds the character to the equipment
    public void equipWeapon(WeaponDTO weaponDTO, long charId) {
        if( userService.getLoggedUser().getRoles().contains("ADMIN")|| userService.hasWeapon(weaponDTO.id())){
            Character character = characterRepository.findById(charId).get();
            Weapon weapon = weaponService.findById(weaponDTO.id());
            character.setWeaponEquiped(true);
            character.setStrength(weaponDTO.strength());
            character.setIntimidation(weaponDTO.intimidation());
            character.setWeapon(weapon);
            weapon.getCharacters().add(character);
            weaponService.save(weapon);
            characterRepository.save(character);
        }
    }

    // equips an armor to the character that recives
    public void equipArmor(ArmorDTO armorDTO, long charId) {
        if( userService.getLoggedUser().getRoles().contains("ADMIN")|| userService.hasArmor(armorDTO.id())){
            Character character = characterRepository.findById(charId).get();
            Armor armor = armorService.findById(armorDTO.id());
            character.setArmorEquiped(true);
            character.setDefense(armorDTO.defense());
            character.setStyle(armorDTO.style());
            character.setArmor(armor);
            armor.getCharacters().add(character);
            armorService.save(armor);
            characterRepository.save(character);
        }
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
        if( userService.getLoggedUser().getRoles().contains("ADMIN")|| userService.getCharacter().id()==charId){
            Character character = findById(charId);
            character.setWeapon(null);
            character.setStrength(0);
            character.setIntimidation(0);
            character.setWeaponEquiped(false);

            Weapon weapon = weaponService.findById(id);
            weapon.getCharacters().remove(character);
            weaponService.save(weapon);
            characterRepository.save(character);
            characterRepository.save(character);
        }
    }

    // unequips the armor in use
    public void unEquipArmor(long charId, long id) {
        if( userService.getLoggedUser().getRoles().contains("ADMIN")|| userService.getCharacter().id()==charId){
            Character character = findById(charId);
            character.setArmor(null);
            character.setDefense(0);
            character.setStyle(0);
            character.setArmorEquiped(false);

            Armor armor = armorService.findById(id);
            armor.getCharacters().remove(character);
            armorService.save(armor);
            characterRepository.save(character);
        }
    }

    public void deleteById(long id) {
        deleteImageSafely(findById(id).getImageName());
        characterRepository.deleteById(id);  
    }

    //returns the image from the id it gets
    public Resource getImageFile(long id) throws SQLException, IllegalAccessException  {
      if( userService.getLoggedUser().getRoles().contains("ADMIN")|| userService.getCharacter().id()==id){

        Character character = characterRepository.findById(id).orElseThrow();
		if (character.getImageFile() != null) {
			return new InputStreamResource(character.getImageFile().getBinaryStream());
		} else {
			throw new NoSuchElementException();
		}
    } else{
        throw new IllegalAccessException();
    }
    
    }

    public Resource downloadImage(long id) throws IOException, IllegalAccessException {
        if (userService.getLoggedUser().getRoles().contains("ADMIN") || userService.getCharacter().id() == id) {

            Character character = characterRepository.findById(id).orElseThrow();
            String imageName = character.getImageName();
            if (imageName == null) {
                throw new NoSuchElementException("Character has no image");
            }
            if (!imageName.contains(".")) {
                imageName = imageName + ".jpg";
            }
            if (imageName.contains("..") 
                || imageName.contains("/") 
                || imageName.contains("\\") 
                || imageName.startsWith(".")) {
                throw new SecurityException("Invalid file name: " + imageName);
            }

            Path imagePath = BACKUP_FOLDER.resolve(imageName).normalize();
            if (!imagePath.startsWith(BACKUP_FOLDER)) {
                throw new SecurityException("Invalid image path: " + imagePath);
            }
            if (!Files.exists(imagePath)) {
                throw new FileNotFoundException("Image not found on disk: " + imagePath);
            }

            Resource resource = new UrlResource(imagePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new IOException("Image cannot be read: " + imagePath);
            }
            return resource;
        } else {
            throw new IllegalAccessException("Not allowed to access this image");
        }
    }

    //change the image for a new one
    public void replaceImage(long id, InputStream inputStream, long size) {
        if( userService.getLoggedUser().getRoles().contains("ADMIN")|| userService.getCharacter().id()==id){
            Character character = characterRepository.findById(id).orElseThrow();
            if(character.getImageName() == null){
                throw new NoSuchElementException();
            }
            character.setImageFile(BlobProxy.generateProxy(inputStream, size));
            characterRepository.save(character);
        }
	}

    public void createCharacterImage(long id, URI location, InputStream inputStream, long size) {
        if( userService.getLoggedUser().getRoles().contains("ADMIN")|| userService.getCharacter().id()==id){
            Character character = characterRepository.findById(id).orElseThrow();
            character.setImageName(location.toString());
            character.setImageFile(BlobProxy.generateProxy(inputStream, size));
            characterRepository.save(character);
        }
	}

    public void editCharacterName(String name, long id) {
        if (userService.getLoggedUser().getRoles().contains("ADMIN") || userService.getCharacter().id() == id) {
            InputSanitizer.validateWhitelist(name);
            if (findById(id) == null) {
                throw new IllegalStateException("No character created");
            } else {
                Character newCharacter = characterRepository.findById(id).get();
                newCharacter.setName(name);
                characterRepository.save(newCharacter);
            }
        }else{
            throw new IllegalStateException("Not your character");
        }
    }

    public long userID(long charid){
        for(User u : userService.findAllNotDTO()){
            if(userService.characterID(u.getId()) != 0){
                if(u.getCharacter().getId() == charid){
                    return u.getId();
                }
            }
        }
        return 0;
    }
    
    public void setImageName(long charId, String imageName){
        Character character = findById(charId);
        character.setImageName(imageName);

        characterRepository.save(character);
    }
}