package com.grupo13.grupo13.dto;

import com.grupo13.grupo13.model.Armor;

public record ArmorDTO(
    long id,
    String name,
    String description,
    int defense,
    int price
) {
    public ArmorDTO(Armor armor) {
        this(
            armor.getId(),
            armor.getName(),
            armor.getDescription(),
            armor.getDefense(),
            armor.getPrice()
        );
    }
}