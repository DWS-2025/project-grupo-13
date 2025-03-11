package com.grupo13.grupo13.repository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;
import com.grupo13.grupo13.model.Weapon;

@Component
public class WeaponRepository {

    //attributes
    private AtomicLong nextId = new AtomicLong(1L);
	private ConcurrentHashMap<Long, Weapon> weapons = new ConcurrentHashMap<>();

    //returns all weapons in a list
    public List<Weapon> findAll() {
        return weapons.values().stream().toList();
    }

    //searches for a specific equipment
    public Optional<Weapon> findById(long id) {
        return Optional.ofNullable(weapons.get(id));
    }

    //creates a new equipment and setting its id
    public void save(Weapon equipment) {
        long id = equipment.getId();
        if (id == 0) {
            id = nextId.getAndIncrement();
            equipment.setId(id);
        }
        weapons.put(id, equipment);
    }

    //deletes an equipement by its id
    public void deleteById(long id) {
        weapons.remove(id);
    }
    
}