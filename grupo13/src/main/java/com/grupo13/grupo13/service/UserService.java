package com.grupo13.grupo13.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.DTOs.ArmorBasicDTO;
import com.grupo13.grupo13.DTOs.ArmorDTO;
import com.grupo13.grupo13.DTOs.CharacterBasicDTO;
import com.grupo13.grupo13.DTOs.CharacterDTO;
import com.grupo13.grupo13.DTOs.UserBasicDTO;
import com.grupo13.grupo13.DTOs.UserDTO;
import com.grupo13.grupo13.DTOs.WeaponBasicDTO;
import com.grupo13.grupo13.DTOs.WeaponDTO;
import com.grupo13.grupo13.mapper.UserMapper;
import com.grupo13.grupo13.mapper.ArmorMapper;
import com.grupo13.grupo13.mapper.WeaponMapper;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.User;
import com.grupo13.grupo13.repository.UserRepository;


@Service
public class UserService {

    //attributes
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WeaponService weaponService;
    @Autowired
    private ArmorService armorService;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private WeaponMapper weaponMapper;

    @Autowired
    private ArmorMapper armorMapper;

    //gets the current user
    public UserDTO getLoggedUser() {
        return mapper.toDTO(userRepository.findById((long)1).orElseThrow());
    }

    //returns all users in a list
    public List<UserBasicDTO> findAll() {
        return mapper.toDTOs(userRepository.findAll());
    }

    public void save(UserDTO userDTO) {

        User user = mapper.toDomain(userDTO);
        userRepository.save(user);
    }

    //returns true if a character (located by its id) has a equipment in use
    public boolean hasWeapon(long id) {

        User user = userRepository.findById((long)1).get();
        WeaponDTO equipment = weaponService.findById(id);
        if (equipment != null) {
            return user.getInventoryWeapon().contains(weaponMapper.toDomain(equipment));
        }
        return false;
    }

    public boolean hasArmor(long id) {

        User user = userRepository.findById((long)1).get();
        ArmorDTO equipment = armorService.findById(id);
        if (equipment != null) {
            return user.getInventoryArmor().contains(armorMapper.toDomain(equipment));
        }
        return false;
    }
    //returns the money os the current user
    public int getMoney() {
        return userRepository.findById((long)1).get().getMoney();
    }

    //returns the inventory of the current user
    public List<WeaponBasicDTO> currentUserInventoryWeapon() {
        User user = userRepository.findById((long)1).get();
        return weaponMapper.toDTOs(user.getInventoryWeapon());
    }

    public List<ArmorBasicDTO> currentUserInventoryArmor() {
        User user = userRepository.findById((long)1).get();
        return armorMapper.toDTOs(user.getInventoryArmor());
    }

    //put a equipment in the inventory of an scpecific user
    public void saveWeapon(long id) {
        User user = userRepository.findById((long)1).get();
        if (!hasWeapon(id)) {
            WeaponDTO equipment = weaponService.findById(id);
            if (equipment != null) {
                Weapon weapon = weaponMapper.toDomain(equipment);
                int price = equipment.price();
                user.setMoney(user.getMoney() - price);
                user.getInventoryWeapon().add(weapon);
                weapon.getUsers().add(user);
                userRepository.save(user);

            }
        }
    }

    public void saveArmor(long id) {
        User user = userRepository.findById((long)1).get();
        if (!hasArmor(id)) {
            ArmorDTO equipment = armorService.findById(id);
            if (equipment != null) {
                Armor armor = armorMapper.toDomain(equipment);
                int price = equipment.price();
                user.setMoney(user.getMoney() - price);
                user.getInventoryArmor().add(armor);
                armor.getUsers().add(user);
                userRepository.save(user);

            }
        }
    }

    //set a character to the current user
    public void saveCharacter(Character character) {
        User user = userRepository.findById((long)1).get();
        user.setCharacter(character); 
    }

    //returns the character of the current user
    public Character getCharacter() {
        return userRepository.findById((long)1).get().getCharacter();
    }

}


    
