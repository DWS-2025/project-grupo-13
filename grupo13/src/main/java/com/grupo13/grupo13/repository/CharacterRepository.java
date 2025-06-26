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

}

