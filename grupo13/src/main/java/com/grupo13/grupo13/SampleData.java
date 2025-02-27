package com.grupo13.grupo13;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class SampleData {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @PostConstruct
    public void init() {
        User lupe = new User(100, "Lupe");
        userRepository.save(lupe);

        equipmentRepository.save(new Weapon("Wood Sword", 20, 10,
                "https://i.imgur.com/ZJDJakr.jpeg",
                "A basic sword made from oak wood."));

        equipmentRepository.save(new Weapon("Iron Longsword", 50, 10,
                "https://i.imgur.com/xq1BDBK.jpeg",
                "A strong and reliable iron longsword."));

        equipmentRepository.save(new Weapon("Golden Rapier", 40, 10,
                "https://i.imgur.com/Bm5NeRx.jpeg",
                "A golden rapier. Stylish but fragile."));

        equipmentRepository.save(new Weapon("Dragon Fang Blade", 80, 10,
                "https://dummyimage.com/320x240/dc143c/000000.png&text=Dragon+Fang",
                "A blade crafted from the fang of an ancient dragon."));

        equipmentRepository.save(new Weapon("Shadow Dagger", 25, 10,
                "https://dummyimage.com/320x240/1a1a1a/ffffff.png&text=Shadow+Dagger",
                "A dagger infused with dark energy, perfect for stealth attacks."));

        equipmentRepository.save(new Armor("Wood Armor", 20, 10,
                "https://dummyimage.com/320x240/87f7ff/000000.png&text=Wood+Armor",
                "Obtained from an oak tree. Light but fragile."));

        equipmentRepository.save(new Armor("Iron Plate", 50, 10,
                "https://dummyimage.com/320x240/a8a8a8/000000.png&text=Iron+Plate",
                "A strong iron armor that provides good protection."));

        equipmentRepository.save(new Armor("Golden Chestplate", 40, 10,
                "https://dummyimage.com/320x240/ffd700/000000.png&text=Golden+Chestplate",
                "Made of gold. Shiny but weak."));

    }

    
}
