package uz.bakhromjon.template;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.feign.WorkPlaceFeignService;
import uz.bakhromjon.template.dto.TemplateCreateDTO;
import uz.bakhromjon.template.dto.TemplateGetDTO;

import java.time.LocalDateTime;
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
                isGlobal(createDTO.getIsGlobal()).createdAt(LocalDateTime.now()).createdBy(createDTO.getWorkPlaceID()).
                build();
    }

    public TemplateGetDTO toGetDTO(Template template) {
        return new TemplateGetDTO(template.getId(), template.getWorkPlaceID(), template.getContent(), template.getName(), template.getImage(), template.getIsGlobal(), template.getCreatedAt());
    }


    public List<TemplateGetDTO> toGetDTO(List<Template> templates) {
        List<TemplateGetDTO> templateGetDTOs = new ArrayList<>();
        templates.forEach(item -> {
            templateGetDTOs.add(toGetDTO(item));
        });
        return templateGetDTOs;
    }
}
