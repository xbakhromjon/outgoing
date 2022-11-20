package uz.bakhromjon.user;

import org.springframework.stereotype.Component;
import uz.bakhromjon.base.validator.BaseValidator;
import uz.bakhromjon.user.dto.UserCreateDTO;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Component
public class UserValidator implements BaseValidator {
    public void validForCreate(UserCreateDTO createDTO) {

    }
}
