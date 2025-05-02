package com.grupo13.grupo13.DTOs;

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
        WeaponBasicDTO weapon,          
        ArmorBasicDTO armor
) {}
