package com.grupo13.grupo13.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.model.Equipment;
import com.grupo13.grupo13.model.User;
import com.grupo13.grupo13.repository.CharacterRepository;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private UserService userService;

    public List<Character> findAll() {
        return characterRepository.findAll();
    }

    public Optional<Character> finding(long id) {
        return characterRepository.find(id);
    }

    public void save(Character character) {
        characterRepository.save(character);
    }

    //for equipping armor or weapon, sets the necessary values from the equipment and adds the character to the equipment
    public void equipWeapon(Equipment weapon, Character character){
        character.setWeaponEquiped(true);
        character.setStrength(equipmentService.getAttribute(weapon));
        character.setWeapon(weapon);
        weapon.getCharacters().add(character);
    }

    public void equipArmor(Equipment armor, Character character){
        character.setArmorEquiped(true);
        character.setDefense(equipmentService.getAttribute(armor));
        character.setArmor(armor);
        armor.getCharacters().add(character);

    }
//gets the equipment
    public Equipment getEquipedWeapon(Character character){
        return character.getWeapon();
    }

    public Equipment getEquipedArmor(Character character){
        return character.getArmor();
    }

    public void unEquipWeapon(Character character, long id){
        character.setWeapon(null);
        character.setStrength(0);
        character.setWeaponEquiped(false);
        //removes the character from the list
        if(equipmentService.findById(id).isPresent()){
            equipmentService.findById(id).get().getCharacters().remove(character);
        }
    }

    public void unEquipArmor(Character character, long id){
        character.setArmor(null);
        character.setDefense(0);
        character.setArmorEquiped(false);
        //removes the character from the list
        if(equipmentService.findById(id).isPresent()){
            equipmentService.findById(id).get().getCharacters().remove(character);
        }
    }

    public void delete(Character character){
        if (character.getArmor()!=null) {
            long id = character.getArmor().getId();
            if(equipmentService.findById(id).isPresent()){
                equipmentService.findById(id).get().getCharacters().remove(character);
            }
        }
        if (character.getWeapon()!=null) {
            long id = character.getWeapon().getId();
            if(equipmentService.findById(id).isPresent()){
                equipmentService.findById(id).get().getCharacters().remove(character);
            }
        }
        userService.getLoggedUser().setCharacter(null);
        characterRepository.deleteById(character.getId());
    } 


}
