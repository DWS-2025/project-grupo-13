package com.grupo13.grupo13.DTOs;

public record ArmorBasicDTO(
    long id,
    String name,
    String description,
    int defense,
    int style,
    int price,
    String imageName
) {}
