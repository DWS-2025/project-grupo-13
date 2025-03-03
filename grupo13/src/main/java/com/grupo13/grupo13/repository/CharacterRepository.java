package com.grupo13.grupo13.repository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;
import com.grupo13.grupo13.model.Character;

@Component
public class CharacterRepository {

    //attributes
    private AtomicLong nextId = new AtomicLong(1L);
	private ConcurrentHashMap<Long, Character> characters = new ConcurrentHashMap<>();
    
    //returns all characters in a list
    public List<Character> findAll() {
        return characters.values().stream().toList();
    }
    
    //search a character by his id
    public Optional<Character> find(long id) {
        return Optional.ofNullable(characters.get(id));
    }

    //create a character and its id
    public void save(Character Character) {
        long id = Character.getId();
        if (id == 0) {
            id = nextId.getAndIncrement();
            Character.setId(id);
        }
        characters.put(id, Character);
    }
    
}

