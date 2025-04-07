package com.grupo13.grupo13.DTOs;

import java.util.List;



public record WeaponDTO(
    long id,
    String name,
    String description,
    int strength,
    int price,
    List<UserDTO> users,
    List<CharacterDTO> characters
) {
}
