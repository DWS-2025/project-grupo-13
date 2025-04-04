package com.grupo13.grupo13.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.model.Weapon;
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

    //gets the current user
    public Optional<User> getLoggedUser() {
        return userRepository.findById((long)1);
    }

    //returns all users in a list
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    //returns true if a character (located by its id) has a equipment in use
    public boolean hasWeapon(long id) {
        User user = getLoggedUser().get();
        Optional<Weapon> equipment = weaponService.findById(id);
        if (equipment.isPresent()) {
            return user.getInventoryWeapon().contains(equipment.get());
        }
        return false;
    }
    public boolean hasArmor(long id) {
        User user = getLoggedUser().get();
        Optional<Armor> equipment = armorService.findById(id);
        if (equipment.isPresent()) {
            return user.getInventoryArmor().contains(equipment.get());
        }
        return false;
    }
    //returns the money os the current user
    public int getMoney() {
        return getLoggedUser().get().getMoney();
    }

    //returns the inventory of the current user
    public List<Weapon> currentUserInventoryWeapon() {
        User user = getLoggedUser().get();
        
        return user.getInventoryWeapon();
    }
    public List<Armor> currentUserInventoryArmor() {
        User user = getLoggedUser().get();
        return user.getInventoryArmor();
    }

    //put a equipment in the inventory of an scpecific user
    public void saveWeapon(long id) {
        User user = getLoggedUser().get();
        if (!hasWeapon(id)) {
            Optional<Weapon> equipment = weaponService.findById(id);
            if (equipment.isPresent()) {
                int price = equipment.get().getPrice();
                user.setMoney(user.getMoney() - price);
                user.getInventoryWeapon().add(equipment.get());
                equipment.get().getUsers().add(user);
                userRepository.save(user);

            }
        }
    }

    public void saveArmor(long id) {
        User user = getLoggedUser().get();
        if (!hasArmor(id)) {
            Optional<Armor> equipment = armorService.findById(id);
            if (equipment.isPresent()) {
                int price = equipment.get().getPrice();
                user.setMoney(user.getMoney() - price);
                user.getInventoryArmor().add(equipment.get());
                equipment.get().getUsers().add(user);
                userRepository.save(user);

            }
        }
    }

    //set a character to the current user
    public void saveCharacter(Character character) {
        User user = getLoggedUser().get();
        user.setCharacter(character); 
    }

    //returns the character of the current user
    public Character getCharacter() {
        return getLoggedUser().get().getCharacter();
    }

}
