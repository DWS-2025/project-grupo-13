package com.grupo13.grupo13;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.plaf.TreeUI;

import org.springframework.stereotype.Component;



@Component
public class EquipmentRepository{

    private AtomicLong nextId = new AtomicLong(1L);
	private ConcurrentHashMap<Long, Equipment> equip = new ConcurrentHashMap<>();

    public List<Equipment> findAll() {
        return equip.values().stream().toList();
    }

public Boolean isArmor(Equipment e){

return e instanceof Armor;

}

public Boolean isWeapon(Equipment e){

    return e instanceof Weapon;
    
    }



}

  /*   public void save(User user) {
        long id = user.getId();
        if (id == 0) {
            id = nextId.getAndIncrement();
            user.setId(id);
        }
        users.put(id, user);
    }

*/
