package uz.bakhromjon.workplace;


import org.mapstruct.Mapper;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.workplace.dto.WorkPlaceCreateDTO;
import uz.bakhromjon.workplace.dto.WorkPlaceGetDTO;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Mapper(componentModel = "spring")
public interface WorkPlaceMapper extends BaseMapper {
    WorkPlace toEntity(WorkPlaceCreateDTO createDTO);

    WorkPlaceGetDTO toGetDTO(WorkPlace workPlace);

}


