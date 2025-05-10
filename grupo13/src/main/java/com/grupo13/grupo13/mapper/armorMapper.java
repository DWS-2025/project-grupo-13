package com.grupo13.grupo13.mapper;
import java.util.List;
import org.mapstruct.Mapper;
import com.grupo13.grupo13.DTOs.ArmorBasicDTO;
import com.grupo13.grupo13.DTOs.ArmorDTO;
import com.grupo13.grupo13.model.Armor;

@Mapper(componentModel = "spring")
public interface armorMapper {
   ArmorDTO toDTO(Armor amor);
    List<ArmorDTO> toDTOs(List<Armor> armors);
    Armor toDomain(ArmorDTO armorDTO);

    ArmorBasicDTO toBasicDTO(Armor armor);
    List<ArmorBasicDTO> toBasicDTOs(List<Armor> armors);
    Armor toBasicDomain(ArmorBasicDTO armorBasicDTO);
}

