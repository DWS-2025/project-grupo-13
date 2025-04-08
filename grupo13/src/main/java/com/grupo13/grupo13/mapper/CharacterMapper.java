package com.grupo13.grupo13.mapper;
<<<<<<< HEAD

=======
>>>>>>> main
import java.util.List;
import org.mapstruct.Mapper;
import com.grupo13.grupo13.DTOs.CharacterBasicDTO;
import com.grupo13.grupo13.DTOs.CharacterDTO;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

    /* 
    CharacterDTO toDTO(Character character);
    List<CharacterBasicDTO> toDTOs(List<Character> characters);
   
    //  @Mapping(target = "user", ignore = true)
    Character toDomain(CharacterBasicDTO characterDTO);
    */

    CharacterBasicDTO toBasicDTO(Character character);
    Character toBasicDomain(CharacterBasicDTO CharacterBasicDTO);
    List<CharacterBasicDTO> toBasicDTO(List<Character> characters);

    Character toDomain(CharacterDTO CharacterBasicDTO);
    CharacterDTO toDTO(Character character);
    List<CharacterDTO> toDTOs(List<Character> characters);
}
