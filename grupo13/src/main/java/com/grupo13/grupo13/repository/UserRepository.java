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

    Optional<User> findByUserName(String name);

}
