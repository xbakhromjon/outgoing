package uz.bakhromjon.user;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.user.dto.UserCreateDTO;
import uz.bakhromjon.user.dto.UserGetDTO;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper {
    User toEntity(UserCreateDTO createDTO);

    UserGetDTO toGetDTO(User user);
}
