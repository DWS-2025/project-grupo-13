package com.grupo13.grupo13;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getLoggedUser() {
        return userRepository.findAll().get(0);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean contains(Equipment equipment){
        User user = getLoggedUser();
        return user.getInventory().contains(equipment);
    }

    public ArrayList<Equipment> currentUserInventory(){
        User user = getLoggedUser();
        return user.getInventory();
    }

    public void saveEquipment(Equipment equipment){
        if (!contains(equipment)) getLoggedUser().getInventory().add(equipment);
    }


}
