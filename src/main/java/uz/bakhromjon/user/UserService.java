package uz.bakhromjon.user;

import org.springframework.stereotype.Service;
import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.missive.MissiveMapper;
import uz.bakhromjon.missive.MissiveRepository;
import uz.bakhromjon.missive.MissiveValidator;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Service
public class UserService extends AbstractService<UserRepository, UserValidator, UserMapper> {
    public UserService(UserRepository repository, UserValidator validator, UserMapper mapper) {
        super(repository, validator, mapper);
    }
}
