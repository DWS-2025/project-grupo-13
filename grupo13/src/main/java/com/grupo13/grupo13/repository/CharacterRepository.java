package com.grupo13.grupo13.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.grupo13.grupo13.model.Character;


public interface CharacterRepository  extends JpaRepository<Character, Long>{

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

