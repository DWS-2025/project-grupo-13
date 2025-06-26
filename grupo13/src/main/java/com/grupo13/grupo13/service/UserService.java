package com.grupo13.grupo13.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
import com.grupo13.grupo13.util.InputSanitizer;
import org.springframework.security.core.Authentication;

@Service
public class UserService {
 private static final Path BACKUP_FOLDER = 
        Paths.get("").toAbsolutePath()
             .resolve("backups")
             .resolve("characters")
             .normalize();
    //attributes
    @Lazy
    @Autowired
    private CharacterService characterService;

    @Autowired
    private UserRepository userRepository;

    @Lazy
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

    @Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    // returns a specific user by its id
    public UserDTO findById(long id) {
        return mapper.toDTO(userRepository.findById(id).get());
    }

    public UserDTO getUser(String name) {
		return mapper.toDTO(userRepository.findByUserName(name).orElseThrow());
	}

    public void saveUser (User user){
        InputSanitizer.validateWhitelist(user.getUserName());
        userRepository.save(user);
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

    public List<User> findAllNotDTO() {
        return userRepository.findAll();
    }

    public void save(UserDTO userDTO) {
        User user = getLoggedUser();
        InputSanitizer.validateWhitelist(user.getUserName());
        userRepository.save(user);
    }

    //returns true if a character (located by its id) has a equipment in use
    public boolean hasWeapon(long id) {
        User user = getLoggedUser();
        WeaponDTO weaponDTO = weaponService.findByIdDTO(id);
        if (weaponDTO != null) {
            return user.getInventoryWeapon().contains(weaponMapper.toDomain(weaponDTO));
        }
        return false;
    }

    //returns if the user has an armor given by an id
    public boolean hasArmor(long id) {
        User user = getLoggedUser();
        ArmorDTO armorDTO = armorService.findByIdDTO(id);
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
        User user = getLoggedUser();
        return weaponMapper.toBasicDTOs(user.getInventoryWeapon());
    }

    public List<ArmorBasicDTO> currentUserInventoryArmor() {
        User user = getLoggedUser();
        return armorMapper.toBasicDTOs(user.getInventoryArmor());
    }

    //return inventory by user ID
    public List<WeaponBasicDTO> UserInventoryWeaponById(long id) {
        if( getLoggedUser().getRoles().contains("ADMIN")){
            User user = userRepository.findById(id).get();
            return weaponMapper.toBasicDTOs(user.getInventoryWeapon());
        }else{
            throw new IllegalAccessError("Only an admin can access to other's inventory");
        }
    }

    public List<ArmorBasicDTO> UserInventoryArmorById(long id) {
        if( getLoggedUser().getRoles().contains("ADMIN")){
            User user = userRepository.findById(id).get();
            return armorMapper.toBasicDTOs(user.getInventoryArmor());    
        }else{
            throw new IllegalAccessError("Only an admin can access to other's inventory");
        }
    }

    //put a equipment in the inventory of an scpecific user
    public void saveWeapon(long id) {       
        User user = getLoggedUser();
        if (!hasWeapon(id)) {
            Weapon weapon = weaponService.findById(id);
            user.getInventoryWeapon().add(weapon);
            weapon.getUsers().add(user);
            userRepository.save(user);
            weaponService.save(weapon);
        }
    }

    //save an armor
    public void saveArmor(long id) {
        User user = getLoggedUser();
        if (!hasArmor(id)) {
            Armor armor = armorService.findById(id);
            user.getInventoryArmor().add(armor);
            armor.getUsers().add(user);
            userRepository.save(user);
            armorService.save(armor);
        }
    }

    //set a character to the current user
    public void saveCharacter(CharacterDTO characterDTO) {
        Character character = characterMapper.toDomain(characterDTO);
        InputSanitizer.sanitizeRichText(character.getDescription());
        InputSanitizer.validateWhitelist(character.getName());
        User user = getLoggedUser();
        user.setCharacter(character);
        userRepository.save(user);
    }

    //returns the character of the current user
    public CharacterDTO getCharacter() {
        return characterMapper.toDTO(getLoggedUser().getCharacter());
    }

    //returns character by id
    public CharacterDTO getCharacterById(long id) {
        if( getLoggedUser().getRoles().contains("ADMIN")){
            return characterMapper.toDTO(userRepository.findById(id).get().getCharacter());
        }else{
            throw new IllegalAccessError("Only an admin can access to other's character");
        }
    }

    //delete character
    public void deleteCharacter(long userid) {
        if (getLoggedUserDTO().id() == userid || getLoggedUser().getRoles().contains("ADMIN")) {
            long charid = characterID(userid);
            if (userid!=0) {
                Character character = characterService.findById(charid);
                User user = character.getUser();
                user.setCharacter(null);
                saveUser(user);
                characterService.deleteById(charid);
            }
        } else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Id");
        }
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

    // updates an user's name when edited
    public void updateName(UserDTO updatedUserDTO, String userName) {
        if (!userExists(userName)) {
            if (updatedUserDTO.equals(getLoggedUserDTO()) || getLoggedUser().getRoles().contains("ADMIN")) {
               
                InputSanitizer.validateWhitelist(userName);

               if(updatedUserDTO.character()!=null){
                String newImageName = changeImage(updatedUserDTO, userName);
                characterService.findById(updatedUserDTO.character().id()).setImageName(newImageName);
               }
                
                User updatedUser = userRepository.findById(updatedUserDTO.id()).get();
                updatedUser.setUserName(userName);
                userRepository.save(updatedUser);
            } else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong id");
            }
        }
    }

    
public String changeImage(UserDTO dto, String newUserName) {
    String currentFileName = dto.character().imageName(); 

    // build new image name
    int dotIndex = currentFileName.lastIndexOf('.');
    String nameWithoutExt = currentFileName.substring(0, dotIndex); 
    String extension      = currentFileName.substring(dotIndex);    

    int dashIndex = nameWithoutExt.lastIndexOf('-');
    if (dashIndex < 0) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Invalid image name"
        );
    }

    
    String prefix      = nameWithoutExt.substring(0, dashIndex); 
    String oldUserName = nameWithoutExt.substring(dashIndex + 1); 

    // checking if it matches
    if (!oldUserName.equals(dto.userName())) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Can't find old file"
        );
    }

   
    String newFileName = prefix + "-" + newUserName + extension; 

    // replacing file
    Path oldPath = BACKUP_FOLDER.resolve(currentFileName).normalize();
    Path newPath = BACKUP_FOLDER.resolve(newFileName).normalize();
    if (!oldPath.startsWith(BACKUP_FOLDER) || !newPath.startsWith(BACKUP_FOLDER)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Path traversal detected");
    }
    try {
        if (Files.exists(oldPath)) {
            Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
            return newFileName;
        }else{   
            return currentFileName;
}
    } catch (IOException exc) {
        throw new ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Error",
            exc
        );
    }
       
}

    


    public void deleteUser(long userid) {   
        if(getLoggedUser().getRoles().contains("ADMIN")|| getLoggedUser().getId() == userid){
            if (userRepository.existsById(userid)) {
                boolean hasChar = characterID(userid)==0?false:true;
                if(hasChar){
                    deleteCharacter(userid);
                    deleteCharacter(userid);
                }
                userRepository.deleteById(userid);
                userRepository.deleteById(userid);
            } else {
                throw new NoSuchElementException("User doesn't exist " + userid);
                throw new NoSuchElementException("User doesn't exist " + userid);
            }
        }
    }
    
    //creates user
    public void createUser(String user, String pass){
        InputSanitizer.validateWhitelist(user);
        InputSanitizer.validateWhitelist(pass);
        userRepository.save(new User(user, passwordEncoder.encode(pass),10000, "USER"));
    }
    
    //checks if username is already in use
    public boolean userExists(String userName){
        List<User> userList = userRepository.findAll();
		User placeholder;
		boolean result = false;
        for(int i = 0; i < userList.size(); i++){
            placeholder = userList.get(i);
			if (placeholder.getUserName().equals(userName)) {
				result = true;
			}
        }
		return result;
    }

    //returns 0 if the user has no character
    public long characterID(long userid){
        for(User u : userRepository.findAll()){
            if(u.getId()==userid){
                if(u.getCharacter()!=null){
                    return u.getCharacter().getId();
                }else{
                    return 0;
                }
            }
        }
        return 0;
    }
}