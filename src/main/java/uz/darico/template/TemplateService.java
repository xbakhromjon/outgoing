package uz.darico.template;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.darico.base.service.AbstractService;
import uz.darico.contentFile.ContentFile;
import uz.darico.contentFile.ContentFileService;
import uz.darico.exception.exception.UniversalException;
import uz.darico.template.dto.TemplateCreateDTO;
import uz.darico.template.dto.TemplateGetDTO;
import uz.darico.template.dto.TemplateUpdateDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TemplateService extends AbstractService<TemplateRepository, TemplateValidator, TemplateMapper> {
    private final ContentFileService contentFileService;

    public TemplateService(TemplateRepository repository, TemplateValidator validator, TemplateMapper mapper,
                           ContentFileService contentFileService) {
        super(repository, validator, mapper);
        this.contentFileService = contentFileService;
    }

    public ResponseEntity<?> create(TemplateCreateDTO createDTO) {
        validator.validateForCreate(createDTO);
        ContentFile contentFile = contentFileService.getContentFile(createDTO.getFileID());
        Template template = mapper.toEntity(createDTO);
        template.setFile(contentFile);
        repository.save(template);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> update(TemplateUpdateDTO updateDTO) {
        validator.validateForUpdate(updateDTO);
        Template template = getPersist(updateDTO.getID());
        if (!updateDTO.getFileID().equals(template.getFile().getId())) {
            ContentFile newContentFile = contentFileService.getContentFile(updateDTO.getFileID());
            template.setFile(newContentFile);
        }
        template.setCreatedPurpose(updateDTO.getCreatedPurpose());
        repository.save(template);
        return ResponseEntity.ok(true);
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
        List<Template> templates = repository.findAll(workPlaceID);
        List<TemplateGetDTO> templateGetDTOs = mapper.toGetDTO(templates);
        return ResponseEntity.ok(templateGetDTOs);
    }
}