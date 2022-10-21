package uz.darico.confirmative;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import uz.darico.exception.exception.UniversalException;
import uz.darico.signatory.SignatoryStatus;

import java.util.EnumSet;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum ConfStatus {
    CONFIRMED("CONFIRMED", 1),
    REJECTED("REJECTED", 2),
    NOT_VIEWED("NOT_VIEWED", 3),
    VIEWED( "VIEWED", 4);

    private final String name;
    private final Integer code;


    public static String toConfStatus(Integer code) {
        EnumSet<ConfStatus> statuses = EnumSet.allOf(ConfStatus.class);
        Optional<ConfStatus> optional = statuses.stream().filter(item -> item.getCode().equals(code)).findFirst();
        return optional.orElseThrow(() -> {
            throw new UniversalException(String.format("%s Conf Status Code incorrect", code), HttpStatus.BAD_REQUEST);
        }).getName();
    }

}
