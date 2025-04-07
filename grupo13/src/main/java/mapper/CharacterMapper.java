package mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.grupo13.grupo13.DTOs.CharacterDTO;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

    CharacterDTO toDTO(Character character);
    List<CharacterDTO> toDTOs(List<Character> characterss);
    Character toDomain(CharacterDTO characterDTO);
}
