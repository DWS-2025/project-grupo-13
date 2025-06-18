package com.grupo13.grupo13.DTOs;

public record WeaponSearchDTO(
    String name,
    String description,
    Integer strength,
    Integer intimidation,
    Integer price
) {}
