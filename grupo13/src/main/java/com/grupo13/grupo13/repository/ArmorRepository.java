package com.grupo13.grupo13.repository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import com.grupo13.grupo13.model.Armor;

@Component
public interface ArmorRepository extends JpaRepository<Armor,Long>{
    
}
    /* 
    //attributes
    private AtomicLong nextId = new AtomicLong(1L);
	private ConcurrentHashMap<Long, Armor> armors = new ConcurrentHashMap<>();

    //returns all equipments in a list
    public List<Armor> findAll() {
        return armors.values().stream().toList();
    }

    //searches for a specific equipment
    public Optional<Armor> findById(long id) {
        return Optional.ofNullable(armors.get(id));
    }

    //creates a new equipment and setting its id
    public void save(Armor armor) {
        long id = armor.getId();
        if (id == 0) {
            id = nextId.getAndIncrement();
            armor.setId(id);
        }
        armors.put(id, armor);
    }

    //deletes an equipement by its id
    public void deleteById(long id) {
        armors.remove(id);
    }
    
}
*/