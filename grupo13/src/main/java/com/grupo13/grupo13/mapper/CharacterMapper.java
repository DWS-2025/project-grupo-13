package com.grupo13.grupo13.mapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.DTOs.CharacterBasicDTO;
import com.grupo13.grupo13.DTOs.CharacterDTO;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

    CharacterDTO toDTO(Character character);
    List<CharacterDTO> toDTOs(List<Character> characters);
    Character toDomain(CharacterDTO characterDTO);

    CharacterBasicDTO toBasicDTO(Character character);
    List<CharacterBasicDTO> toBasicDTOs(List<Character> characters);
    Character toBasicDomain(CharacterBasicDTO characterBasicDTO);
    /* 
    //  @Mapping(target = "user", ignore = true)
    Character toDomain(CharacterBasicDTO characterDTO);
    */
}
