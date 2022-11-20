package uz.bakhromjon.template;

import org.springframework.stereotype.Component;
import uz.bakhromjon.base.validator.BaseValidator;
import uz.bakhromjon.template.dto.TemplateCreateDTO;
import uz.bakhromjon.template.dto.TemplateUpdateDTO;

@Component
public class TemplateValidator implements BaseValidator {

    public void validateForCreate(TemplateCreateDTO createDTO) {

    }

    public void validateForUpdate(TemplateUpdateDTO updateDTO) {

    }
}
