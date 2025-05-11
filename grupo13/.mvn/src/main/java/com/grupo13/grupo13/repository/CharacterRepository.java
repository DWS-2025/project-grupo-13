package com.grupo13.grupo13.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.grupo13.grupo13.model.Character;

public interface CharacterRepository  extends JpaRepository<Character, Long>{

    @Query("SELECT c FROM Character c WHERE c.weapon.name = :weaponName")
    List<Character> findCharactersByWeaponName(@Param("weaponName") String weaponName);

    @Query("SELECT c FROM Character c WHERE c.armor.name = :armorName")
    List<Character> findCharactersByArmorName(@Param("armorName") String armorName);

    /* 
    //attributes
    private AtomicLong nextId = new AtomicLong(1L);
	private ConcurrentHashMap<Long, Character> characters = new ConcurrentHashMap<>();
    
    //returns all characters in a list
    public List<Character> findAll() {
        return characters.values().stream().toList();
    }
    
    //searchs a character by his id
    public Optional<Character> find(long id) {
        return Optional.ofNullable(characters.get(id));
    }

    //creates a character and its id
    public void save(Character Character) {
        long id = Character.getId();
        if (id == 0) {
            id = nextId.getAndIncrement();
            Character.setId(id);
        }
        characters.put(id, Character);
    }
    public void deleteById(long id) {
        characters.remove(id);
    }
        */
    
}

