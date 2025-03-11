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

        //attributes
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private EquipmentRepository equipmentRepository;

        //loads the default items
        @PostConstruct
        public void init() {
        User lupe = new User(10000, "Lupe");
        userRepository.save(lupe);

        equipmentRepository.save(new Weapon("Wood Sword", 20, 10,
                "w1.png",
                "A basic sword made from oak wood.",10000));

        equipmentRepository.save(new Weapon("Iron Longsword", 50, 10,
                "w2.png",
                "A strong and reliable iron longsword.",15));

        equipmentRepository.save(new Weapon("Golden Rapier", 40, 10,
                "w3.png",
                "A golden rapier. Stylish but very fragile.",20));

        equipmentRepository.save(new Weapon("Dragon Fang Blade", 80, 10,
                "w5.png",
                "A blade crafted from the fang of an ancient dragon.",30));

        equipmentRepository.save(new Weapon("Shadow Dagger", 25, 10,
                "w4.png",
                "A dagger infused with dark energy, perfect for stealth attacks.",40));

        equipmentRepository.save(new Armor("Wood Armor", 20, 10,
                "a1.png",
                "Obtained from an oak tree. Light but fragile.",50));

        equipmentRepository.save(new Armor("Iron Plate", 50, 10,
                "a2.png",
                "A strong iron armor that provides good protection.",65));

        equipmentRepository.save(new Armor("Golden Chestplate", 40, 10,
                "a3.png",
                "Made of valuable gold. Shiny but very weak.",70));
    }
    
}
