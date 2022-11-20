package uz.bakhromjon.userPosition;


import org.mapstruct.Mapper;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.userPosition.dto.UserPositionCreateDTO;
import uz.bakhromjon.userPosition.dto.UserPositionGetDTO;


/**
 * @author : Bakhromjon Khasanboyev
 **/
@Mapper(componentModel = "spring")
public interface UserPositionMapper extends BaseMapper {
    UserPosition toEntity(UserPositionCreateDTO createDTO);

    UserPositionGetDTO toGetDTO(UserPosition workPlace);

}


