package uz.bakhromjon.fishka;

import org.springframework.stereotype.Component;
import uz.bakhromjon.base.validator.BaseValidator;
import uz.bakhromjon.fishka.dto.FishkaCreateDTO;
import uz.bakhromjon.fishka.dto.FishkaUpdateDTO;

@Component
public class FishkaValidator implements BaseValidator {
    public void validForCreate(FishkaCreateDTO createDTO) {

    }

    public void validForUpdate(FishkaUpdateDTO updateDTO) {

    }
}
