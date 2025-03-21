package com.grupo13.grupo13.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo13.grupo13.model.Weapon;

public interface WeaponRepository extends JpaRepository<Weapon, Long>{
    //consultar, guardar, borrar y modificar
}
