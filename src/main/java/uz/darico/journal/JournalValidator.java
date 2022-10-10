package uz.darico.journal;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.darico.base.validator.BaseValidator;
import uz.darico.exception.exception.UniversalException;
import uz.darico.journal.dto.JournalCreateDto;
import uz.darico.journal.dto.JournalUpdateDto;

import java.util.UUID;

@Component
public class JournalValidator implements BaseValidator {
    public void validOnCreate(JournalCreateDto createDTO) {

    }

    public void validOnUpdate(JournalUpdateDto updateDTO) {

    }

    public void validOnKey(UUID key) {

    }

    public UUID validOnKey(String id) {
        if (id == null) {
            throw new UniversalException("ID null bo'lishi mumkin emas", HttpStatus.BAD_REQUEST);
        }
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw new UniversalException("ID noto'g'ri berildi. UUID formatida bo'lishi kerak", HttpStatus.BAD_REQUEST);
        }
    }
}
