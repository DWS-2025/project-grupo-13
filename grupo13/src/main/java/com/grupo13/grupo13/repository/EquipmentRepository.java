package com.grupo13.grupo13.repository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;
import com.grupo13.grupo13.model.Equipment;

@Component
public class EquipmentRepository {

    //attributes
    private AtomicLong nextId = new AtomicLong(1L);
	private ConcurrentHashMap<Long, Equipment> equipments = new ConcurrentHashMap<>();

    //returns all equipments in a list
    public List<Equipment> findAll() {
        return equipments.values().stream().toList();
    }

    //searches for a specific equipment
    public Optional<Equipment> findById(long id) {
        return Optional.ofNullable(equipments.get(id));
    }

    //creates a new equipment and setting its id
    public void save(Equipment equipment) {
        long id = equipment.getId();
        if (id == 0) {
            id = nextId.getAndIncrement();
            equipment.setId(id);
        }
        equipments.put(id, equipment);
    }

    //deletes an equipement by its id
    public void deleteById(long id) {
        equipments.remove(id);
    }
}