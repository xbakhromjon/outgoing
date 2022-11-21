package uz.bakhromjon.workplace;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.department.Department;
import uz.bakhromjon.department.DepartmentService;
import uz.bakhromjon.exception.exception.UniversalException;
import uz.bakhromjon.permission.PermissionService;
import uz.bakhromjon.role.Role;
import uz.bakhromjon.role.RoleService;
import uz.bakhromjon.template.Template;
import uz.bakhromjon.user.UserService;
import uz.bakhromjon.userPosition.UserPositionService;
import uz.bakhromjon.workplace.dto.WorkPlaceCreateDTO;
import uz.bakhromjon.workplace.dto.WorkPlaceUpdateDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Service
@Slf4j
public class WorkPlaceService extends AbstractService<
        WorkPlaceRepository, WorkPlaceValidator, WorkPlaceMapper> {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserPositionService userPositionService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private DepartmentService departmentService;

    public WorkPlaceService(WorkPlaceRepository repository, WorkPlaceValidator validator, WorkPlaceMapper mapper) {
        super(repository, validator, mapper);
    }


    public ResponseEntity<?> create(WorkPlaceCreateDTO createDTO) {
        validator.validForCreate(createDTO);
        Department department = departmentService.getPersist(createDTO.getDepartmentID());
        List<WorkPlace> workPlaces = getWorkPlacesByCount(createDTO.getCount());
        workPlaces = repository.saveAll(workPlaces);
        department.getWorkPlaces().addAll(workPlaces);
        departmentService.save(department);
        List<UUID> IDs = workPlaces.stream().map(item -> item.getId()).collect(Collectors.toList());
        log.info("WorkPlace succesfully create {} with id", IDs);
        return ResponseEntity.ok(IDs);
    }

    private List<WorkPlace> getWorkPlacesByCount(Integer count) {
        List<WorkPlace> workPlaces = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            workPlaces.add(new WorkPlace());
        }
        return workPlaces;
    }

    public ResponseEntity<?> update(WorkPlaceUpdateDTO updateDTO) {
        validator.validForUpdate(updateDTO);
        WorkPlace workPlace = getPersist(updateDTO.getID());
        if (isAttachedUser(updateDTO.getUserID())) {
            throw new UniversalException("User attached to other workPlace", HttpStatus.BAD_REQUEST);
        }
        workPlace.setUser(userService.getPersist(updateDTO.getUserID()));
        workPlace.setRole(roleService.getPersist(updateDTO.getRoleID()));
        workPlace.setUserPosition(userPositionService.getPersist(updateDTO.getUserPositionID()));
        workPlace.setPermissions(permissionService.getPersist(updateDTO.getPermissionsID()));
        workPlace = repository.save(workPlace);
        log.info("WorkPlace succesfully updated {} with id", updateDTO.getID());
        return ResponseEntity.ok(mapper.toGetDTO(workPlace));
    }

    private boolean isAttachedUser(UUID userID) {
        return repository.isAttachedUser(userID);
    }

    public ResponseEntity<?> get(UUID ID) {
        WorkPlace workPlace = getPersist(ID);
        log.info("Get workPlace {} with ID", ID);
        return ResponseEntity.ok(mapper.toGetDTO(workPlace));
    }

    public ResponseEntity<?> delete(UUID id) {
        if (isUsed(id)) {
            throw new UniversalException("WorkPlace must be empty for delete", HttpStatus.BAD_REQUEST);
        }
        repository.delete(id);
        log.info("WorkPlace succesfully delete {} with id", id);
        return ResponseEntity.ok(id);
    }

    public boolean isUsed(UUID id) {
        WorkPlace workPlace = getPersist(id);
        if (workPlace.getUser() != null) {
            return true;
        }
        return false;
    }


    public void empty(UUID id) {

    }

    public WorkPlace getPersist(UUID id) {
        Optional<WorkPlace> optional = repository.findByIdAndIsDeleted(id, false);
        return optional.orElseThrow(() -> {
            log.warn("WorkPlace not found {} with id", id);
            throw new UniversalException(String.format("WorkPlace not found %s with", id), HttpStatus.BAD_REQUEST);
        });
    }

    public Role getRoleByUser(UUID userId) {
        Optional<WorkPlace> optional = repository.findByUserId(userId);
        WorkPlace workPlace = optional.orElseThrow(() -> {
            throw new UniversalException(String.format("WorkPlace not found by %s userId", userId), HttpStatus.BAD_REQUEST);
        });
        return workPlace.getRole();
    }
}


