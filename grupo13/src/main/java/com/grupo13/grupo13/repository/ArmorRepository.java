package com.grupo13.grupo13.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.Weapon;

public interface ArmorRepository extends JpaRepository<Armor, Long>{
    Page<Armor> findAll(Pageable page);
    List<Armor> findAll();

}  