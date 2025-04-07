package com.grupo13.grupo13.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.grupo13.grupo13.DTOs.WeaponBasicDTO;
import com.grupo13.grupo13.DTOs.WeaponDTO;
import com.grupo13.grupo13.model.Weapon;

@Mapper(componentModel = "spring")
public interface WeaponMapper {

    WeaponDTO toDTO(Weapon weapon);

    List<WeaponDTO> toDTOs(List<Weapon> weaponss);

    //@Mapping(target = "books", ignore = true)
    Weapon toDomain(WeaponBasicDTO weaponDTO);

    Weapon toDomain(WeaponDTO weapondto);
}
