package com.grupo13.grupo13.DTOs;

import java.util.List;

import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.Weapon;

public record UserDTO(
    Long id,
    int money,
    String userName,
    CharacterBasicDTO character,          
    List<Weapon> inventoryWeapon,   //poner Simpleweapon
    List<Armor> inventoryArmor     //poner simplearmor
) {
}
