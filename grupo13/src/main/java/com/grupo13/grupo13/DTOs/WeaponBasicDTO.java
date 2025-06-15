package com.grupo13.grupo13.DTOs;

public record WeaponBasicDTO(
    long id,
    String name,
    String description,
    int strength,
    int intimidation,
    int price
) {}
