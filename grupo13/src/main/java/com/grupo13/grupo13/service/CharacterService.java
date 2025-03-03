package com.grupo13.grupo13.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.model.Equipment;
import com.grupo13.grupo13.repository.CharacterRepository;

@Service
public class CharacterService {

    //attributes
    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private EquipmentService equipmentService;

    //returns all characters in a list
    public List<Character> findAll() {
        return characterRepository.findAll();
    }

    //returns a specific character by its id
    public Optional<Character> finding(long id) {
        return characterRepository.find(id);
    }

    //creates a new character
    public void save(Character character) {
        characterRepository.save(character);
    }

    //for equipping armor or weapon, sets the necessary values from the equipment
    public void equipWeapon(Equipment weapon, Character character){
        character.setWeaponEquiped(true);
        character.setStrength(equipmentService.getAttribute(weapon));
        character.setWeapon(weapon);
    }

    //equips an armor to the character that recives
    public void equipArmor(Equipment armor, Character character){
        character.setArmorEquiped(true);
        character.setDefense(equipmentService.getAttribute(armor));
        character.setArmor(armor);

    }

    //gets the equipment
    public Equipment getEquipedWeapon(Character character){
        return character.getWeapon();
    }

    //gets the armor in use
    public Equipment getEquipedArmor(Character character){
        return character.getArmor();
    }

    //unequips the weapon in use
    public void unEquipWeapon(Character character){
        character.setWeapon(null);
        character.setStrength(0);
        character.setWeaponEquiped(false);
    }

    //unequips the armor in use
    public void unEquipArmor(Character character){
        character.setArmor(null);
        character.setDefense(0);
        character.setArmorEquiped(false);
    }

}
