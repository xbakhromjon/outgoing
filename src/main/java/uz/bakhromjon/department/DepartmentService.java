package uz.bakhromjon.department;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.department.dto.DepartmentCreateDTO;
import uz.bakhromjon.department.dto.DepartmentUpdateDTO;
import uz.bakhromjon.exception.exception.UniversalException;

import java.util.Optional;
import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Service
@Slf4j
public class DepartmentService extends AbstractService<
        DepartmentRepository, DepartmentValidator, DepartmentMapper> {


    public DepartmentService(DepartmentRepository repository, DepartmentValidator validator, DepartmentMapper mapper) {
        super(repository, validator, mapper);
    }


    public ResponseEntity<?> create(DepartmentCreateDTO createDTO) {
        validator.validForCreate(createDTO);
        Department department = mapper.toEntity(createDTO);
        Department saved = repository.save(department);
        log.info("Department succesfully create {} with id", saved.getId());
        return ResponseEntity.ok(mapper.toGetDTO(saved));
    }

    public ResponseEntity<?> update(DepartmentUpdateDTO updateDTO) {
        validator.validForUpdate(updateDTO);
        Department department = getPersist(updateDTO.getID());
        department.setName(updateDTO.getName());
        department.setShortName(updateDTO.getShortName());
        department = repository.save(department);
        log.info("Department succesfully updated {} with id", updateDTO.getID());
        return ResponseEntity.ok(mapper.toGetDTO(department));
    }

    public ResponseEntity<?> get(UUID ID) {
        Department department = getPersist(ID);
        log.info("Get department {} with ID", ID);
        return ResponseEntity.ok(mapper.toGetDTO(department));
    }

    public ResponseEntity<?> delete(UUID id) {
        if (isUsed(id)) {
            throw new UniversalException("Department in use. Cannot delete", HttpStatus.BAD_REQUEST);
        }
        repository.delete(id);
        log.info("Department succesfully delete {} with id", id);
        return ResponseEntity.ok(id);
    }

    private boolean isUsed(UUID id) {
        Department department = getPersist(id);
        if (department.getEmployeeCount() > 0 || !department.getUserPositions().isEmpty()
                || !department.getWorkPlaces().isEmpty()) {
            return true;
        }
        return false;
    }


    public void empty(UUID id) {

    }

    public Department getPersist(UUID id) {
        Optional<Department> optional = repository.findByIdAndIsDeleted(id, false);
        return optional.orElseThrow(() -> {
            log.warn("Department not found {} with id", id);
            throw new UniversalException(String.format("Department not found %s with", id), HttpStatus.BAD_REQUEST);
        });
    }

    public void save(Department department) {
        repository.save(department);
    }
}


