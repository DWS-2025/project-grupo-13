package com.grupo13.grupo13.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.grupo13.grupo13.DTOs.ArmorBasicDTO;
import com.grupo13.grupo13.DTOs.ArmorDTO;
import com.grupo13.grupo13.model.Armor;

@Mapper(componentModel = "spring")
public interface ArmorMapper {

    ArmorDTO toDTO(Armor armor);

    List<ArmorBasicDTO> toDTOs(List<Armor> armors);

    //@Mapping(target = "books", ignore = true)
    Armor toDomain(ArmorBasicDTO armorDTO);

    Armor toDomain(ArmorDTO armorDTO);
}
