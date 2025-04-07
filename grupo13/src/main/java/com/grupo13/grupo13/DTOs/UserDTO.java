package com.grupo13.grupo13.DTOs;

import java.util.List;

public record UserDTO(
    Long id,
    int money,
    String userName,
    CharacterBasicDTO character,          
    List<WeaponBasicDTO> inventoryWeapon,   
    List<ArmorBasicDTO> inventoryArmor     
) {
}
