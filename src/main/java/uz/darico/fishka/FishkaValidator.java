package uz.darico.fishka;

import org.springframework.stereotype.Component;
import uz.darico.base.validator.BaseValidator;
import uz.darico.fishka.dto.FishkaCreateDTO;
import uz.darico.fishka.dto.FishkaUpdateDTO;

@Component
public class FishkaValidator implements BaseValidator {
    public void validForCreate(FishkaCreateDTO createDTO) {

    }

    public void validForUpdate(FishkaUpdateDTO updateDTO) {

    }
}
