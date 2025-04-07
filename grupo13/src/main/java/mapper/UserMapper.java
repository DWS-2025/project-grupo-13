package mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.grupo13.grupo13.DTOs.UserDTO;
import com.grupo13.grupo13.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);
    List<UserDTO> toDTOs(List<User> users);
    User toDomain(UserDTO userDTO);
}
