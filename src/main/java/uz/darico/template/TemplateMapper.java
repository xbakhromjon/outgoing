package uz.darico.template;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.darico.base.mapper.BaseMapper;
import uz.darico.contentFile.ContentFileService;
import uz.darico.feign.WorkPlaceFeignService;
import uz.darico.template.dto.TemplateCreateDTO;
import uz.darico.template.dto.TemplateGetDTO;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TemplateMapper implements BaseMapper {
    private final WorkPlaceFeignService workPlaceFeignService;


    public Template toEntity(TemplateCreateDTO createDTO) {
        return new Template.TemplateBuilder().
                workPlaceID(createDTO.getWorkPlaceID()).
                name(createDTO.getName()).
                content(createDTO.getContent()).
                userID(workPlaceFeignService.getUserID(createDTO.getWorkPlaceID())).
                isGlobal(createDTO.getIsGlobal()).
                build();
    }

    public TemplateGetDTO toGetDTO(Template template) {
        return new TemplateGetDTO(template.getWorkPlaceID(), template.getContent(), template.getName(), template.getImage());
    }


    public List<TemplateGetDTO> toGetDTO(List<Template> templates) {
        List<TemplateGetDTO> templateGetDTOs = new ArrayList<>();
        templates.forEach(item -> {
            templateGetDTOs.add(toGetDTO(item));
        });
        return templateGetDTOs;
    }
}
