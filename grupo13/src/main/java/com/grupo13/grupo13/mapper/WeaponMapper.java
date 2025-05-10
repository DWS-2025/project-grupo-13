package com.grupo13.grupo13.mapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import com.grupo13.grupo13.DTOs.WeaponBasicDTO;
import com.grupo13.grupo13.DTOs.WeaponDTO;
import com.grupo13.grupo13.model.Weapon;

@Mapper(componentModel = "spring")
public interface WeaponMapper {
    WeaponDTO toDTO(Weapon weapon);
    List<WeaponBasicDTO> toDTOs(List<Weapon> weaponss);
   
    Weapon toDomain(WeaponBasicDTO weaponDTO);
    Weapon toDomain(WeaponDTO weapondto);
}
