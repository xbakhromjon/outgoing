package uz.bakhromjon.department;


import org.mapstruct.Mapper;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.department.dto.DepartmentCreateDTO;
import uz.bakhromjon.department.dto.DepartmentGetDTO;


/**
 * @author : Bakhromjon Khasanboyev
 **/
@Mapper(componentModel = "spring")
public interface DepartmentMapper extends BaseMapper {

    Department toEntity(DepartmentCreateDTO createDTO);

    DepartmentGetDTO toGetDTO(Department workPlace);

}


