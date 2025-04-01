package com.grupo13.grupo13.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.model.User;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.repository.CharacterRepository;

@Service
public class CharacterService {

    // attributes
    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private WeaponService weaponService;
    @Autowired
    private ArmorService armorService;
    @Autowired
    private UserService userService;

    // returns all characters in a list
    public List<Character> findAll() {
        return characterRepository.findAll();
    }

    // returns a specific character by its id
    public Optional<Character> findById(long id) {
        return characterRepository.findById(id);
    }

    // creates a new character
    public void save(Character character) {
        characterRepository.save(character);
    }

    public void save(Character character, MultipartFile imageFile) throws IOException {
        if (!imageFile.isEmpty()) {
            character.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
        }
        this.save(character);
    }

    public void saveUser(Character character) {
        character.setUser(userService.getLoggedUser().get());
    }

    // for equipping armor or weapon, sets the necessary values from the equipment
    // and adds the character to the equipment
    public void equipWeapon(Weapon weapon, Character character) {
        character.setWeaponEquiped(true);
        character.setStrength(weapon.getstrength());
        character.setWeapon(weapon);
        characterRepository.save(character);
    }

    // equips an armor to the character that recives
    public void equipArmor(Armor armor, Character character) {
        character.setArmorEquiped(true);
        character.setDefense(armor.getDefense());
        character.setArmor(armor);
        characterRepository.save(character);
    }

    // gets the equipment
    public Weapon getEquipedWeapon(Character character) {
        return character.getWeapon();
    }

    // gets the armor in use
    public Armor getEquipedArmor(Character character) {
        return character.getArmor();
    }

    // unequips the weapon in use
    public void unEquipWeapon(Character character, long id) {
        character.setWeapon(null);
        character.setStrength(0);
        character.setWeaponEquiped(false);
        // removes the character from the list
        if (weaponService.findById(id).isPresent()) {
            weaponService.findById(id).get().getCharacters().remove(character);
            weaponService.save(weaponService.findById(id).get());
        }
        characterRepository.save(character);
    }

    // unequips the armor in use
    public void unEquipArmor(Character character, long id) {
        character.setArmor(null);
        character.setDefense(0);
        character.setArmorEquiped(false);
        // removes the character from the list
        if (armorService.findById(id).isPresent()) {
            armorService.findById(id).get().getCharacters().remove(character);
            armorService.save(armorService.findById(id).get());
        }
        characterRepository.save(character);
    }

    // deletes the given character
    public void delete(Character character) {
        if (character.getArmor() != null) {
            long id = character.getArmor().getId();
            if (armorService.findById(id).isPresent()) {
                armorService.findById(id).get().getCharacters().remove(character);
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

}
