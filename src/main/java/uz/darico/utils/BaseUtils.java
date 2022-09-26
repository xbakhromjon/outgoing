package uz.darico.utils;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.darico.exception.exception.UniversalException;

import java.util.StringTokenizer;
import java.util.UUID;

@Component
public class BaseUtils {

    public UUID strToUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw new UniversalException("%s ID UUID formatda bo'lishi kerak.".formatted(id),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    public Long strToLong(String id) {
        try {
            return Long.parseLong(id);
        } catch (Exception e) {
            throw new UniversalException("%s Long formatda bo'lishi kerak", HttpStatus.BAD_REQUEST);
        }
    }
}
