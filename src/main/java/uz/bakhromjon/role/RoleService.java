package uz.bakhromjon.role;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uz.bakhromjon.base.service.BaseService;
import uz.bakhromjon.exception.exception.UniversalException;

import java.util.Optional;
import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Service
@Slf4j
public class RoleService implements BaseService {
    @Autowired
    private RoleRepository repository;


    public Role getPersist(UUID id) {
        Optional<Role> optional = repository.findById(id);
        return optional.orElseThrow(() -> {
            log.warn("Role not found {} with id", id);
            throw new UniversalException(String.format("Role not found %s with id", id), HttpStatus.BAD_REQUEST);
        });
    }
}
