package com.grupo13.grupo13.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.DTOs.ArmorBasicDTO;
import com.grupo13.grupo13.DTOs.ArmorDTO;
import com.grupo13.grupo13.DTOs.CharacterDTO;
import com.grupo13.grupo13.DTOs.UserBasicDTO;
import com.grupo13.grupo13.DTOs.UserDTO;
import com.grupo13.grupo13.DTOs.WeaponBasicDTO;
import com.grupo13.grupo13.DTOs.WeaponDTO;
import com.grupo13.grupo13.mapper.CharacterMapper;
import com.grupo13.grupo13.mapper.UserMapper;
import com.grupo13.grupo13.mapper.armorMapper;
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
    private armorMapper armorMapper;

    @Autowired
    private CharacterMapper characterMapper;

    //gets the current user
    public UserDTO getLoggedUser() {
        return mapper.toDTO(userRepository.findById((long)1).orElseThrow());
    }

    //returns all users in a list
    public List<UserBasicDTO> findAll() {
        return mapper.toBasicDTOs(userRepository.findAll());
    }

    public void save(UserDTO userDTO) {
        User user = mapper.toDomain(userDTO);

        userRepository.save(user);
    }

    //returns true if a character (located by its id) has a equipment in use
    public boolean hasWeapon(long id) {
        User user = userRepository.findById((long)1).get();
        WeaponDTO weaponDTO = weaponService.findById(id);

        if (weaponDTO != null) {
            return user.getInventoryWeapon().contains(weaponMapper.toDomain(weaponDTO));
        }
        return false;
    }

    //returns if the user has an armor given by an id
    public boolean hasArmor(long id) {
        User user = userRepository.findById((long)1).get();
        ArmorDTO armorDTO = armorService.findById(id);

        if (armorDTO != null) {
            return user.getInventoryArmor().contains(armorMapper.toDomain(armorDTO));
        }
        return false;
    }

    //returns the money of the current user
    public int getMoney() {
        return userRepository.findById((long)1).get().getMoney();
    }

    //returns the inventory of the current user
    public List<WeaponBasicDTO> currentUserInventoryWeapon() {
        User user = userRepository.findById((long)1).get();
        return weaponMapper.toBasicDTOs(user.getInventoryWeapon());
    }

    public List<ArmorBasicDTO> currentUserInventoryArmor() {
        User user = userRepository.findById((long)1).get();
        return armorMapper.toBasicDTOs(user.getInventoryArmor());
    }

    //put a equipment in the inventory of an scpecific user
    public void saveWeapon(long id) {
        User user = userRepository.findById((long)1).get();

        if (!hasWeapon(id)) {
            WeaponDTO weaponDTO = weaponService.findById(id);
            if (weaponDTO != null) {
                Weapon weapon = weaponMapper.toDomain(weaponDTO);
                int price = weaponDTO.price();
                user.setMoney(user.getMoney() - price);
                user.getInventoryWeapon().add(weapon);
                weapon.getUsers().add(user);
                userRepository.save(user);

            }
        }
    }

    //save an armor
    public void saveArmor(long id) {
        User user = userRepository.findById((long)1).get();

        if (!hasArmor(id)) {
            ArmorDTO armorDTO = armorService.findById(id);
            if (armorDTO != null) {
                Armor armor = armorMapper.toDomain(armorDTO);
                int price = armorDTO.price();
                user.setMoney(user.getMoney() - price);
                user.getInventoryArmor().add(armor);
                armor.getUsers().add(user);
                userRepository.save(user);

            }
        }
    }

    //set a character to the current user
    public void saveCharacter(CharacterDTO characterDTO) {
        Character character = characterMapper.toDomain(characterDTO);

        User user = userRepository.findById((long)1).get();

        user.setCharacter(character); 
    }

    //returns the character of the current user
    public CharacterDTO getCharacter() {
        return characterMapper.toDTO(userRepository.findById((long)1).get().getCharacter());
    }

}


    
