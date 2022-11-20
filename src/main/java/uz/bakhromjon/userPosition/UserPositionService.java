package uz.bakhromjon.userPosition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.department.Department;
import uz.bakhromjon.department.DepartmentService;
import uz.bakhromjon.exception.exception.UniversalException;
import uz.bakhromjon.userPosition.dto.UserPositionCreateDTO;
import uz.bakhromjon.userPosition.dto.UserPositionUpdateDTO;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Service
@Slf4j
public class UserPositionService extends AbstractService<
        UserPositionRepository, UserPositionValidator, UserPositionMapper> {

    @Autowired
    private DepartmentService departmentService;

    public UserPositionService(UserPositionRepository repository, UserPositionValidator validator, UserPositionMapper mapper) {
        super(repository, validator, mapper);
    }


    public ResponseEntity<?> create(UserPositionCreateDTO createDTO) {
        validator.validForCreate(createDTO);
        Department department = departmentService.getPersist(createDTO.getDepartmentID());
        UserPosition userPosition = mapper.toEntity(createDTO);
        userPosition = repository.save(userPosition);
        department.getUserPositions().add(userPosition);
        departmentService.save(department);
        log.info("UserPosition succesfully create {} with id", userPosition.getId());
        return ResponseEntity.ok(mapper.toGetDTO(userPosition));
    }

    public ResponseEntity<?> update(UserPositionUpdateDTO updateDTO) {
        validator.validForUpdate(updateDTO);
        UserPosition userPosition = getPersist(updateDTO.getID());
        userPosition.setName(updateDTO.getName());
        userPosition = repository.save(userPosition);
        log.info("UserPosition succesfully updated {} with id", updateDTO.getID());
        return ResponseEntity.ok(mapper.toGetDTO(userPosition));
    }

    public ResponseEntity<?> get(UUID ID) {
        UserPosition userPosition = getPersist(ID);
        log.info("Get userPosition {} with ID", ID);
        return ResponseEntity.ok(mapper.toGetDTO(userPosition));
    }

    public ResponseEntity<?> delete(UUID id) {
        if (isUsed(id)) {
            throw new UniversalException("UserPosition in use. Cannot delete", HttpStatus.BAD_REQUEST);
        }
        repository.delete(id);
        log.info("UserPosition succesfully delete {} with id", id);
        return ResponseEntity.ok(id);
    }

    private boolean isUsed(UUID id) {
        return repository.isUsed(id);
    }


    public void empty(UUID id) {

    }

    public UserPosition getPersist(UUID id) {
        Optional<UserPosition> optional = repository.findByIdAndIsDeleted(id, false);
        return optional.orElseThrow(() -> {
            log.warn("UserPosition not found {} with id", id);
            throw new UniversalException(String.format("UserPosition not found %s with", id), HttpStatus.BAD_REQUEST);
        });
    }

}


