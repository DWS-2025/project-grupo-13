package com.grupo13.grupo13.DTOs;

import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.Weapon;

public record CharacterDTO(
        long id,
        String name,
        String description,
        int strength,
        int defense,
        int healthPoints,
        boolean weaponEquiped,
        boolean armorEquiped,
        String imageName,
        UserBasicDTO user,
        Weapon weapon,          // mirar si da referencia circular
        Armor armor) {          // mirar si da referencia circular
}
