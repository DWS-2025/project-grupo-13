package com.grupo13.grupo13.service;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

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

    // returns a specific user by its id
    public UserDTO findById(long id) {
        return mapper.toDTO(userRepository.findById(id).get());
    }

    public UserDTO getUser(String name) {
		return mapper.toDTO(userRepository.findByUserName(name).orElseThrow());
	}

	public User getLoggedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUserName(username).get();
    }

    //gets the current user
	public UserDTO getLoggedUserDTO() {
        return mapper.toDTO(getLoggedUser());
    }

    //for the shop, returns null if not logged
    public User getLoggedUserOrNull() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // null if not authenticated or anonymus
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        // if auth, we search in db
        return userRepository.findByUserName(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + auth.getName()));
    }
    
    public UserDTO getLoggedUserDTOOrNull() {
        
        User user = getLoggedUserOrNull();
        return (user != null) ? mapper.toDTO(getLoggedUserOrNull()) : null;
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
    public int getMoney(long id) {
        return userRepository.findById(id).get().getMoney();
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
        userRepository.save(user);
    }

    //returns the character of the current user
    public CharacterDTO getCharacter() {
        return characterMapper.toDTO(userRepository.findById((long)1).get().getCharacter());
    }

    //returns if the user has a specific weapon or armor
    public boolean hasWeapon(WeaponBasicDTO weapon){ 
        List<WeaponBasicDTO> inventory = getLoggedUserDTO().inventoryWeapon();   
        for (WeaponBasicDTO equipmentWeapon : inventory) {
            if (inventory.contains(equipmentWeapon)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasArmor(ArmorBasicDTO armor){ 
        List<ArmorBasicDTO> inventory = getLoggedUserDTO().inventoryArmor();   
        for (ArmorBasicDTO equipmentArmor : inventory) {
            if (inventory.contains(equipmentArmor)) {
                return true;
            }
        }
        return false;
    }

    public void setMoney(long id, int ammount){
        userRepository.findById(id).get().setMoney(ammount);
    }

    //updates an user's name when edited
    public void updateName(UserDTO updatedUserDTO, String userName){

        User updatedUser = mapper.toDomain(updatedUserDTO);
        updatedUser.setUserName(userName);;

        userRepository.save(updatedUser);
    }

    public void deleteUser(long id) {
    if (userRepository.existsById(id)) {
        userRepository.deleteById(id);
    } else {
        throw new NoSuchElementException("No existe un usuario con ID: " + id);
    }
}

}