package uz.bakhromjon.organization;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.organization.dto.OrganizationGetDTO;
import uz.bakhromjon.organization.dto.OrganizationUpdateDTO;


/**
 * @author : Bakhromjon Khasanboyev
 **/
@Mapper(componentModel = "spring")
public interface OrganizationMapper extends BaseMapper {


    OrganizationGetDTO toGetDTO(Organization workPlace);

    @Mapping(ignore = true, target = "logo")
    Organization toEntity(OrganizationUpdateDTO updateDTO);
}


