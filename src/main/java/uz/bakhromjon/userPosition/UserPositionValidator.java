package uz.bakhromjon.userPosition;

import org.springframework.stereotype.Component;
import uz.bakhromjon.base.validator.BaseValidator;
import uz.bakhromjon.userPosition.dto.UserPositionCreateDTO;
import uz.bakhromjon.userPosition.dto.UserPositionUpdateDTO;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Component
public class UserPositionValidator implements BaseValidator {
    public void validForCreate(UserPositionCreateDTO createDTO) {

    }

    public void validForUpdate(UserPositionUpdateDTO updateDTO) {

    }
}

