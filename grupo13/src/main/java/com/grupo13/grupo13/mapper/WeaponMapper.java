package com.grupo13.grupo13.mapper;
import java.util.List;
import org.mapstruct.Mapper;

import com.grupo13.grupo13.DTOs.WeaponBasicDTO;
import com.grupo13.grupo13.DTOs.WeaponDTO;
import com.grupo13.grupo13.model.Weapon;

@Mapper(componentModel = "spring")
public interface WeaponMapper {
    WeaponDTO toDTO(Weapon weapon);
    List<WeaponDTO> toDTOs(List<Weapon> weapons);
    Weapon toDomain(WeaponDTO weaponDTO);

    WeaponBasicDTO toBasicDTO(Weapon weapon);
    List<WeaponBasicDTO> toBasicDTOs(List<Weapon> weapons);
    Weapon toBasicDomain(WeaponBasicDTO weaponBasicDTO);

    /* 
    //@Mapping(target = "books", ignore = true)
    Weapon toDomain(WeaponBasicDTO weaponDTO);
    */
}