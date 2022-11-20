package uz.bakhromjon.permission;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.bakhromjon.base.service.BaseService;
import uz.bakhromjon.exception.exception.UniversalException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Service
@Slf4j
public class PermissionService implements BaseService {
    @Autowired
    private PermissionRepository repository;

    public Permission getPersist(UUID id) {
        Optional<Permission> optional = repository.findById(id);
        return optional.orElseThrow(() -> {
            log.warn("Permission not found {} with id", id);
            throw new UniversalException(String.format("Permission not found %s with", id), HttpStatus.BAD_REQUEST);
        });
    }

    public List<Permission> getPersist(List<UUID> ids) {
        List<Permission> permissions = new ArrayList<>();
        for (UUID id : ids) {
            permissions.add(getPersist(id));
        }
        return permissions;
    }
}
