package com.grupo13.grupo13.DTOs;

import java.util.List;

public record UserBasicDTO(
    Long id,
    int money,
    String userName,
    List<String> roles
) {}
