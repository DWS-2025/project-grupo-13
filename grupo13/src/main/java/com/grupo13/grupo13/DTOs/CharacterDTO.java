package com.grupo13.grupo13.DTOs;

import java.sql.Blob;

import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.User;
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
        Blob imageFile,
        User user,              // poner simpleuser
        Weapon weapon,          // mirar si da referencia circular
        Armor armor) {          // mirar si da referencia circular
}
