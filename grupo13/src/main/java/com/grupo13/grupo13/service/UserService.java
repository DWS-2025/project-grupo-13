package com.grupo13.grupo13.service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo13.grupo13.model.Character;


import com.grupo13.grupo13.model.Equipment;
import com.grupo13.grupo13.model.User;
import com.grupo13.grupo13.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EquipmentService equipmentService;

    public User getLoggedUser() {
        return userRepository.findAll().get(0);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean hasEquipment(long id) {
        User user = getLoggedUser();
        Optional<Equipment> equipment = equipmentService.findById(id);
        if (equipment.isPresent()) {
            return user.getInventory().contains(equipment.get());
        }
        return false;
    }

    public int getMoney() {
        return getLoggedUser().getMoney();
    }

    public ArrayList<Equipment> currentUserInventory() {
        User user = getLoggedUser();
        return user.getInventory();
    }

    public void saveEquipment(long id) {
        User user = getLoggedUser();
        if (!hasEquipment(id)) {
            Optional<Equipment> equipment = equipmentService.findById(id);
            if (equipment.isPresent()) {
                int price = equipment.get().getPrice();
                user.setMoney(user.getMoney() - price);
                user.getInventory().add(equipment.get());
                equipment.get().getUsers().add(user);
            }
        }
    }

    public void saveCharacter(Character character) {
        getLoggedUser().setCharacter(character);
    }

    public Character getCharacter() {
        return getLoggedUser().getCharacter();
    }
}
