package com.grupo13.grupo13.controller;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import com.grupo13.grupo13.DTOs.ArmorBasicDTO;
import com.grupo13.grupo13.DTOs.ArmorDTO;
import com.grupo13.grupo13.DTOs.CharacterBasicDTO;
import com.grupo13.grupo13.DTOs.CharacterDTO;
import com.grupo13.grupo13.DTOs.UserDTO;
import com.grupo13.grupo13.DTOs.WeaponBasicDTO;
import com.grupo13.grupo13.DTOs.WeaponDTO;
import com.grupo13.grupo13.mapper.CharacterMapper;
import com.grupo13.grupo13.mapper.WeaponMapper;
import com.grupo13.grupo13.mapper.armorMapper;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.model.User;
import com.grupo13.grupo13.service.ArmorService;
import com.grupo13.grupo13.service.CharacterService;
import com.grupo13.grupo13.service.UserService;
import com.grupo13.grupo13.service.WeaponService;
import com.grupo13.grupo13.util.InputSanitizer;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api")
public class grupo13RestController {

	@Autowired
	private CharacterService characterService;

	@Autowired
	private WeaponService weaponService;

	@Autowired
	private ArmorService armorService;

	@Autowired
	private WeaponMapper weaponMapper;

	@Autowired
	private armorMapper armorMapper;

	@Autowired
	private CharacterMapper characterMapper;

	@Autowired
	private UserService userService;

	grupo13RestController(CharacterService characterService) {
		this.characterService = characterService;
	}

	// WEAPONS AND ARMORS
	// _________________________________________________________________________________________________________
	
	// SHOW ALL -------------------------------------------------

	@GetMapping("/weapons")
	public Page<WeaponBasicDTO> getPageWeapons(Pageable pageable) {
		return weaponService.findAllBasic(pageable);
	}

	@GetMapping("/armors")
	public Page<ArmorBasicDTO> getPageArmors(Pageable pageable) {
		return armorService.findAllBasic(pageable);
	}

	// SHOW 1 -------------------------------------------------

	@GetMapping("/weapon/{id}")
	public WeaponDTO getWeapon(@PathVariable long id) {
		return weaponService.findByIdDTO(id);
	}

	@GetMapping("/weapon/{id}/image")
	public ResponseEntity<Object> getWeaponImage(@PathVariable long id) throws SQLException, IOException {
		Resource weaponImage = weaponService.getImageFile(id);
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
				.body(weaponImage);
	}

	@GetMapping("/armor/{id}")
	public ArmorDTO getArmor(@PathVariable long id) {
		return armorService.findByIdDTO(id);
	}

	@GetMapping("/armor/{id}/image")
	public ResponseEntity<Object> getArmorImage(@PathVariable long id) throws SQLException, IOException {
		Resource armorImage = armorService.getImageFile(id);
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
				.body(armorImage);
	}

	// CREATE -------------------------------------------------

	@PostMapping("/weapons")
	public ResponseEntity<?> createWeapon(@RequestBody WeaponDTO weaponDTO) {
    	try {
        	InputSanitizer.validateWhitelist(weaponDTO.name());
        	InputSanitizer.validateWhitelist(weaponDTO.description());
        	weaponService.saveDTO(weaponDTO);
        	Weapon weapon = weaponMapper.toDomain(weaponDTO);
        	URI location = fromCurrentRequest().path("/{id}").buildAndExpand(weapon.getId()).toUri();
        	return ResponseEntity.created(location).body(weaponDTO);
    	}catch (IllegalArgumentException ex) {
        	return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
    	}
	}

	@PostMapping("/armors")
	public ResponseEntity<?> createArmor(@RequestBody ArmorDTO armorDTO) {
		try {
        	InputSanitizer.validateWhitelist(armorDTO.name());
        	InputSanitizer.validateWhitelist(armorDTO.description());
        	armorService.saveDTO(armorDTO);
       		Armor armor = armorMapper.toDomain(armorDTO);
        	URI location = fromCurrentRequest().path("/{id}").buildAndExpand(armor.getId()).toUri();
        	return ResponseEntity.created(location).body(armorDTO);
    	}catch (IllegalArgumentException ex) {
        	return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
    	}
	}

