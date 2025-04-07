package mapper;



import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;

import com.grupo13.grupo13.DTOs.WeaponDTO;
import com.grupo13.grupo13.model.Weapon;

@Mapper(componentModel = "spring")
public interface WeaponMapper {

    WeaponDTO toDTO(Weapon weapon);

    List<WeaponDTO> toDTOs(Collection<Weapon> weapons);

    Weapon toDomain(WeaponDTO weaponDTO);
}
