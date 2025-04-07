package com.grupo13.grupo13.dto;

import com.grupo13.grupo13.model.Weapon;

public record WeaponDTO(
    long id,
    String name,
    String description,
    int strength,
    int price
) {
    public WeaponDTO(Weapon weapon) {
        this(
            weapon.getId(),
            weapon.getName(),
            weapon.getDescription(),
            weapon.getstrength(),
            weapon.getPrice()
        );
    }
}
