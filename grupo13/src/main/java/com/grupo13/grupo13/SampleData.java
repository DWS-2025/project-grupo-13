package com.grupo13.grupo13;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.User;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.repository.EquipmentRepository;
import com.grupo13.grupo13.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class SampleData {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @PostConstruct
    public void init() {
        User lupe = new User(10000, "Lupe");
        userRepository.save(lupe);

        equipmentRepository.save(new Weapon("Wood Sword", 20, 10,
                "imp_imgs/w1.png",
                "A basic sword made from oak wood.",10000));

        equipmentRepository.save(new Weapon("Iron Longsword", 50, 10,
                "imp_imgs/w2.png",
                "A strong and reliable iron longsword.",15));

        equipmentRepository.save(new Weapon("Golden Rapier", 40, 10,
                "imp_imgs/w3.png",
                "A golden rapier. Stylish but very fragile.",20));

        equipmentRepository.save(new Weapon("Dragon Fang Blade", 80, 10,
                "https://dummyimage.com/320x240/dc143c/000000.png&text=Dragon+Fang",
                "A blade crafted from the fang of an ancient dragon.",30));

        equipmentRepository.save(new Weapon("Shadow Dagger", 25, 10,
                "https://dummyimage.com/320x240/1a1a1a/ffffff.png&text=Shadow+Dagger",
                "A dagger infused with dark energy, perfect for stealth attacks.",40));

        equipmentRepository.save(new Armor("Wood Armor", 20, 10,
                "https://dummyimage.com/320x240/87f7ff/000000.png&text=Wood+Armor",
                "Obtained from an oak tree. Light but fragile.",50));

        equipmentRepository.save(new Armor("Iron Plate", 50, 10,
                "https://dummyimage.com/320x240/a8a8a8/000000.png&text=Iron+Plate",
                "A strong iron armor that provides good protection.",65));

        equipmentRepository.save(new Armor("Golden Chestplate", 40, 10,
                "https://dummyimage.com/320x240/ffd700/000000.png&text=Golden+Chestplate",
                "Made of valuable gold. Shiny but very weak.",70));

    }

    
}
