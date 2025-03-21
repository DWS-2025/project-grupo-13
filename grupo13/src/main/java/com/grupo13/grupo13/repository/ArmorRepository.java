package com.grupo13.grupo13.repository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo13.grupo13.model.Armor;

public interface ArmorRepository extends JpaRepository<Armor, Long>{

}  