	@PostMapping("/weapon/{id}/image")
	public ResponseEntity<Object> createWeaponImage(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {
		if(!InputSanitizer.isImageValid(imageFile)){
			return ResponseEntity.badRequest().body("Error Validating Image ");
		}
		URI location = fromCurrentRequest().build().toUri();
		weaponService.createWeaponImage(id, location, imageFile.getInputStream(), imageFile.getSize());
		return ResponseEntity.created(location).build();
	}

	@PostMapping("/armor/{id}/image")
	public ResponseEntity<Object> createArmorImage(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {
		if(!InputSanitizer.isImageValid(imageFile)){
			return ResponseEntity.badRequest().body("Error Validating Image ");
		}
		URI location = fromCurrentRequest().build().toUri();
		armorService.createArmorImage(id, location, imageFile.getInputStream(), imageFile.getSize());
		return ResponseEntity.created(location).build();
	}

	// UPDATE -------------------------------------------------

	@PutMapping("/weapon/{id}")
	public ResponseEntity<?> replaceWeapon(@PathVariable long id, @RequestBody WeaponDTO updatedWeaponDTO) {
		try {
			InputSanitizer.validateWhitelist(updatedWeaponDTO.name());
			InputSanitizer.validateWhitelist(updatedWeaponDTO.description());

			weaponService.update(id, updatedWeaponDTO);
			return ResponseEntity.ok(updatedWeaponDTO);
		}catch (IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
		}
	}


	@PutMapping("/armor/{id}")
	public ResponseEntity<?> replaceArmor(@PathVariable long id, @RequestBody ArmorDTO updatedArmorDTO) {
		try {
			InputSanitizer.validateWhitelist(updatedArmorDTO.name());
			InputSanitizer.validateWhitelist(updatedArmorDTO.description());
			armorService.update(id, updatedArmorDTO);
			return ResponseEntity.ok(updatedArmorDTO);
		}catch (IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
		}
	}


	@PutMapping("/weapon/{id}/image")
	public ResponseEntity<Object> replaceWeaponImage(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {
		if (!InputSanitizer.isImageValid(imageFile)) {
			return ResponseEntity.badRequest().body("Error: Invalid image file.");
		}
		weaponService.replaceImage(id, imageFile.getInputStream(), imageFile.getSize());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/armor/{id}/image")
	public ResponseEntity<Object> replaceArmorImage(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {
		if (!InputSanitizer.isImageValid(imageFile)) {
			return ResponseEntity.badRequest().body("Error: Invalid image file.");
		}
		armorService.replaceImage(id, imageFile.getInputStream(), imageFile.getSize());
		return ResponseEntity.noContent().build();
	}

	// DELETE -------------------------------------------------

	@DeleteMapping("/armor/{id}")
	public void deleteArmor(@PathVariable long id) {
		armorService.deleteById(id);
	}

	@DeleteMapping("/weapon/{id}")
	public void deleteWeapon(@PathVariable long id) {
		weaponService.deleteById(id);
	}

	// CHARACTERS
	// _________________________________________________________________________________________________________

	// SHOW ALL -------------------------------------------------

	@GetMapping("/characters")
	public List<CharacterBasicDTO> getCharacters() {
		return characterService.findAll(); 
	}

	// SHOW 1 -------------------------------------------------

	@GetMapping("/character/")
	public CharacterDTO getCharacter() {
		return characterService.findByIdDTO(userService.getLoggedUser().getId());
	}

	@GetMapping("/character/image")
	public ResponseEntity<Object> getCharacterImage() throws SQLException, IOException {
		Resource postImage = characterService.getImageFile(userService.getLoggedUser().getId());
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
				.body(postImage);
	}

	// CREATE -------------------------------------------------

	@PostMapping("/characters")
	public ResponseEntity<?> createCharacter(@RequestBody CharacterDTO characterDTO) {
		try{
			InputSanitizer.validateWhitelist(characterDTO.name());
			InputSanitizer.validateWhitelist(characterDTO.description());
			InputSanitizer.validateWhitelist(characterDTO.imageName());
			characterService.save(characterDTO);
			Character character = characterMapper.toDomain(characterDTO);
			URI location = fromCurrentRequest().path("/{id}").buildAndExpand(character.getId()).toUri();
			return ResponseEntity.created(location).body(characterDTO);
		}catch (IllegalArgumentException ex) {
	 		return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
		}
	}

	@PostMapping("/character/{id}/image")
	public ResponseEntity<Object> createCharacterImage(@PathVariable long id, @RequestParam MultipartFile imageFile)
			throws IOException {
		if (!InputSanitizer.isImageValid(imageFile)) {
			return ResponseEntity.badRequest().body("Error: Invalid image file.");
		}
		URI location = fromCurrentRequest().build().toUri();
		characterService.createCharacterImage(id, location, imageFile.getInputStream(), imageFile.getSize());
		return ResponseEntity.created(location).build();
	}

	// Update character

	@PutMapping("/character/{id}")
	public ResponseEntity<String> editCharacter(@RequestBody String name, @PathVariable long id){       
        CharacterDTO characterDTO = characterService.findByIdDTO(id); //gets the character from the user
        if (characterDTO == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("There is no character");
        } else {
            characterService.editCharacterName(name, id);
			return ResponseEntity.status(HttpStatus.CREATED).body("Character updated succesfully");
        }
	}

	// -------------------------------------------------

	// DELETE -------------------------------------------------

	@DeleteMapping("/character/{id}")
	public ResponseEntity<String> deleteCharacter(@PathVariable long id) {
		CharacterDTO characterDTO = characterService.findByIdDTO(id); //gets the character from the user
        if (characterDTO == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("There is no character");
        } else {
            userService.deleteCharacter(id);
			return ResponseEntity.status(HttpStatus.CREATED).body("Character deleted succesfully");
        }
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<String> deleteUser (@PathVariable long id) {
		UserDTO userDTO = userService.findById(id); //gets the user
        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("There is no user");
        } else {
            userService.deleteUser(id);
			return ResponseEntity.status(HttpStatus.CREATED).body("User deleted succesfully");
        }
	}

	// IMAGES ------------------------------------------------------

	@PutMapping("character/{id}/image")
	public ResponseEntity<Object> replaceCharacterImage(@PathVariable long id, @RequestParam MultipartFile imageFile)
			throws IOException {

		characterService.replaceImage(id, imageFile.getInputStream(), imageFile.getSize());
		return ResponseEntity.noContent().build();
	}

	// GENERAL / FUNCTIONALITY

	@PostMapping("/weapon/purchase/{id}")
	public WeaponDTO purchaseWeapon(@PathVariable long id) {
		WeaponDTO weaponDTO = weaponService.findByIdDTO(id);
		// checks if the user has enough money or not
		if (weaponDTO == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"); // later will be automatic
		}
		int money = userService.getLoggedUserDTO().money();
		if (money >= weaponDTO.price()) {
			if (!userService.hasWeapon(id)) {
				userService.saveWeapon(id);
				return weaponService.findByIdDTO(id);
			} else {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Already purchased");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough Money");
		}
	}

	// GENERAL / FUNCTIONALITY

	@PostMapping("/armor/purchase/{id}")
	public ArmorDTO purchaseArmor(@PathVariable long id) {
		ArmorDTO armorDTO = armorService.findByIdDTO(id);
		// checks if the user has enough money or not
		if (armorDTO == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"); // later will be automatic
		}
		int money = userService.getLoggedUserDTO().money();
		if (money >= armorDTO.price()) {
			if (!userService.hasArmor(id)) {
				userService.saveArmor(id);
				return armorService.findByIdDTO(id);
			} else {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Already purchased");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough Money");
		}
	}


	//Equip weapons and armors

	@PostMapping("/weapon/equipment/{id}")
	public WeaponDTO equipWeapon(@PathVariable long id) {
		//launches error if character doesnt exist, to be improved
		CharacterDTO characterDTO = userService.getCharacter();
		Character character = characterMapper.toDomain(characterDTO);
		long charId = character.getId();
		WeaponDTO equipment = weaponService.findByIdDTO(id);
		if (equipment != null) { // if it exists
			if (userService.hasWeapon(id)) {
				characterService.equipWeapon(equipment, charId); // equips it, adding the necessary attributes
				//weaponService.addCharacter(characterDTO, equipment);
				return equipment;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not purchased"); // later will be automatic
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"); // later will be automatic
		}
	}

	@PostMapping("/armor/equipment/{id}")
	public ArmorDTO equipArmor(@PathVariable long id) {
		//launches error if character doesnt exist, to be improved
		CharacterDTO characterDTO = userService.getCharacter();
		Character character = characterMapper.toDomain(characterDTO);
		long charId = character.getId();
		ArmorDTO equipment = armorService.findByIdDTO(id);
		if (equipment != null) { // if it exists
			if (userService.hasArmor(id)) {
				characterService.equipArmor(equipment, charId); // equips it, adding the necessary attributes
				//armorService.addCharacter(characterDTO, equipment);
				return equipment;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not purchased"); // later will be automatic
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"); // later will be automatic
		}
	}

	//Unequip weapons and armors

	@DeleteMapping("/armor/equipment/{id}")
    public ArmorDTO unEquipArmor(@RequestParam long id) {
        //launches error if character doesnt exist, to be improved
		CharacterDTO characterDTO = userService.getCharacter();
		Character character = characterMapper.toDomain(characterDTO);
		long charId = character.getId();
		ArmorDTO equipment = armorService.findByIdDTO(id);
		if (equipment != null) { // if it exists
			if (userService.hasArmor(id)) {
				characterService.unEquipArmor(id, charId); // equips it, adding the necessary attributes
				return equipment;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not purchased"); // later will be automatic
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"); // later will be automatic
		}
    }

	@DeleteMapping("/weapon/equipment/{id}")
    public WeaponDTO unEquipWeapon(@RequestParam long id) {
        //launches error if character doesnt exist, to be improved
		CharacterDTO characterDTO = userService.getCharacter();
		Character character = characterMapper.toDomain(characterDTO);
		long charId = character.getId();
		WeaponDTO equipment = weaponService.findByIdDTO(id);
		if (equipment != null) { // if it exists
			if (userService.hasWeapon(id)) {
				characterService.unEquipWeapon(id, charId); // equips it, adding the necessary attributes
				return equipment;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not purchased"); // later will be automatic
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"); // later will be automatic
		}
    }

	// replace user
	@PutMapping("/user/")
	public ResponseEntity<?> replaceUser(@RequestParam String userName) {
		try {
			InputSanitizer.validateWhitelist(userName);
			UserDTO old =userService.getLoggedUserDTO();
		if (old == null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		userService.updateName(old, userName);
		return ResponseEntity.ok(userName);
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
		}
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<String> updateUser(@RequestBody String userName, @PathVariable long id) throws IOException{
        if(userName.isBlank()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The name cannot be left blank");
        }
        UserDTO oldUserDTO = userService.findById(id);
        if(oldUserDTO != null){
            userService.updateName(oldUserDTO, userName);
			return ResponseEntity.status(HttpStatus.CREATED).body("User updated succesfully");
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Could not manage");
        }
	}
}
