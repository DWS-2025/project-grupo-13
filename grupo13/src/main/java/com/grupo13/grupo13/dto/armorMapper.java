import org.mapstruct.Mapper;
package com.grupo13.grupo13.dto;
import java.util.Collection;
import java.util.List;

import com.grupo13.grupo13.model.Armor;

@Mapper(componentModel = "spring")
public interface armorMapper {

    ArmorDTO toDTO(Armor armor);

    List<ArmorDTO> toDTOs(Collection<Armor> armors);

    Armor toDomain(ArmorDTO armorDTO);
}
