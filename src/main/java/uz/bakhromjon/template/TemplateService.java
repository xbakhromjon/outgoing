package uz.bakhromjon.template;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.contentFile.ContentFileService;
import uz.bakhromjon.exception.exception.UniversalException;
import uz.bakhromjon.feign.WorkPlaceFeignService;
import uz.bakhromjon.template.dto.TemplateCreateDTO;
import uz.bakhromjon.template.dto.TemplateGetDTO;
import uz.bakhromjon.template.dto.TemplateUpdateDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TemplateService extends AbstractService<TemplateRepository, TemplateValidator, TemplateMapper> {
    private final ContentFileService contentFileService;
    private final WorkPlaceFeignService workPlaceFeignService;

    public TemplateService(TemplateRepository repository, TemplateValidator validator, TemplateMapper mapper,
                           ContentFileService contentFileService, WorkPlaceFeignService workPlaceFeignService) {
        super(repository, validator, mapper);
        this.contentFileService = contentFileService;
        this.workPlaceFeignService = workPlaceFeignService;
    }

    public ResponseEntity<?> create(TemplateCreateDTO createDTO) {
        validator.validateForCreate(createDTO);
        Template template = mapper.toEntity(createDTO);
        if (createDTO.getFileID() != null) {
            template.setImage(contentFileService.getPersist(createDTO.getFileID()));
        }
        Template saved = repository.save(template);
        return ResponseEntity.ok(mapper.toGetDTO(saved));
    }

    public ResponseEntity<?> update(TemplateUpdateDTO updateDTO) {
        validator.validateForUpdate(updateDTO);
        Template template = getPersist(updateDTO.getID());
        template.setContent(updateDTO.getContent());
        template.setName(updateDTO.getName());
        template.setIsGlobal(updateDTO.getIsGlobal());
        if (updateDTO.getFileID() != null) {
            template.setImage(contentFileService.getPersist(updateDTO.getFileID()));
        }
        Template saved = repository.save(template);
        return ResponseEntity.ok(mapper.toGetDTO(saved));
    }


    public Template getPersist(UUID ID) {
        Optional<Template> optional = repository.find(ID);
        return optional.orElseThrow(() -> {
            throw new UniversalException("Template not found", HttpStatus.BAD_REQUEST);
        });
    }

    public ResponseEntity<?> delete(UUID ID) {
        repository.delete(ID);
        return ResponseEntity
                .ok(true);
    }

    public ResponseEntity<?> get(UUID ID) {
        Template template = getPersist(ID);
        TemplateGetDTO templateGetDTO = mapper.toGetDTO(template);
        return ResponseEntity.ok(templateGetDTO);
    }

    public ResponseEntity<?> list(Long workPlaceID) {
        Long orID = workPlaceFeignService.getOrgID(workPlaceID);
        List<Template> templates = repository.findAll(workPlaceID, orID);
        List<TemplateGetDTO> templateGetDTOs = mapper.toGetDTO(templates);
        return ResponseEntity.ok(templateGetDTOs);
    }
}
