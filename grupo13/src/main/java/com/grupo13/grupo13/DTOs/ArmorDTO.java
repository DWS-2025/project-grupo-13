package com.grupo13.grupo13.DTOs;

import java.util.List;

public record ArmorDTO(
    long id,
    String name,
    String description,
    int defense,
    int price,
    List<UserBasicDTO> users,
    List<CharacterBasicDTO> characters
) {}

