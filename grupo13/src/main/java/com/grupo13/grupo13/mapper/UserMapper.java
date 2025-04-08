package com.grupo13.grupo13.mapper;
<<<<<<< HEAD:grupo13/src/main/java/mapper/UserMapper.java

=======
>>>>>>> main:grupo13/src/main/java/com/grupo13/grupo13/mapper/UserMapper.java
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.grupo13.grupo13.DTOs.UserBasicDTO;
import com.grupo13.grupo13.DTOs.UserDTO;
import com.grupo13.grupo13.model.User;

@Mapper(componentModel = "spring", uses = {CharacterMapper.class})
public interface UserMapper {

    UserDTO toDTO(User user);
    List<UserBasicDTO> toDTOs(List<User> users);
    
    @Mapping(target = "character", ignore = true)
<<<<<<< HEAD:grupo13/src/main/java/mapper/UserMapper.java
=======
    User toDomain(UserBasicDTO userDTO);
>>>>>>> main:grupo13/src/main/java/com/grupo13/grupo13/mapper/UserMapper.java
    User toDomain(UserDTO userDTO);         
}
