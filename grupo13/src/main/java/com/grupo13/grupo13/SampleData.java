package com.grupo13.grupo13;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
        
        @Autowired
	private PasswordEncoder passwordEncoder;

        public Blob localImageToBlob(String localFilePath) {
                File imageFile = new File(localFilePath);
                if (imageFile.exists()) {
                        try {
                                return BlobProxy.generateProxy(imageFile.toURI().toURL().openStream(), imageFile.length());
                        } catch (IOException e) {
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error at processing the image");
                        }
                }
                return null;
        }

        // loads the default items
        @PostConstruct
        public void init() {
                userRepository.save(new User("user", passwordEncoder.encode("pass"), "USER"));
		userRepository.save(new User("admin", passwordEncoder.encode("adminpass"), "USER", "ADMIN"));

                //faltan las imagenes

                //w1.png
                Weapon weapon = new Weapon("Wood Sword", "A basic sword made from oak wood.",
                                30, 10000, 25);
                
                weapon.setimageFile(localImageToBlob("images/imp_imgs/w1.png"));
                weaponRepository.save(weapon);

                //w2.png
                Weapon weapon2 = new Weapon("Iron Longsword", "A strong and reliable iron longsword.", 
                10, 15, 50);
                weapon2.setimageFile(localImageToBlob("images/imp_imgs/w2.png"));
                weaponRepository.save(weapon2);

                //w3.png
                Weapon weapon3 = new Weapon("Golden Rapier", "A golden rapier. Stylish but very fragile.", 
                40, 20, 10);
                weapon3.setimageFile(localImageToBlob("images/imp_imgs/w3.png"));
                weaponRepository.save(weapon3);

                //w5.png
                Weapon weapon4 = new Weapon("Dragon Fang blade", "A blade crafted from the fang of an ancient dragon.", 
                10, 30, 80);
                weapon4.setimageFile(localImageToBlob("images/imp_imgs/w4.png"));
                weaponRepository.save(weapon4);

                //w4.png
                Weapon weapon5 = new Weapon("Shadow Dagger", "A dagger infused with dark energy, perfect for stealth attacks.", 
                10, 40, 25);
                weapon5.setimageFile(localImageToBlob("images/imp_imgs/w5.png"));
                weaponRepository.save(weapon5);

                //a1.png
                Armor armor = new Armor("Wood Armor", "Obtained from an oak tree. Light but fragile.", 
                10, 50, 20);
                armor.setImageFile(localImageToBlob("images/imp_imgs/a1.png"));
                armorRepository.save(armor);

                //a2.png
                Armor armor2 = new Armor("Iron Plate", "A strong iron armor that provides good protection.", 
                10, 65, 50);
                armor2.setImageFile(localImageToBlob("images/imp_imgs/a2.png"));
                armorRepository.save(armor2);

                //a3.png
                Armor armor3 = new Armor("Golden Chestplate", "Made of valuable gold. Shiny but very weak.", 
                10, 40, 70);
                armor3.setImageFile(localImageToBlob("images/imp_imgs/a3.png"));
                armorRepository.save(armor3);

        }

}
