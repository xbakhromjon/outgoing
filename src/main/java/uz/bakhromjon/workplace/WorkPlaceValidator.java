package uz.bakhromjon.workplace;

import org.springframework.stereotype.Component;
import uz.bakhromjon.base.validator.BaseValidator;
import uz.bakhromjon.workplace.dto.WorkPlaceCreateDTO;
import uz.bakhromjon.workplace.dto.WorkPlaceUpdateDTO;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Component
public class WorkPlaceValidator implements BaseValidator {
    public void validForCreate(WorkPlaceCreateDTO createDTO) {

    }

    public void validForUpdate(WorkPlaceUpdateDTO updateDTO) {

    }
}

