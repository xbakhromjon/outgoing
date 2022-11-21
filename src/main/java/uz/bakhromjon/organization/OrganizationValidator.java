package uz.bakhromjon.organization;

import org.springframework.stereotype.Component;
import uz.bakhromjon.base.validator.BaseValidator;
import uz.bakhromjon.organization.dto.OrganizationUpdateDTO;


/**
 * @author : Bakhromjon Khasanboyev
 **/
@Component
public class OrganizationValidator implements BaseValidator {

    public void validForUpdate(OrganizationUpdateDTO updateDTO) {

    }
}

