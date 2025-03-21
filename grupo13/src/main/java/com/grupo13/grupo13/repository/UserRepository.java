package com.grupo13.grupo13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import com.grupo13.grupo13.model.User;

@Component
public interface UserRepository extends JpaRepository<User,Long>{

    
    //attributes
   /* private AtomicLong nextId = new AtomicLong(1L);
	private ConcurrentHashMap<Long, User> users = new ConcurrentHashMap<>();

    //returns all users in a list
    public List<User> findAll() {
        return users.values().stream().toList();
    }

    //creates a new user and its id
    public void save(User user) {
        long id = user.getId();
        if (id == 0) {
            id = nextId.getAndIncrement();
            user.setId(id);
        }
        users.put(id, user);
    }
*/
}
