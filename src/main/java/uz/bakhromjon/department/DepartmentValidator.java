package uz.bakhromjon.department;

import org.springframework.stereotype.Component;
import uz.bakhromjon.base.validator.BaseValidator;
import uz.bakhromjon.department.dto.DepartmentCreateDTO;
import uz.bakhromjon.department.dto.DepartmentUpdateDTO;


/**
 * @author : Bakhromjon Khasanboyev
 **/
@Component
public class DepartmentValidator implements BaseValidator {
    public void validForCreate(DepartmentCreateDTO createDTO) {

    }

    public void validForUpdate(DepartmentUpdateDTO updateDTO) {

    }
}

