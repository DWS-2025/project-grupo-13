package com.grupo13.grupo13.controller;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

import com.grupo13.grupo13.DTOs.ArmorBasicDTO;
import com.grupo13.grupo13.DTOs.ArmorDTO;
import com.grupo13.grupo13.DTOs.CharacterBasicDTO;
import com.grupo13.grupo13.DTOs.CharacterDTO;
import com.grupo13.grupo13.DTOs.WeaponBasicDTO;
import com.grupo13.grupo13.DTOs.WeaponDTO;
import com.grupo13.grupo13.mapper.CharacterMapper;
import com.grupo13.grupo13.mapper.WeaponMapper;
import com.grupo13.grupo13.mapper.armorMapper;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.service.ArmorService;
import com.grupo13.grupo13.service.CharacterService;
import com.grupo13.grupo13.service.WeaponService;
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


	grupo13RestController(CharacterService characterService) {
		this.characterService = characterService;
	}

	// WEAPONS AND ARMORS
	// _________________________________________________________________________________________________________
	// SHOW ALL -------------------------------------------------
	@GetMapping("/weapons")
	public List<WeaponBasicDTO> getWeapons() {
		return weaponService.findAll();
	}

	@GetMapping("/armors")
	public List<ArmorBasicDTO> getArmors() {
		return armorService.findAll();
	}

	// SHOW 1 -------------------------------------------------
	@GetMapping("/weapon/{id}")
	public WeaponDTO getWeapon(@PathVariable long id) {
		return weaponService.findById(id); 
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
		return armorService.findById(id); 
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
	public ResponseEntity<WeaponDTO> createWeapon(@RequestBody WeaponDTO weaponDTO) {
		weaponService.save(weaponDTO);

		Weapon weapon = weaponMapper.toDomain(weaponDTO);
		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(weapon.getId()).toUri();

		return ResponseEntity.created(location).body(weaponDTO);
	}

	@PostMapping("/armors")
	public ResponseEntity<ArmorDTO> createArmor(@RequestBody ArmorDTO armorDTO) {
		armorService.save(armorDTO);

		Armor armor = armorMapper.toDomain(armorDTO);
		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(armor.getId()).toUri();

		return ResponseEntity.created(location).body(armorDTO);
	}

	
	@PostMapping("/weapon/{id}/image")
	public ResponseEntity<Object> createWeaponImage(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {
		URI location = fromCurrentRequest().build().toUri();

		weaponService.createWeaponImage(id, location, imageFile.getInputStream(), imageFile.getSize());

		return ResponseEntity.created(location).build();
	}

	@PostMapping("/armor/{id}/image")
	public ResponseEntity<Object> createArmorImage(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {
		URI location = fromCurrentRequest().build().toUri();

		armorService.createArmorImage(id, location, imageFile.getInputStream(), imageFile.getSize());

		return ResponseEntity.created(location).build();
	}

	// UPDATE -------------------------------------------------
	  @PutMapping("/weapon/{id}")
	public WeaponDTO replaceWeapon(@PathVariable long id, @RequestBody WeaponDTO updatedWeaponDTO) {
		weaponService.update(id, updatedWeaponDTO);

		return updatedWeaponDTO;
	}

	@PutMapping("/armor/{id}")
	public ArmorDTO replaceArmor(@PathVariable long id, @RequestBody ArmorDTO updatedArmorDTO) {
		armorService.update(id, updatedArmorDTO);

		return updatedArmorDTO;
	}

	@PutMapping("weapon/{id}/image")
	public ResponseEntity<Object> replaceWeaponImage(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {
		weaponService.replaceImage(id, imageFile.getInputStream(), imageFile.getSize());

		return ResponseEntity.noContent().build();
	}

	@PutMapping("armor/{id}/image")
	public ResponseEntity<Object> replaceArmorImage(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {
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
	@GetMapping("/character/{id}")
	public CharacterDTO getCharacter(@PathVariable long id) {
		return characterService.findById(id);
	}

	@GetMapping("/character/{id}/image")
	public ResponseEntity<Object> getCharacterImage(@PathVariable long id) throws SQLException, IOException {
		Resource postImage = characterService.getImageFile(id);

		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
				.body(postImage);
	}

	// CREATE -------------------------------------------------
	@PostMapping("/characters")
	public ResponseEntity<CharacterDTO> createCharacter(@RequestBody CharacterDTO characterDTO) {
		characterService.save(characterDTO);

		Character character = characterMapper.toDomain(characterDTO);
		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(character.getId()).toUri();

		return ResponseEntity.created(location).body(characterDTO);
	}

	@PostMapping("/character/{id}/image")
	public ResponseEntity<Object> createCharacterImage(@PathVariable long id, @RequestParam MultipartFile imageFile)
			throws IOException {

		URI location = fromCurrentRequest().build().toUri();
		characterService.createCharacterImage(id, location, imageFile.getInputStream(), imageFile.getSize());

		return ResponseEntity.created(location).build();
	}

	// YOU CANNOT UPDATE CHARACTERS
	// -------------------------------------------------

	// DELETE -------------------------------------------------
	@DeleteMapping("/character/{id}")
	public void deleteCharacter(@PathVariable long id) {
		characterService.deleteById(id);
	}

	// IMAGES ------------------------------------------------------
	@PutMapping("character/{id}/image")
	public ResponseEntity<Object> replaceCharacterImage(@PathVariable long id, @RequestParam MultipartFile imageFile)
			throws IOException {

		characterService.replaceImage(id, imageFile.getInputStream(), imageFile.getSize());

		return ResponseEntity.noContent().build();
	}
	
}
