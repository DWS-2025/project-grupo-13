package com.grupo13.grupo13.DTOs;

public record CharacterBasicDTO(
        long id,
        String name,
        String description,
        int strength,
        int defense,
        int intimidation,
        int style,
        boolean weaponEquiped,
        boolean armorEquiped,
        String imageName
) {}
