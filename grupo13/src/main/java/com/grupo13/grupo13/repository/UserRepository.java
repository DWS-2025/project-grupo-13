package com.grupo13.grupo13.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import com.grupo13.grupo13.model.User;

@Component
public interface UserRepository extends JpaRepository<User,Long>{

    @Query("SELECT u From User u join u.inventoryWeapon w WHERE w.name = :weaponNam")
    List<User> findByWeaponName(@Param("weaponName")String weaponName);

    @Query("SELECT u From User u join u.inventoryArmor a WHERE a.name = :armorNam")
    List<User> findByArmorName(@Param("armorName")String armorName);

    Optional<User> findByName(String name);


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
