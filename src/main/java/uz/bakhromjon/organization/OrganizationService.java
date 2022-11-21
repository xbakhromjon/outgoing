package uz.bakhromjon.organization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.contentFile.ContentFileService;
import uz.bakhromjon.exception.exception.UniversalException;
import uz.bakhromjon.organization.dto.OrganizationUpdateDTO;

import java.util.Optional;
import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Service
@Slf4j
public class OrganizationService extends AbstractService<
        OrganizationRepository, OrganizationValidator, OrganizationMapper> {

    @Autowired
    private ContentFileService contentFileService;

    public OrganizationService(OrganizationRepository repository, OrganizationValidator validator, OrganizationMapper mapper) {
        super(repository, validator, mapper);
    }


    public ResponseEntity<?> update(OrganizationUpdateDTO updateDTO) {
        validator.validForUpdate(updateDTO);
        Organization organization = getPersist(updateDTO.getId());
        Organization toSave = mapper.toEntity(updateDTO);
        toSave.setLogo(contentFileService.getPersist(updateDTO.getLogoId()));
        organization = repository.save(toSave);
        log.info("Organization succesfully updated {} with id", updateDTO.getId());
        return ResponseEntity.ok(mapper.toGetDTO(organization));
    }

    public ResponseEntity<?> get(UUID ID) {
        Organization organization = getPersist(ID);
        log.info("Get organization {} with ID", ID);
        return ResponseEntity.ok(mapper.toGetDTO(organization));
    }


    public Organization getPersist(UUID id) {
        Optional<Organization> optional = repository.findById(id);
        return optional.orElseThrow(() -> {
            log.warn("Organization not found {} with id", id);
            throw new UniversalException(String.format("Organization not found %s with", id), HttpStatus.BAD_REQUEST);
        });
    }

}


