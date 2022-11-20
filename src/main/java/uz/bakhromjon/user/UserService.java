package uz.bakhromjon.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.contentFile.ContentFile;
import uz.bakhromjon.contentFile.ContentFileService;
import uz.bakhromjon.exception.exception.UniversalException;
import uz.bakhromjon.user.dto.UserCreateDTO;
import uz.bakhromjon.workplace.WorkPlace;
import uz.bakhromjon.workplace.WorkPlaceRepository;
import uz.bakhromjon.workplace.WorkPlaceService;

import java.util.Optional;
import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Service
@Slf4j
public class UserService extends AbstractService<UserRepository, UserValidator, UserMapper> {

    @Autowired
    private ContentFileService contentFileService;

    public UserService(UserRepository repository, UserValidator validator, UserMapper mapper) {
        super(repository, validator, mapper);
    }

    public ResponseEntity<?> create(UserCreateDTO createDTO) {
        validator.validForCreate(createDTO);
        User user = mapper.toEntity(createDTO);
        ContentFile avatar = contentFileService.getPersist(createDTO.getAvatarId());
        user.setAvatar(avatar);
        user = repository.save(user);
        return ResponseEntity.ok(mapper.toGetDTO(user));
    }

    public ResponseEntity<?> delete(UUID id) {
        repository.delete(id);
//        workPlaceService.empty(id);
        return ResponseEntity.ok(id);
    }

    public User getPersist(UUID id) {
        Optional<User> optional = repository.findByIdAndIsDeleted(id, false);
        return optional.orElseThrow(() -> {
            log.warn("User not found {} with id", id);
            throw new UniversalException(String.format("User not found %s with", id), HttpStatus.BAD_REQUEST);
        });
    }
}
