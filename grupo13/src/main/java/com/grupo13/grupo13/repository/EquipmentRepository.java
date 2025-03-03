package com.grupo13.grupo13.repository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import com.grupo13.grupo13.model.Equipment;

@Component
public class EquipmentRepository {
    private AtomicLong nextId = new AtomicLong(1L);
	private ConcurrentHashMap<Long, Equipment> equipments = new ConcurrentHashMap<>();

    public List<Equipment> findAll() {
        return equipments.values().stream().toList();
    }

    public Optional<Equipment> findById(long id) {
        return Optional.ofNullable(equipments.get(id));
    }

    public void save(Equipment equipment) {
        long id = equipment.getId();
        if (id == 0) {
            id = nextId.getAndIncrement();
            equipment.setId(id);
        }
        equipments.put(id, equipment);
    }

    public void deleteById(long id) {
        equipments.remove(id);
    }
}