package com.grupo13.grupo13.repository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo13.grupo13.DTOs.WeaponBasicDTO;
import com.grupo13.grupo13.model.Weapon;

public interface WeaponRepository extends JpaRepository<Weapon, Long>{
    Page<Weapon> findAll(Pageable page);
    List<Weapon> findAll();
    
    
}
