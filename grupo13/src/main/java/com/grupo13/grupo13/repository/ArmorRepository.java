package com.grupo13.grupo13.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo13.grupo13.model.Armor;

public interface ArmorRepository extends JpaRepository<Armor, Long>{


}  