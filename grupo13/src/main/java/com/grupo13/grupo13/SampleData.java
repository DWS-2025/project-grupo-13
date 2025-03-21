package com.grupo13.grupo13;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.User;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.repository.ArmorRepository;
import com.grupo13.grupo13.repository.UserRepository;
import com.grupo13.grupo13.repository.WeaponRepository;

import jakarta.annotation.PostConstruct;

@Service
public class SampleData {

        // attributes
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private WeaponRepository weaponRepository;

        @Autowired
        private ArmorRepository armorRepository;

        // loads the default items
        @PostConstruct
        public void init() {
                User lupe = new User(10000, "Lupe");
                userRepository.save(lupe);

                //faltan las imagenes

                //w1.png
                Weapon weapon = new Weapon("Wood Sword", "A basic sword made from oak wood.",
                                30, 10000, 25);
                weaponRepository.save(weapon);

                //w2.png
                Weapon weapon2 = new Weapon("Iron Longsword", "A strong and reliable iron longsword.", 
                10, 15, 50);
                weaponRepository.save(weapon2);

                //w3.png
                Weapon weapon3 = new Weapon("Golden Rapier", "A golden rapier. Stylish but very fragile.", 
                40, 20, 10);
                weaponRepository.save(weapon3);

                //w5.png
                Weapon weapon4 = new Weapon("Dragon Fang blade", "A blade crafted from the fang of an ancient dragon.", 
                10, 30, 80);
                weaponRepository.save(weapon4);

                //w4.png
                Weapon weapon5 = new Weapon("Shadow Dagger", "A dagger infused with dark energy, perfect for stealth attacks.", 
                10, 40, 25);
                weaponRepository.save(weapon5);

                //a1.png
                Armor armor = new Armor("Wood Armor", "Obtained from an oak tree. Light but fragile.", 
                10, 50, 20);
                armorRepository.save(armor);

                //a2.png
                Armor armor2 = new Armor("Iron Plate", "A strong iron armor that provides good protection.", 
                10, 65, 50);
                armorRepository.save(armor2);

                //a3.png
                Armor armor3 = new Armor("Golden Chestplate", "Made of valuable gold. Shiny but very weak.", 
                10, 40, 70);
                armorRepository.save(armor3);

        }

}
